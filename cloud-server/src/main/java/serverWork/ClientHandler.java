package serverWork;


import communication.GetMessage;
import communication.SendMessage;
import workWithSQL.SQLServer;

import java.io.*;
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

    public DataInputStream getIn() {
        return in;
    }

    public void getMessage(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String clientMessage = in.readUTF();
                        //Message message = Message.fromJson(clientMessage);
                        //ниже тест!!!!
                        System.out.println("Вне цикла "+clientMessage);
                        getMessage.workWithInformation(clientMessage);


                       //recieveFile("test.txt");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void recieveFile(String filename) {
        try {
            long s;
            s = Long.parseLong(in.readUTF());
            System.out.println(s);
            System.out.println("File size: " + s);
            byte[] byteArray = new byte[1024];

            new File("Recieved").mkdir();
            File f = new File("./Recieved/" + filename);
            f.createNewFile();

            FileOutputStream fos = new FileOutputStream(f);
            int sp = (int)(s / 1024);
            if (s % 1024 != 0) sp++;
            BufferedInputStream bis = new BufferedInputStream(in);
            while (s > 0) {
                int i = bis.read(byteArray);
                fos.write(byteArray, 0, i);
                s-= i;
            }
            fos.close();
        } catch (IOException e) {
            System.err.println("Recieve IO Error");
        }
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
