package test.io.netty.msg;

public abstract class AbstractMessage implements Message {
    private int length;
    protected short sequence;
    private int processorId;
    private Session session;
    private int hostId;
    private volatile byte[] content;

    public AbstractMessage() {
    }

    public short getSequence() {
        return this.sequence;
    }

    public void setSequence(short sequence) {
        this.sequence = sequence;
    }

    public void decode(byte[] bytes) {
    }

    public byte[] encode() {
        return this.content;
    }

    public int length() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getProcessorId() {
        return this.processorId;
    }

    public void setProcessorId(int processorId) {
        this.processorId = processorId;
    }

    public int getHostId() {
        return this.hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public String toString() {
        return "[id->" + this.getId() + ",sequence->" + this.sequence + "]";
    }
}
