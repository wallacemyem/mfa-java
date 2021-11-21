package com.mfa.frames;

import com.mfa.util.ConnectionUtil;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField textEmail;
    
    @FXML
    private PasswordField textPassword;
    
    Stage dialogStage = new Stage();
    Scene scene;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public FXMLDocumentController() {
    connection = ConnectionUtil.connectdb();
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    String email = textEmail.getText().toString();
    String password = textPassword.getText().toString();
    
    String sql = "SELECT * FROM employee WHERE email = ? and password = ?";
    
    try{
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, email);
    preparedStatement.setString(2, password);
    resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()){
    infoBox("Enter Correct Email and Password", "Failed", null);
    }else{
    infoBox("Login Successfull", "Success", null);
    Node source = (Node) event.getSource();
    dialogStage = (Stage) source.getScene().getWindow();
    dialogStage.close();
    scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenu.fxml")));
    dialogStage.setScene(scene);
    dialogStage.show();
    }
    
    }catch(Exception e){
    e.printStackTrace();
    }
    }
    
    public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(titleBar);
    alert.setHeaderText(headerMessage);
    alert.setContentText(infoMessage);
    alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
}
