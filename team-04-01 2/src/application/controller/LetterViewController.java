package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;


public class LetterViewController implements Initializable{

    public static RecommendationLetter letter;

    @FXML
    private TextArea letterTextArea;

    @FXML
    private AnchorPane rootElement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        letterTextArea.setText(letter.compileTemplate());
        letterTextArea.setWrapText(true);
    }

    @FXML
    private void save() throws IOException {

        String edited = letterTextArea.getText();
       // String draft = letter.compileTemplate();

        File file = new File("./letters");
        if(!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }

        FileWriter fw = null;
        BufferedWriter writer = null;

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream("./letters/"+letter.getLastName().concat("-draft.ser"));
            oos = new ObjectOutputStream(fos);

            oos.writeObject(letter);

            fos.flush();
            oos.flush();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if(fos!=null) fos.close();
            if(oos!=null) oos.close();
        }

        try {
            fw = new FileWriter("./letters/"+letter.getLastName().concat("-edited.txt"));
            writer = new BufferedWriter(fw);

            writer.write(edited+"\n");
            fw.flush();
            writer.flush();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if(fw!=null) fw.close();
            if(writer!=null) writer.close();
        }

        Parent homePage = FXMLLoader.load(this.getClass().getClassLoader().getResource("views/homepage.fxml"));
        Scene scene = new Scene(homePage , 600 , 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        ((Stage)rootElement.getScene().getWindow()).close();

    }


}
