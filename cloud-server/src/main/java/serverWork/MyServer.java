package serverWork;



import workWithSQL.BaseSQLServer;
import workWithSQL.SQLServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class MyServer {
    private static final int PORT = 8189;
    private final SQLServer sqlServer = new BaseSQLServer();

    public MyServer() {
        System.out.println("Server is running");
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            //BD starts
            sqlServer.start();

            while (true){
                System.out.println("Waiting for client!!");
                Socket socket = serverSocket.accept();
                System.out.println("Client is connected");
                new ClientHandler(socket,this,sqlServer);
            }

        } catch (IOException e) {
            System.err.println("Error server. Reason: "+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL error: "+e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                sqlServer.stop();
            } catch (SQLException e) {
                System.out.println("SQL error: "+e.getMessage());
            }
        }
    }
}
