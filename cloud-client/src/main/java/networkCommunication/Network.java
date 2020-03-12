package networkCommunication;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {
    private final String serverAddress;
    private final int port;
    private final IService myServerDoctor;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Network(String serverAddress, int port, MyServerClient myServerDoctor) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.myServerDoctor = myServerDoctor;

        try {
            //it's first connection or not
            initNetworkState(serverAddress, port);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Connection is failed");
            alert.setContentText("Нет подключения в серверу");
            alert.showAndWait();
        }

    }

    //creat connection
    private void initNetworkState(String serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress,port);
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.inputStream = new DataInputStream(socket.getInputStream());

        //подключили и ждем сообщений
        getMessage();
    }

    public void getMessage(){
        new Thread(() ->{
            while (true){
                //waiting for message
                try {
                    String message = inputStream.readUTF();
                    Platform.runLater(() -> myServerDoctor.processRetrievedMessage(message));
                } catch (IOException e){
                    System.exit(0);
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
