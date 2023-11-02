package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	
	public LoginModel loginModel = new LoginModel();
	private Stage stage;
	private Scene scene;
	
	public boolean firstTimeUser = true;
	private boolean returningUser = true;
	@FXML
	private TextField passwordField;
	@FXML
	private Button loginBtn;
	@FXML
	private Label dbStatus;
	@FXML
	private Label failMessage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
//		if(loginModel.isDBConnected()){
//			 dbStatus.setText("Connected!"); 
//		} else {
//			 dbStatus.setText("Not connected to DB"); }
	}
	
	public void loginBtnClicked(ActionEvent event) throws IOException {
			try {
				if (isValidLogin() && firstTimeUser && passwordField.getText().equals("p")){
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/reset.fxml"));
					scene = new Scene(fxmlLoader.load());
						
					stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.setTitle("Set password");
					stage.show();
					
				} else if(isValidLogin() && returningUser && !passwordField.getText().equals("p")){
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/homepage.fxml"));
					System.out.println(this.getClass().getResource("views/homepage.fxml"));
				    scene = new Scene(fxmlLoader.load());
					
					stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.setTitle("Main Window");
					stage.show();
				} else {
					failMessage.setText("Wrong password");
				}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isValidLogin() throws Exception {
		return loginModel.isLogin(passwordField.getText());
	}	
}
