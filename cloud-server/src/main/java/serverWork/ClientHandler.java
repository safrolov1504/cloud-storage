package serverWork;


import communication.GetMessage;
import communication.SendMessage;
import workWithSQL.SQLServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private final Socket socket;
    private final MyServer myServer;
    private final DataInputStream in;
    private final DataOutputStream out;

    private GetMessage getMessage;
    private SendMessage sendMessage;

    public ClientHandler(Socket socket, MyServer myServer, SQLServer sqlServer) throws IOException {
        //creat the connection
        this.socket = socket;
        this.myServer = myServer;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.sendMessage = new SendMessage(this);
        this.getMessage = new GetMessage(this,sqlServer,sendMessage);


        //connection was created. Start to work with getting message

        getMessage();
    }

    public void getMessage(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String clientMessage = in.readUTF();
                        //ниже тест!!!!
                        getMessage.workWithInformation(clientMessage);
                        //System.out.println(clientMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessage(String message)  {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            //MyServer.admin.error("Failed to send message to user " + clientName + ": "+ message);
            //System.err.println("Failed to send message to user " + clientName + ": "+ message);
            e.printStackTrace();
        }
    }

    private void CloseConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
