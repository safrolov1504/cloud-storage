package communication;


import networkCommunication.IService;
import networkCommunication.Network;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SendMessage {
    private IService messageService;
    private Network network;

    public SendMessage(IService messageService) {
        this.messageService = messageService;
        this.network = network;
    }

//    public void checkLogin(String login, String password) {
//        AuthMessage authMessage = new AuthMessage();
//        authMessage.login = login;
//        authMessage.password = password;
//        Message auth = Message.creatAuth(authMessage);
//
//        messageService.sendMessage(auth.toJson());
//    }

    public void sendFileFirst(){

    }
}
