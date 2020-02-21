package main;

import exceptions.*;
import fileManager.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import person.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Label nameError, dobError, emailError, numberError, submissionResult;

    @FXML
    private TextField nameInput, dateInput, yearInput, emailInput, numberInput;

    @FXML
    private ChoiceBox<String> choiceMonth;

    @FXML
    void newRegistry(ActionEvent event) {
        if(App.fileManager.isSaved()){
            if(App.tableViewStage.isShowing()){
                App.tableViewStage.close();
            }
            try {
                FileSaver fileSaver = new FileSaver() {};
                fileSaver.save(App.fileManager, Registry.getRegistry());
            } catch (IOException e) {
                e.printStackTrace();
            }
            App.fileManager.setSaved(false);
            Registry.getRegistry().clear();

            resetInputs();
            resetErrors();
            submissionResult.setText("");
        }
        else if(Registry.getRegistry().isEmpty()){
            if(App.tableViewStage.isShowing()){
                App.tableViewStage.close();
            }
            resetInputs();
            resetErrors();
            submissionResult.setText("");
        }
        else{
            Alerts.newRegistryAlert();
            if(!Alerts.newRegistryCancelled) {
                resetInputs();
                resetErrors();
                submissionResult.setText("");
            }
        }
    }

    @FXML
    void openRegistry(ActionEvent event) {
        if(!Registry.getRegistry().isEmpty()){
            Alerts.addOrReplace();
        }
        else{
            try {
                FileOpener fileOpener = new FileOpener() {};
                fileOpener.open(App.fileManager);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidPersonException e){
                App.fileManager.setSaved(false);
            }
        }
    }

    @FXML
    void saveRegistry(ActionEvent event) {
        try {
            FileSaver fileSaver = new FileSaver() {};
            fileSaver.save(App.fileManager, Registry.getRegistry());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveRegistryAs(ActionEvent event) {
        FileSaver fileSaver = new FileSaver() {};
        fileSaver.saveAs(App.fileManager, Registry.getRegistry());
    }

    @FXML
    void submitPerson(ActionEvent event) {
        resetErrors();
        submissionResult.setText("");

        boolean exceptionDetected = false;

        Name name = null;
        try {
            name = new Name(nameInput.getText());
        } catch (InvalidNameException e) {
            nameError.setText(e.getMessage());
            exceptionDetected = true;
        }

        Birthdate dob = null;
        try {
            dob = new Birthdate(dateInput.getText() + ". " +
                    choiceMonth.getSelectionModel().getSelectedItem() + " " +
                    yearInput.getText());
        } catch (InvalidDateException e) {
            dobError.setText(e.getMessage());
            exceptionDetected = true;
        }

        Email email = null;
        try {
            email = new Email(emailInput.getText());
        } catch (InvalidEmailException e) {
            emailError.setText(e.getMessage());
            exceptionDetected = true;
        }

        PhoneNumber number = null;
        try {
            number = new PhoneNumber(numberInput.getText());
        } catch (InvalidPhoneException e){
            numberError.setText(e.getMessage());
            exceptionDetected = true;
        }

        if(!exceptionDetected){
            Person person = new Person(name, dob, email, number);
            if(!Registry.personExists(person)){
                Registry.getRegistry().add(person);
                submissionResult.setText(person.toString());
                resetInputs();
            }
            else{
                submissionResult.setText("Personen eksisterer fra før av!");
            }
        }
        else{
            submissionResult.setText("Registrering mislykkes! Rett opp i feil!");
        }

    }

    @FXML
    void showTableView(ActionEvent event) throws IOException {
        //Disse kodene lager ett nytt vindu med Tableview
        App.tableViewStage = new Stage();
        App.tableViewStage.setTitle("TableView");
        FXMLLoader tableViewFXML = new FXMLLoader(App.class.getResource("tableViewStage.fxml"));

        Scene tableViewScene = new Scene(tableViewFXML.load());

        App.tableViewStage.setScene(tableViewScene);
        App.tableViewStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceMonth.getItems().addAll(Birthdate.monthName);
    }

    @FXML
    public void resetInputs(){
        nameInput.setText("");
        dateInput.setText("");
        choiceMonth.setValue("");
        yearInput.setText("");
        emailInput.setText("");
        numberInput.setText("");
    }

    @FXML
    public void resetErrors(){
        nameError.setText("");
        dobError.setText("");
        emailError.setText("");
        numberError.setText("");
    }
}
