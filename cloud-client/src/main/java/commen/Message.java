package commen;

import com.google.gson.Gson;

public class Message {
    public CommandFirst commandFirst;

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Message fromJson(String json){
        return new Gson().fromJson(json, Message.class);
    }

    private static Message create(CommandFirst cmd) {
        Message m = new Message();
        m.commandFirst=cmd;
        return m;
    }


    public static Message creatSend(){
        Message m = create(CommandFirst.SEND);
        return m;
    }

    public static Message creatSendEnd(){
        Message m = create(CommandFirst.SEND_END);
        return m;
    }
}
