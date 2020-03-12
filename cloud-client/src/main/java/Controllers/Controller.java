package Controllers;


import communication.SendMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import networkCommunication.IService;
import networkCommunication.MyServerClient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public @FXML AnchorPane window;
    //Login window
    public @FXML VBox loginBox;
    public @FXML TextField textField_login;
    public @FXML PasswordField testField_pass;
    public @FXML Button button_signIn;


    private IService messageService;
    private SendMessage sendMessage;

    public void shutdown() {
        //System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            this.messageService = new MyServerClient(this);
            //this.getMessage = new GetMessage(this.messageService,this);
            this.sendMessage = new SendMessage(this.messageService);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void button_signIn(ActionEvent actionEvent) {
    }
}
