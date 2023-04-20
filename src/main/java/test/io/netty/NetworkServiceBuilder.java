package test.io.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.WriteBufferWaterMark;
import test.io.netty.channel.ClientPackageMonitor;
import test.io.netty.channel.listener.MessageInboundEventListener;
import test.io.netty.msg.MessagePool;
import test.io.netty.msg.consumer.NetworkConsumer;

import java.util.ArrayList;
import java.util.List;

public class NetworkServiceBuilder {
    private int bossLoopGroupCount;
    private int workerLoopGroupCount;
    private int port;
    private MessagePool msgPool;
    private NetworkConsumer consumer;
    private MessageInboundEventListener messageInboundEventListener;
    private boolean webSocket;
    private boolean ssl;
    private String sslKeyCertChainFile;
    private String sslKeyFile;
    private int idleMaxTime;
    private int soRecBuf;
    private int soSendBuf;
    private WriteBufferWaterMark writeBufferWaterMark;
    private ClientPackageMonitor.MonitorOption clientPackageMonitorOption;
    private List<ChannelHandler> channelHandlerList;

    public NetworkServiceBuilder() {
        this.clientPackageMonitorOption = ClientPackageMonitor.DEFAULT_MONITOR_OPTION;
        this.channelHandlerList = new ArrayList();
    }

    public NetworkService createService() {
        return new NetworkService(this);
    }

    public void addChannelHandler(ChannelHandler handler) {
        if (handler == null) {
            throw new NullPointerException("指定的handler为空");
        } else {
            this.channelHandlerList.add(handler);
        }
    }

    public int getBossLoopGroupCount() {
        return this.bossLoopGroupCount;
    }

    public void setBossLoopGroupCount(int bossLoopGroupCount) {
        this.bossLoopGroupCount = bossLoopGroupCount;
    }

    public int getWorkerLoopGroupCount() {
        return this.workerLoopGroupCount;
    }

    public void setWorkerLoopGroupCount(int workerLoopGroupCount) {
        this.workerLoopGroupCount = workerLoopGroupCount;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<ChannelHandler> getChannelHandlerList() {
        return this.channelHandlerList;
    }

    public void setChannelHandlerList(List<ChannelHandler> channelHandlerList) {
        this.channelHandlerList = channelHandlerList;
    }

    public MessagePool getMsgPool() {
        return this.msgPool;
    }

    public void setMsgPool(MessagePool msgPool) {
        this.msgPool = msgPool;
    }

    public NetworkConsumer getConsumer() {
        return this.consumer;
    }

    public void setConsumer(NetworkConsumer consumer) {
        this.consumer = consumer;
    }

    public MessageInboundEventListener getNetworkEventlistener() {
        return this.messageInboundEventListener;
    }

    public void setNetworkEventlistener(MessageInboundEventListener messageInboundEventListener) {
        this.messageInboundEventListener = messageInboundEventListener;
    }

    public boolean isWebSocket() {
        return this.webSocket;
    }

    public void setWebSocket(boolean webSocket) {
        this.webSocket = webSocket;
    }

    public int getIdleMaxTime() {
        return this.idleMaxTime;
    }

    public void setIdleMaxTime(int idleMaxTime) {
        this.idleMaxTime = idleMaxTime;
    }

    public boolean isSsl() {
        return this.ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public String getSslKeyCertChainFile() {
        return this.sslKeyCertChainFile;
    }

    public void setSslKeyCertChainFile(String sslKeyCertChainFile) {
        this.sslKeyCertChainFile = sslKeyCertChainFile;
    }

    public String getSslKeyFile() {
        return this.sslKeyFile;
    }

    public void setSslKeyFile(String sslKeyFile) {
        this.sslKeyFile = sslKeyFile;
    }

    public int getSoRecBuf() {
        return this.soRecBuf;
    }

    public void setSoRecBuf(int soRecBuf) {
        this.soRecBuf = soRecBuf;
    }

    public int getSoSendBuf() {
        return this.soSendBuf;
    }

    public void setSoSendBuf(int soSendBuf) {
        this.soSendBuf = soSendBuf;
    }

    public ClientPackageMonitor.MonitorOption getClientPackageMonitorOption() {
        return this.clientPackageMonitorOption;
    }

    public void setClientPackageMonitorOption(ClientPackageMonitor.MonitorOption clientPackageMonitorOption) {
        this.clientPackageMonitorOption = clientPackageMonitorOption;
    }

    public WriteBufferWaterMark getWriteBufferWaterMark() {
        return this.writeBufferWaterMark;
    }

    public void setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        this.writeBufferWaterMark = writeBufferWaterMark;
    }
}
