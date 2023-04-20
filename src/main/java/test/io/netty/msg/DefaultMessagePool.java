package test.io.netty.msg;

import test.io.netty.msg.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

public class DefaultMessagePool implements MessagePool, HandlerPool {
    private Map<Integer, Class<? extends Message>> pool = new HashMap();
    protected Map<Integer, Class<? extends MessageHandler<? extends AbstractMessage>>> handlerPool = new HashMap();
    protected Map<Integer, Byte> queueIdMap = new HashMap();

    public DefaultMessagePool() {
    }

    public MessageHandler<? extends Message> getHandler(int messageId) {
        Class<? extends MessageHandler<? extends Message>> clazz = this.handlerPool.get(messageId);
        if (clazz != null) {
            byte queueId = this.queueIdMap.get(messageId);

            try {
                MessageHandler<? extends Message> handler = clazz.getDeclaredConstructor().newInstance();
                handler.setQueueId(queueId);
                return handler;
            } catch (Exception var5) {
                return null;
            }
        } else {
            return null;
        }
    }

    public Message getMessage(int messageId) {
        Class<? extends Message> clazz = this.pool.get(messageId);
        if (clazz != null) {
            try {
                return clazz.newInstance();
            } catch (Exception var4) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void register(Message message, Class<? extends MessageHandler<? extends AbstractMessage>> handlerClazz, byte queueId) {
        this.pool.put(message.getId(), message.getClass());
        this.queueIdMap.put(message.getId(), queueId);
        this.handlerPool.put(message.getId(), handlerClazz);
    }

    public void register(Message message, Class<? extends MessageHandler<? extends AbstractMessage>> handlerClazz) {
        this.register(message, handlerClazz, (byte) 0);
    }
}
