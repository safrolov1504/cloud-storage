package communication;


import networkCommunication.IService;

public class SendMessage {
    private IService messageService;

    public SendMessage(IService messageService) {
        this.messageService = messageService;
    }

//    public void checkLogin(String login, String password) {
//        AuthMessage authMessage = new AuthMessage();
//        authMessage.login = login;
//        authMessage.password = password;
//        Message auth = Message.creatAuth(authMessage);
//
//        messageService.sendMessage(auth.toJson());
//    }


}
