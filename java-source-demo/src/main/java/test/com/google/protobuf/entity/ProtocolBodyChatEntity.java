package test.com.google.protobuf.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by C on 2019/6/10.
 */
@Data
@ToString
public class ProtocolBodyChatEntity {
    private int from;
    private int chatType;
    private int roomId;
    private List<Integer> to;
    private List<String> text;
}
