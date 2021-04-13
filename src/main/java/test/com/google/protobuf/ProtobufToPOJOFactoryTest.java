package test.com.google.protobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.com.google.protobuf.entity.ProtocolHeadEntity;
import test.com.google.protobuf.factory.ProtobufToPOJOFactory;
import test.com.google.protobuf.proto.ProtocolHead;

/**
 * Created by C on 2019/6/10.
 */
public class ProtobufToPOJOFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(ProtobufToPOJOFactoryTest.class);

    public void test() {
        ProtocolHead.Head head2 = ProtocolHead.Head.newBuilder().setType(ProtocolHead.Type.BODY_ONE).build();
        logger.debug("----------------------------\n" + head2.toString());
        ProtocolHeadEntity protocolHeadEntity2 = ProtobufToPOJOFactory.convert(head2, ProtocolHeadEntity.class);
        logger.debug("---------------------------\n" + protocolHeadEntity2.toString());
    }
}