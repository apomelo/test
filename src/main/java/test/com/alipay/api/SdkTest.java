package test.com.alipay.api;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdkTest {
    private static final Logger logger = LoggerFactory.getLogger(SdkTest.class);

    public static void main(String[] args) {
        try {
            // 1. 创建AlipayClient实例
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtils.getAlipayConfig());
            // 2. 创建使用的Open API对应的Request请求对象
            AlipayTradeAppPayRequest request = getRequest();
            // 3. 发起请求并处理响应，这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                logger.info("调用成功。");
                logger.info("{}", response);
            } else {
                logger.info("调用失败，原因：{}, {}", response.getMsg(), response.getSubMsg());
            }
        } catch (Exception e) {
            logger.info("调用遭遇异常，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static AlipayTradeAppPayRequest getRequest() {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("outtradeno");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        return request;
    }
}