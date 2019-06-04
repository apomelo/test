package test.com.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by C on 2019/3/22.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topic_voice_log")
public class TopicVoiceLog {
    @Indexed
    private String sessionId;                       // 会话id

    private String responseSource;                  // 答案垂类
    private String questionText;                    // 问题文本
    private String questionGroupName;               // 问题声音文件存储组名称
    private String questionVoice;                   // 问题声音文件路径
    private Map<String, Integer> voiceAttrs;        // 语音属性
    private String answerText;                      // 答案文本
    private String answerType;                      // 答案类型
    private String answerGroupName;                 // 答案声音文件存储组名称
    private String answerVoice;                     // 答案声音文件路径
    private String matchedTopicId;                  // 匹配的标准问题id
    private String matchedTopicName;                // 匹配的标准问题内容
    private String isMultiTopic;                    // 是否是多轮
    private String userCodeId;                      // 用户id
    private String questionTypeId;                  // 问句类型id
    private String questionType;                    // 问句类型
    private Integer dialogueNumber;                 // 问答对轮次
    private Integer dialogueStartTime;              // 问答起始时间，用户提问的时间
    private Integer userAskTime;                    // 用户说话时间
    private Integer answerPlayTime;                 // 答案播报时间
    private Integer dialogueEndTime;                // 问答对结束时间，单轮结束时间
    private Integer dialogueState;                  // 会话状态
    private Integer voiceTransferCheckResult;       // 语音识别复核结果
    private String voiceTransferRightResult;        // 语音识别正确结果
    private Integer robotAnswerCheckResult;         // 机器人答案复核结果
    private String robotAnswerRightResult;          // 机器人答案正确结果
    private Integer checkFlag;                      // 问题复核状态
    private String checkUser;                       // 复核人
    private Integer updateTime;                     // 更新时间
    private Integer createTime;                     // 创建时间
}
