package test.com.fasterxml.jackson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogueEntity {
    
    private String user;
    private String chatText;
    private String chatTime;
    
    public DialogueEntity(String user, String chatText, Date chatTime) {
        this.user = user;
        this.chatText = chatText;
        this.chatTime = getChatTime(chatTime);
    }
    
    public String getChatTime(Date chatTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(chatTime);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    @Override
    public String toString() {
        return "DialogueEntity [user=" + user + ", chatText=" + chatText + ", chatTime=" + chatTime + "]";
    }

}
