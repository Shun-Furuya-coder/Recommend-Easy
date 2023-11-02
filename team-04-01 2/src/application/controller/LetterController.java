package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import dbUtil.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LetterController implements Initializable{
	
	
	@FXML
    private Button returnBtn;
	@FXML
	private DatePicker dateInput;
	@FXML
	private Scene scene;
	@FXML
	private BorderPane rootElement;
	@FXML 
	private ComboBox<String> gender;
	@FXML
	private ComboBox<String> program;
	@FXML
	private ComboBox<String> semester;
	@FXML
	private ComboBox<String> pChar;
	@FXML
	private ComboBox<String> aChar;
	@FXML
	private ComboBox<String> course;
	@FXML
    private TextArea semesterSelection;
    @FXML
    private Button addSemester;
    @FXML
    private TextField addYear;
    @FXML
    private TextArea courseSelection;
    @FXML
    private TextField grade , firstNameInput , lastNameInput , targetSchoolInput ;
    @FXML
    private TextArea pCharSelection;
    @FXML
    private TextArea aCharSelection;
    @FXML
    private TextArea recommendationTextArea;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gender.getItems().addAll(Utility.getProperty("Gender").split(","));
		program.getItems().addAll(Utility.getProperty("Program").split(","));
		semester.getItems().addAll(Utility.getProperty("Semester").split(","));
		pChar.getItems().addAll(Utility.getProperty("PersonalChar").split(","));
		aChar.getItems().addAll(Utility.getProperty("AcademicChar").split(","));
		course.getItems().addAll(Utility.getProperty("Course").split(","));

		if(LetterViewController.letter!=null) {
			RecommendationLetter letter = LetterViewController.letter;
			firstNameInput.setText(letter.getFirstName());
			lastNameInput.setText(letter.getLastName());
			gender.getSelectionModel().select(letter.getGender());
			targetSchoolInput.setText(letter.getTargetSchool());
			dateInput.setValue(letter.getDate());
			program.getSelectionModel().select(letter.getProgramName());
			for(String semester : letter.getSemesters()) {
				semesterSelection.appendText(semester+"\n");
			}
			for(String course : letter.getCourseInfo()) {
				courseSelection.appendText(course+"\n");
			}
			for(String personal : letter.getPersonalChar()) {
				pCharSelection.appendText(personal+"\n");
			}
			for(String academic : letter.getAcademicChar()) {
				aCharSelection.appendText(academic+"\n");
			}

		}
	}
	
	@FXML
	void addSemester(ActionEvent event) throws IOException {
		semesterSelection.appendText(String.valueOf(semester.getValue() + ", " + addYear.getText() + "\n"));
	}
	
	@FXML
	void addCourse(ActionEvent event) throws IOException {
		courseSelection.appendText(String.valueOf(course.getValue() + ", " + grade.getText() + "\n"));
	}

	@FXML
	void addAcademic(ActionEvent event) throws IOException {
		aCharSelection.appendText(String.valueOf(aChar.getValue() + "\n"));
	}
	
	@FXML
	void addPersonal(ActionEvent event) throws IOException {
		pCharSelection.appendText(String.valueOf(pChar.getValue() + "\n"));
	}
	
	@FXML
    void returnToHomepage(ActionEvent event) throws IOException {
		returnBtn.getScene().getWindow().hide();
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/homepage.fxml"));
	    scene = new Scene(fxmlLoader.load());
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Main Window");
		stage.show();
    }

	@FXML
	private void compileLetter() throws Exception {

		String firstName = this.firstNameInput.getText();
		String lastName = this.lastNameInput.getText();
		// String fullName = this.getFullName();
		LocalDate date = this.dateInput.getValue();
		String programName = program.getSelectionModel().getSelectedItem();
		String firstSemester = semester.getSelectionModel().getSelectedItem();
		String firstGrade = this.courseSelection.getText().split("\n")[0].split(",")[1];
		String gender = this.gender.getSelectionModel().getSelectedItem();
		List<String> courseInfo , academicChar , personalChar;
		courseInfo = Arrays.asList(this.courseSelection.getText().split("\n"));
		academicChar = Arrays.asList(this.aCharSelection.getText().split("\n"));
		personalChar = Arrays.asList(this.pCharSelection.getText().split("\n"));


		RecommendationLetter letter = new RecommendationLetter(firstName, lastName, date, gender, programName, firstSemester ,firstGrade ,courseInfo ,academicChar ,personalChar);
		letter.setTargetSchool(this.targetSchoolInput.getText());
		letter.setSemesters(Arrays.asList(this.semesterSelection.getText().split("\n")));
		LetterViewController.letter = letter;
		Parent letterView = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/letter.fxml"));
		Scene scene = new Scene(letterView , 600 , 462);
		Stage backStage = (Stage)this.rootElement.getScene().getWindow();
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		backStage.close();
		stage.show();



	}
	
	/*
	 * void saveRecommendatisheon(ActionEvent event) throws IOException { String
	 * fullName = getFullName(); }
	 */

	public String getFullName() {
		return firstNameInput.getText() + lastNameInput.getText();
	}
	
	
	
	
}