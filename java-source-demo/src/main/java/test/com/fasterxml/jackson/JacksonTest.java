package test.com.fasterxml.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JacksonTest {
    
    public static ObjectMapper objectMapper;

    public static void main(String[] args) throws IOException {
//        test1();
        test2();
    }
    
    public static void test1() throws IOException {
        JacksonTestEntity<String> entity = new JacksonTestEntity<String>(0, "Byq", true);
        List<String> list = new ArrayList<String>();
        String content1 = "法律手段框架";
        list.add("{\"用户\":\"" + content1 + "\"}");
        System.out.println(list.get(0));
        String content2 = "阿斯蒂芬";
        list.add("{\"机器人\":\"" + content2 + "\"}");
        entity.setDialogue(list);
        String jsonStr = toJson(entity);
        System.out.println(jsonStr);
        JsonNode root = objectMapper.readTree(jsonStr);
        JsonNode dialogue = root.get("dialogue");
        System.out.println(dialogue);
        String userText = dialogue.get(0).asText();
        System.out.println(userText);
        String user = objectMapper.readTree(userText).get("用户").asText();
        System.out.println(user);
    }
    
    public static void test2() throws IOException {
        JacksonTestEntity<DialogueEntity> entity = new JacksonTestEntity<DialogueEntity>(0, "Byq", true);
        List<DialogueEntity> list = new ArrayList<DialogueEntity>();
        String content1 = "法律手段框架";
        list.add(new DialogueEntity("customer", content1, new Date()));
        String content2 = "阿斯蒂芬";
        list.add(new DialogueEntity("robot", content2, new Date()));
        entity.setDialogue(list);
        String jsonStr = toJson(entity);
        System.out.println(jsonStr);
        JsonNode root = objectMapper.readTree(jsonStr);
        JsonNode dialogue = root.get("dialogue");
        System.out.println(dialogue);
        String user = dialogue.get(0).get("user").asText();
        System.out.println(user);
//        String user = objectMapper.readTree(userText).get("用户").asText();
//        System.out.println(user);
    }
    
    public static String toJson(final Object object) throws JsonProcessingException {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper.writeValueAsString(object);
    }

}
