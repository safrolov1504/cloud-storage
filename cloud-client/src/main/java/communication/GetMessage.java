package communication;


import Controllers.Controller;
import networkCommunication.IService;

public class GetMessage {
    private IService messageService;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public GetMessage(IService messageService, Controller controller) {
        this.messageService = messageService;
        this.controller = controller;
    }

    public void sendMessageToWorkWith(String messageIn) {
        System.out.println(messageIn);

    }
}
