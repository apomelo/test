package test.com.alipay.api;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static test.com.alipay.api.AlipayConstant.*;

public class TradeAppPayTest {
    private static final Logger logger = LoggerFactory.getLogger(TradeAppPayTest.class);

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
                logger.info("{}", response.getBody());
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
        // 订单标题
        model.setSubject("App支付测试Java商品");
        // 订单附加信息
        model.setBody("我是测试数据");
        // 商家维护的唯一订单id
        model.setOutTradeNo(outTradeNo);
        // 价格
        model.setTotalAmount(price);
//        // 商家和支付宝签约的产品码
//        model.setProductCode("QUICK_MSECURITY_PAY");
        // 相对过期时间
        model.setTimeoutExpress("30m");
//        // 绝对过期时间
//        model.setTimeExpire("2022-08-01 22:00:00");

        // 商品详细信息
        List<GoodsDetail> goodsDetailList = new LinkedList<>();
        GoodsDetail goods1 = new GoodsDetail();
        goods1.setGoodsId("goodsNo1");
        goods1.setGoodsName("子商品1");
        goods1.setQuantity(1L);
        goods1.setPrice(price);
        goodsDetailList.add(goods1);
        GoodsDetail goods2 = new GoodsDetail();
        goods2.setGoodsId("goodsNo2");
        goods2.setGoodsName("子商品2");
        goods2.setQuantity(1L);
        goods2.setPrice("0");
        goodsDetailList.add(goods2);
        model.setGoodsDetail(goodsDetailList);

//        // 扩展信息，按需传入
//        ExtendParams extendParams = new ExtendParams();
//        // 系统商编号
//        extendParams.setSysServiceProviderId("2088511833207846");
//        model.setExtendParams(extendParams);

        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        return request;
    }
}