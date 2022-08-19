package test.com.alipay.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static test.com.alipay.api.AlipayConstant.*;

public class TradeQueryTest {
    private static final Logger logger = LoggerFactory.getLogger(TradeQueryTest.class);

    public static void main(String[] args) {
        try {
            // 1. 创建AlipayClient实例
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayUtils.getAlipayConfig());
            // 2. 创建使用的Open API对应的Request请求对象
            AlipayTradeQueryRequest request = getRequest();
            // 3. 发起请求并处理响应
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            logger.info("{}", JSONObject.parse(response.getBody()));
            if (response.isSuccess()) {
                logger.info("调用成功。");
            } else {
                logger.info("调用失败，原因：{}, {}", response.getMsg(), response.getSubMsg());
            }
        } catch (Exception e) {
            logger.info("调用遭遇异常，原因：{}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static AlipayTradeQueryRequest getRequest() {
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
//        model.setTradeNo(tradeNo);
        request.setBizModel(model);
        return request;
    }
}