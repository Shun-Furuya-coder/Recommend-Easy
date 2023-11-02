package application.controller;


import application.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetController {
	
	private LoginModel loginModel;
	// private LoginController loginController;
	@FXML
	private TextField defaultPass;
	@FXML
	private Scene preScene;
	@FXML
	private TextField newPass;
	@FXML
	private Label successMsg;
	@FXML
	private Button confirmBtn;
	
	
	public ResetController() {
		loginModel = new LoginModel();
	}
	
	public void updatePasswordBtn(ActionEvent event) throws Exception{


		String defaultPassword = defaultPass.getText();
		String newPassword = newPass.getText();

		if(!loginModel.isLogin(defaultPassword)) {
			successMsg.setText("Old password doesn't match !");
		}
		else if(newPassword.equals(defaultPassword)) {
			successMsg.setText("Choose a different password.");

		}
		else if(newPassword.isEmpty()) {
			successMsg.setText("new password cannot be blank !");
		}
		else{
			loginModel.updatePassword(newPassword);
			successMsg.setText("Password change successful!");

			confirmBtn.getScene().getWindow().hide();

			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
			Scene scene = new Scene(root,500,500);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Log In");
			stage.show();
		}
		
	}
}
