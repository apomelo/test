package test.com.google.protobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.com.google.protobuf.entity.ProtocolBodyOneEntity;
import test.com.google.protobuf.entity.ProtocolHeadEntity;
import test.com.google.protobuf.factory.POJOToProtobufFactory;
import test.com.google.protobuf.proto.ProtocolBodyOne;
import test.com.google.protobuf.proto.ProtocolHead;

import java.util.ArrayList;

/**
 * Created by C on 2019/6/10.
 */
public class POJOToProtobufFactoryTest {
    private static final Logger logger = LoggerFactory.getLogger(POJOToProtobufFactoryTest.class);

    public static void main(String[] args) throws InterruptedException {
//        test();
        testNest();
//        testBuild();
//        testMultiNest();
//        testMultiBuild();
    }

    public static void test() {
        ProtocolHeadEntity protocolHeadEntity = new ProtocolHeadEntity();
        protocolHeadEntity.setType(ProtocolHeadEntity.Type.BODY_CHAT);
        ProtocolHead.Head head = POJOToProtobufFactory.convert(protocolHeadEntity, ProtocolHead.Head.class);
        logger.debug("----------------------------\n" + head.toString());
    }

    public static void testNest() {
        ProtocolBodyOneEntity protocolBodyOneEntity = new ProtocolBodyOneEntity();
        protocolBodyOneEntity.setCorpus(ProtocolBodyOneEntity.Corpus.NEWS);

        protocolBodyOneEntity.setAction(new ProtocolBodyOneEntity.Action(3));

        protocolBodyOneEntity.setPage(3);

        protocolBodyOneEntity.setFloatListTest(new ArrayList<Float>() {{add((float) 1); add((float) 2);}});

        protocolBodyOneEntity.setCorpusListTest(new ArrayList<ProtocolBodyOneEntity.Corpus>() {{add(ProtocolBodyOneEntity.Corpus.IMAGES);}});

        protocolBodyOneEntity.setActionListTest(new ArrayList<ProtocolBodyOneEntity.Action>() {{add(new ProtocolBodyOneEntity.Action(1));}});

        ProtocolBodyOne.BodyOne bodyOne = POJOToProtobufFactory.convert(protocolBodyOneEntity, ProtocolBodyOne.BodyOne.class);

        logger.debug("----------------------------\n" + bodyOne.toString());

        ProtocolBodyOne.BodyOne build = ProtocolBodyOne.BodyOne.newBuilder()
                .setCorpus(ProtocolBodyOne.Corpus.NEWS)
                .setAction(ProtocolBodyOne.Action.newBuilder().setType(2).build())
                .setPage(3)
                .addFloatListTest(1)
                .addFloatListTest(2)
                .addCorpusListTest(ProtocolBodyOne.Corpus.IMAGES)
                .addActionListTest(ProtocolBodyOne.Action.newBuilder().setType(1).build())
                .build();
        logger.debug("----------------------------\n" + build.toString());
    }

    public static void testBuild() {
        ProtocolBodyOneEntity protocolBodyOneEntity = new ProtocolBodyOneEntity();
        protocolBodyOneEntity.setCorpus(ProtocolBodyOneEntity.Corpus.NEWS);

        protocolBodyOneEntity.setAction(new ProtocolBodyOneEntity.Action());

        protocolBodyOneEntity.setPage(3);

        protocolBodyOneEntity.setFloatListTest(new ArrayList<Float>() {{add((float) 1); add((float) 2);}});

        protocolBodyOneEntity.setCorpusListTest(new ArrayList<ProtocolBodyOneEntity.Corpus>() {{add(ProtocolBodyOneEntity.Corpus.IMAGES);}});

        protocolBodyOneEntity.setActionListTest(new ArrayList<ProtocolBodyOneEntity.Action>() {{add(new ProtocolBodyOneEntity.Action(2));}});

        logger.debug("----------------------------\n" + protocolBodyOneEntity.toString());

        ProtocolBodyOne.BodyOne build = ProtocolBodyOne.BodyOne.newBuilder()
                .setCorpus(ProtocolBodyOne.Corpus.NEWS)
                .setAction(ProtocolBodyOne.Action.newBuilder().setType(4).build())
                .setPage(3)
                .addFloatListTest(1)
                .addFloatListTest(2)
                .addCorpusListTest(ProtocolBodyOne.Corpus.IMAGES)
                .addActionListTest(ProtocolBodyOne.Action.newBuilder().setType(1).build())
                .build();
        logger.debug("----------------------------\n" + build.toString());
    }

    public static void testMultiNest() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i=0; i < 1000; i ++) {
            testNest();
//            TimeUnit.MILLISECONDS.sleep(50);
        }
        long end = System.currentTimeMillis();
        logger.debug("nest time is {}", end - start);
    }

    public static void testMultiBuild() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i=0; i < 1000; i ++) {
            testBuild();
//            TimeUnit.MILLISECONDS.sleep(50);
        }
        long end = System.currentTimeMillis();
        logger.debug("build time is {}", end - start);
    }
}