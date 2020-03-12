package communication;


import serverWork.ClientHandler;
import workWithSQL.BaseSQLServer;
import workWithSQL.RequirementSQL;
import workWithSQL.SQLServer;

import java.sql.SQLException;


public class GetMessage {
    private ClientHandler clientHandler;
    private RequirementSQL requirementSQL;
    private SendMessage sendMessage;

    public GetMessage(ClientHandler clientHandler, SQLServer sqlServer, SendMessage sendMessage) {
        this.clientHandler = clientHandler;
        this.requirementSQL = new RequirementSQL((BaseSQLServer) sqlServer);
        this.sendMessage = sendMessage;
    }

    public void workWithInformation(String clientMessage) throws SQLException {
            System.out.println(clientMessage);

    }
}
