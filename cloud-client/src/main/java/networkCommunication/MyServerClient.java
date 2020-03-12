package networkCommunication;

import Controllers.Controller;
import communication.GetMessage;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyServerClient implements IService{
    //for communication with server
    private static final String HOST_ADDRESS_PROP = "server.address";
    private static final String HOST_PORT_PROP = "server.port";
    private String hostAddress;
    private int hostPort;

    private Network network;
    private GetMessage getMessage;
    private Controller controller;

    public MyServerClient(Controller controller) {
        this.controller = controller;
        //есть какая то камуникация с контроллером 
        initialise();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void initialise() {
        readProperties();
        startConnectionToServer();
        this.getMessage = new GetMessage(this,controller);
    }

    private void readProperties() {
        Properties serverProperty = new Properties();
        try(InputStream inputStream = getClass().getResourceAsStream("/application.properties")){
            serverProperty.load(inputStream);
            hostAddress = serverProperty.getProperty(HOST_ADDRESS_PROP);
            hostPort = Integer.parseInt(serverProperty.getProperty(HOST_PORT_PROP));
        } catch (IOException e) {
            new RuntimeException("Failed to read application.properties file", e);
        }
    }

    private void startConnectionToServer() {
        this.network = new Network(hostAddress, hostPort, this);
    }

    @Override
    public void sendMessage(String message) {
        network.sendMessage(message);
    }

    @Override
    public void processRetrievedMessage(String message) {
        getMessage.sendMessageToWorkWith(message);
    }
}
