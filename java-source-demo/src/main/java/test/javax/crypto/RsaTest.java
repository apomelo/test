package test.javax.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;

/**
 * Created by C on 2019/4/3.
 */
public class RsaTest {
    private static final Logger logger = LoggerFactory.getLogger(RsaTest.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        KeyPair keyPair = getKeyPare();
        byte[] publicKeyBytes = getPublicKeyBytes(keyPair);
        byte[] privateKeyBytes = getPrivateKeyBytes(keyPair);

        String publicKey = new String(byte2Base64(publicKeyBytes), "UTF-8");
        String privateKey = new String(byte2Base64(privateKeyBytes), "UTF-8");
        logger.info("publicKey={}, length={}", publicKey, publicKey.length());
        logger.info("privateKey={}, length={}", privateKey, privateKey.length());

        String s = "12345";
        byte[] encryptBytes = encryptByPublicKey(base642Byte(publicKey.getBytes()), s.getBytes());
        byte[] decryptBytes = decryptByPrivateKey(base642Byte(privateKey.getBytes()), encryptBytes);

        logger.info("decryptStr={}", new String(decryptBytes, "UTF-8"));
    }

    public static KeyPair getKeyPare() {
        return getKeyPare(1024);
    }

    public static KeyPair getKeyPare(int keySize) {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            logger.warn("{}", e);
        }
        return keyPair;
    }

    public static PublicKey getPublicKey(KeySpec keySpec) {
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("{}", e);
        } catch (InvalidKeySpecException e) {
            logger.warn("{}", e);
        }
        return publicKey;
    }

    public static PublicKey getPublicKey(byte[] keyBytes) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        return getPublicKey(keySpec);
    }

    public static PublicKey getPublicKey(BigInteger modulus, BigInteger publicExponent) {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, publicExponent);
        return getPublicKey(keySpec);
    }

    public static byte[] getPublicKeyBytes(KeyPair keyPair) {
        return keyPair.getPublic().getEncoded();
    }

    public static PrivateKey getPrivateKey(byte[] keyBytes) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("{}", e);
        } catch (InvalidKeySpecException e) {
            logger.warn("{}", e);
        }
        return privateKey;
    }

    public static byte[] getPrivateKeyBytes(KeyPair keyPair) {
        return keyPair.getPrivate().getEncoded();
    }

    public static byte[] byte2Base64(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] base642Byte(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] encryptByPublicKey(PublicKey publicKey, byte[] data) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            logger.warn("{}", e);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("{}", e);
        } catch (InvalidKeyException e) {
            logger.warn("{}", e);
        } catch (BadPaddingException e) {
            logger.warn("{}", e);
        } catch (IllegalBlockSizeException e) {
            logger.warn("{}", e);
        }
        return result;
    }

    public static byte[] encryptByPublicKey(byte[] key, byte[] data) {
        PublicKey publicKey = getPublicKey(key);
        return encryptByPublicKey(publicKey, data);
    }

    public static byte[] encryptByPublicKey(BigInteger modulus, BigInteger publicExponent, byte[] data) {
        PublicKey publicKey = getPublicKey(modulus, publicExponent);
        return encryptByPublicKey(publicKey, data);
    }

    public static byte[] decryptByPrivateKey(PrivateKey privateKey, byte[] data) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            logger.warn("{}", e);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("{}", e);
        } catch (InvalidKeyException e) {
            logger.warn("{}", e);
        } catch (BadPaddingException e) {
            logger.warn("{}", e);
        } catch (IllegalBlockSizeException e) {
            logger.warn("{}", e);
        }
        return result;
    }

    public static byte[] decryptByPrivateKey(byte[] key, byte[] data) {
        PrivateKey privateKey = getPrivateKey(key);
        return decryptByPrivateKey(privateKey, data);
    }
}
