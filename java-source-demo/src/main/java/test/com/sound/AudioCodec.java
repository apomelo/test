package test.com.sound;

import javax.sound.sampled.AudioFormat;

/**
 * Created by viruser on 2018/3/30.
 */
public class AudioCodec {

    public static final int DEFAULT_PAYLOAD_SIZE = 160;
    private int payloadType;
    private AudioFormat audioFormat;
    private int packetRate;
    private int payloadSize = DEFAULT_PAYLOAD_SIZE;

    public static final AudioCodec G711_MULAW = new AudioCodec(0, DEFAULT_PAYLOAD_SIZE, new AudioFormat(AudioFormat.Encoding.ULAW, 8000.0F, 16, 1, 160, 50.0F, false));
    public static final AudioCodec G711_ALAW = new AudioCodec(8, DEFAULT_PAYLOAD_SIZE, new AudioFormat(AudioFormat.Encoding.ALAW, 8000.0F, 16, 1, 160, 50.0F, false));
    public static final AudioCodec G729 = new AudioCodec(18, 20, new AudioFormat(new AudioFormat.Encoding("G729"), 8000.0F, 16, 1, 20, 50.0F, false));

    public AudioCodec(int payloadType, int payloadSize, AudioFormat audioFormat) {
        this.payloadType = payloadType;
        this.audioFormat = audioFormat;
        this.payloadSize = payloadSize;
        this.packetRate = packetRate();
    }

    public int packetRate() {
        return Double.valueOf(audioFormat.getFrameRate() * audioFormat.getFrameSize() / payloadSize).intValue();
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    @Override
    public String toString() {
        return "AudioCodec{" +
                "payloadType=" + payloadType +
                ", audioFormat=" + audioFormat +
                ", packetRate=" + packetRate +
                ", payloadSize=" + payloadSize +
                '}';
    }
}
