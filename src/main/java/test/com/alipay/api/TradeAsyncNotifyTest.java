package test.com.alipay.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TradeAsyncNotifyTest {
    private static final Logger logger = LoggerFactory.getLogger(TradeAsyncNotifyTest.class);

    public static void main(String[] args) {
        try {
            String request = "";
            boolean verify = verify(request);
            if (verify) {
                logger.info("验证成功。");
            } else {
                logger.info("验证失败。");
            }
        } catch (Exception e) {
            logger.info("调用遭遇异常，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static boolean verify(String request) throws AlipayApiException {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = JSON.parseObject(request, new TypeReference<Map<String, String>>() {});
        // 切记alipayPublicKey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        return AlipaySignature.rsaCheckV1(params, AlipayConstant.alipayPublicKey, "UTF8", "RSA2");
    }
}
