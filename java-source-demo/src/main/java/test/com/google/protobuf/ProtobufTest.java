package test.com.google.protobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.com.google.protobuf.entity.ProtocolHeadEntity;
import test.com.google.protobuf.factory.ProtobufToPOJOFactory;
import test.com.google.protobuf.proto.PrivateProtocol;
import test.com.google.protobuf.proto.ProtocolBodyChat;
import test.com.google.protobuf.proto.ProtocolBodyOne;
import test.com.google.protobuf.proto.ProtocolHead;

/**
 * Created by C on 2019/6/10.
 */
public class ProtobufTest {
    private static final Logger logger = LoggerFactory.getLogger(ProtobufTest.class);

    public static void main(String[] args) {
//        test();
        testToBuilder();
    }

    public static void test() {
        PrivateProtocol.Protocol protocol = PrivateProtocol.Protocol.newBuilder()
                .setBodyOne(ProtocolBodyOne.BodyOne.newBuilder().build())
                .build();
        logger.info("{}", protocol);
        logger.info("{}", protocol.getHead().toString());
    }

    public static void testToBuilder() {
        ProtocolBodyChat.BodyChat bodyChat = ProtocolBodyChat.BodyChat.newBuilder()
                .setFrom(1)
                .setChatType(1)
                .setRoomId(1)
                .addTo(2)
                .addText("123")
                .build();
        logger.info("{}", bodyChat);
        ProtocolBodyChat.BodyChat bodyChat1 = bodyChat.toBuilder().setFrom(9).build();
        logger.info("{}", bodyChat1);
        ProtocolBodyChat.BodyChat bodyChat2 = bodyChat.newBuilderForType().setFrom(99).build();
        logger.info("{}", bodyChat2);
    }
}