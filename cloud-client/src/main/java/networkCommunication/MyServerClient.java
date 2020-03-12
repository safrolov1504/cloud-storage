package networkCommunication;

import Controllers.Controller;
import commen.Message;
import communication.GetMessage;


import java.io.*;
import java.util.Arrays;
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

    public Network getNetwork() {
        return network;
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
        sendFile();
    }

    private void sendFileNew(File f) {
        try {
            byte[] byteArray = new byte[1024];
            FileInputStream fis = new FileInputStream(f.getPath());
            long s;
            s = f.length();

            int sp = (int)(s / 1024);
            if (s % 1024 != 0) sp++;
            BufferedOutputStream bos = new BufferedOutputStream(network.getOutputStream());
            Thread.sleep(500);
            while (s > 0) {
                int i = fis.read(byteArray);
                bos.write(byteArray, 0, i);
                s-= i;
            }
            bos.flush();
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (IOException e) {
            System.err.println("IOException");
        } catch (Exception e) {

        }
    }

    @Override
    public void sendMessage(String message) {
        //пока здесь отправялем сообщения
        sendFile();

        //network.sendMessage(message);
    }

    private void sendFile() {
        File file = new File("cloud-client/test.txt");
        //File file = new File("cloud-client/unnamed.jpg");

        System.out.println("send file: " + file.getName());

        //Message message = Message.creatSend();
        //отправялем команду
        network.sendMessage("SEND");

        try {
            FileInputStream fileInputStream = new FileInputStream(file.getPath());
            byte[] byteArray = new byte[1024];
            byte[] tmpByteArray;

            String nameFile = file.getName();
            System.out.println(file.length());

            byteArray[0] = (byte) nameFile.length();
            byteArray [1] = (byte) file.length();

            // переводим в байты название файла
            tmpByteArray = new byte[byteArray[0]];
            tmpByteArray = nameFile.getBytes();

            for (int i = 0; i < byteArray[0]; i++) {
                byteArray[2+i] = tmpByteArray[i];
            }

            System.out.println(Arrays.toString(tmpByteArray));
            System.out.println(Arrays.toString(byteArray));

            //переделываем файл в байты
            tmpByteArray = new byte[byteArray[1]];
            fileInputStream.read(tmpByteArray);
            for (int i = 0; i < byteArray[1]; i++) {
                byteArray[2+byteArray[0]+i] = tmpByteArray[i];
            }

            System.out.println(Arrays.toString(tmpByteArray));
            System.out.println(Arrays.toString(byteArray));

            for (int i = 0; i < byteArray.length; i++) {
                network.sendMessage(byteArray[i]);
            }

            network.sendMessage("SEND_END");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processRetrievedMessage(String message) {
        getMessage.sendMessageToWorkWith(message);
    }

}
