package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	private Stage loginStage;

	@FXML
	private TextField lastNameInput;

	@FXML
	private ListView<String> existingLetters;

	@FXML
	private HBox rootElement;

	@FXML
	private Button logout , editButton , deleteButton;

	private List<String> names = new ArrayList<String>();
	public void logoutClicked(ActionEvent event) throws IOException{
		
		logout.getScene().getWindow().hide();
		
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
		Scene scene = new Scene(root,500,500);

		loginStage = new Stage();
		loginStage.setScene(scene);
		loginStage.setTitle("Log in");
		loginStage.show();
	}
	
	@FXML
    void createNewLetter(ActionEvent event) throws IOException {
		
		logout.getScene().getWindow().hide();
		
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/data.fxml"));
		Scene scene = new Scene(root,600,600);

		Stage dataStage = new Stage();
		dataStage.setScene(scene);
		dataStage.setTitle("Details Page");
		dataStage.show();
    }

	private void fillList() {

		this.existingLetters.setPlaceholder(new Label("No record !"));

		File file = new File("./letters");
		if(!file.exists() || !file.isDirectory()) {
			return;
		}

		File[] list = file.listFiles();

		for(int i=0; i<list.length; i++) {

			File currentFile = list[i];
			String currentFileName = currentFile.getName();

			if(currentFileName.endsWith(".ser")) continue;

			String lastName = currentFileName.split("-edited.")[0];

			this.existingLetters.getItems().add(lastName);
			this.names.add(lastName);

		}
	}

	@FXML
	private void userEnterName() {

		String userInput = this.lastNameInput.getText();
		this.existingLetters.getItems().clear();
		if(userInput.isEmpty()) {

			this.existingLetters.getItems().addAll(names);
			return;
		}

		for(int i=0; i<names.size(); i++) {

			String currentName = (String)names.get(i);

			if(currentName.toLowerCase().contains(userInput.toLowerCase())) {
				this.existingLetters.getItems().add(currentName);
			}
		}
	}

	@FXML
	private void editLetter() throws Exception {



		String selectedName = (String)this.existingLetters.getSelectionModel().getSelectedItem();

		if(selectedName==null) {
			return;
		}

		File file = new File("./letters/"+selectedName+"-draft.ser");
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			LetterViewController.letter = (RecommendationLetter)ois.readObject();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if(fis!=null) fis.close();
			if(ois!=null) ois.close();
		}

		Parent parent = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/data.fxml"));
		Scene scene = new Scene(parent , 600 , 600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();

		((Stage)this.rootElement.getScene().getWindow()).close();
	}

	@FXML
	private void deleteLetter() {

		String selectedName = (String)this.existingLetters.getSelectionModel().getSelectedItem();

		File file = new File("./letters/"+selectedName+"-draft.ser");
		File file2 = new File("./letters/"+selectedName+"-edited.txt");

		file.delete();
		file2.delete();
		this.existingLetters.getItems().remove(selectedName);
		this.names.remove(selectedName);
	}

	@FXML
	private void updatePassword() throws Exception {

		Parent reset = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/reset.fxml"));

		Scene scene = new Scene(reset , 500 , 500);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();

		((Stage)this.rootElement.getScene().getWindow()).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		this.fillList();

		this.existingLetters.getSelectionModel().selectedItemProperty().addListener((obv , oldValue , newValue) -> {
			if(newValue==null) {

				this.deleteButton.setDisable(true);
				this.editButton.setDisable(true);
			}
			else {
				this.deleteButton.setDisable(false);
				this.editButton.setDisable(false);
			}
		});

	}

}
