package test.com.google.protobuf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by C on 2019/6/10.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolHeadEntity {
    private Type type;

    public enum Type {
        NONE(0),
        BODY_ONE(11),
        BODY_CHAT(12);

        private int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
