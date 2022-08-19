package test.com.alipay.api;

import com.alipay.api.AlipayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static test.com.alipay.api.AlipayConstant.*;

/**
 * @author C
 * @date 2022/8/17
 */
public class AlipayUtils {
    private static final Logger logger = LoggerFactory.getLogger(AlipayUtils.class);

    public static AlipayConfig getAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        // 设置网关地址
        alipayConfig.setServerUrl("https://openapi.alipaydev.com/gateway.do");
        // 设置应用ID
        alipayConfig.setAppId(appId);
        // 设置应用私钥
        alipayConfig.setPrivateKey(privateKey);
        // 设置请求格式，固定值json
        alipayConfig.setFormat("json");
        // 设置字符集
        alipayConfig.setCharset("UTF8");
        // 设置签名类型
        alipayConfig.setSignType("RSA2");
        // 设置支付宝公钥
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        return alipayConfig;
    }

    public static AlipayConfig getCertAlipayConfig() {
        AlipayConfig alipayConfig = new AlipayConfig();
        // 设置网关地址
        alipayConfig.setServerUrl("https://openapi.alipaydev.com/gateway.do");
        // 设置应用ID
        alipayConfig.setAppId(appId);
        // 设置应用私钥
        alipayConfig.setPrivateKey(privateKey);
        // 设置请求格式，固定值json
        alipayConfig.setFormat("json");
        // 设置字符集
        alipayConfig.setCharset("UTF8");
        // 设置签名类型
        alipayConfig.setSignType("RSA2");
        // 设置应用公钥证书路径
        alipayConfig.setAppCertPath(appCertPath);
        // 设置支付宝公钥证书路径
        alipayConfig.setAlipayPublicCertPath(alipayPublicCertPath);
        // 设置支付宝根证书路径
        alipayConfig.setRootCertPath(rootCertPath);
        return alipayConfig;
    }
}
