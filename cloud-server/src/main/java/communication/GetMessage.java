package communication;

import serverWork.ClientHandler;
import workWithSQL.BaseSQLServer;
import workWithSQL.RequirementSQL;
import workWithSQL.SQLServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;


public class GetMessage {
    private ClientHandler clientHandler;
    private RequirementSQL requirementSQL;
    private SendMessage sendMessage;

    public GetMessage(ClientHandler clientHandler, SQLServer sqlServer, SendMessage sendMessage) {
        this.clientHandler = clientHandler;
        this.requirementSQL = new RequirementSQL((BaseSQLServer) sqlServer);
        this.sendMessage = sendMessage;
    }

    public void workWithInformation(String clientMessage) throws SQLException, IOException {
            //System.out.println(clientMessage);

            byte [] byteArray = new byte[1024];
            int i =0;
            if(clientMessage.equals("SEND")){
                while (!clientMessage.equals("SEND_END")){
                    clientMessage = clientHandler.getIn().readUTF();
                        if(!clientMessage.equals("SEND_END")) {
                            //System.out.println("В цикле: " + clientMessage);
                            byteArray[i] = Byte.parseByte(clientMessage);
                            i++;
                        }
                    }
            }
        System.out.println(Arrays.toString(byteArray));
            createFile(byteArray);
    }

    private void createFile(byte[] byteArray) throws IOException {


        byte [] tmpByteArray;
        String nameFile;
        byte lengthName = byteArray[0];
        byte lengthFile = byteArray [1];


        tmpByteArray = new byte[lengthName];
        for (int i = 0; i < lengthName; i++) {
            tmpByteArray[i] = byteArray[2+i];
        }
        nameFile = new String(tmpByteArray);

        System.out.println(nameFile);

        File f = new File("cloud-server/src/Recieved/" + nameFile);
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);

        tmpByteArray = new byte[lengthFile];
        for (int i = 0; i < lengthFile; i++) {
            tmpByteArray[i] = byteArray[2+lengthName+i];
        }

        fos.write(tmpByteArray);
        fos.close();
    }
}

