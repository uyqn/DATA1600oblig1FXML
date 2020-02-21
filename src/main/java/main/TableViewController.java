package main;

import exceptions.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import person.Person;
import person.Registry;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TableViewController implements Initializable {

    @FXML
    private TableView<Person> tableViewTable;

    @FXML
    private TableColumn<Person, String> nameColumn;

    @FXML
    private TableColumn<Person, String> ageColumn;

    @FXML
    private TableColumn<Person, String> dobColumn;

    @FXML
    private TableColumn<Person, String> emailColumn;

    @FXML
    private TableColumn<Person, String> numberColumn;

    @FXML
    private ChoiceBox<String> filterByChoice;

    @FXML
    private TextField filterField;

    @FXML
    void nameEdit(TableColumn.CellEditEvent<Person, String> edit){
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        try {
            person.setName(edit.getNewValue());
        }catch (InvalidNameException e){
            if (!edit.getNewValue().isBlank()) {
                Alerts.infoDialog(e.getMessage());
            }
            person.setName(edit.getOldValue());
        }
        tableViewTable.refresh();
    }

    @FXML
    void ageEdit(TableColumn.CellEditEvent<Person, String> edit){
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        try {
            int age = Integer.parseInt(edit.getNewValue());
            person.setAge(age);
        }catch (InvalidAgeException e){
            if (edit.getNewValue() != null) {
                Alerts.infoDialog(e.getMessage());
            }
            person.setAge(Integer.parseInt(edit.getOldValue()));
        }
        tableViewTable.refresh();
    }

    @FXML
    void dobEdit(TableColumn.CellEditEvent<Person, String> edit){
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        try {
            person.setBirthdate(edit.getNewValue());
        }catch (InvalidDateException e){
            if (!edit.getNewValue().isBlank()) {
                Alerts.infoDialog(e.getMessage());
            }
            person.setBirthdate(edit.getOldValue());
        }
        tableViewTable.refresh();
    }

    @FXML
    void emailEdit(TableColumn.CellEditEvent<Person, String> edit){
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        try {
            person.setEmail(edit.getNewValue());
        }catch (InvalidEmailException e){
            if (!edit.getNewValue().isBlank()) {
                Alerts.infoDialog(e.getMessage());
            }
            person.setEmail(edit.getOldValue());
        }
        tableViewTable.refresh();
    }

    @FXML
    void numberEdit(TableColumn.CellEditEvent<Person, String> edit){
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        try {
            person.setNumber(edit.getNewValue());
        }catch (InvalidDateException e){
                Alerts.infoDialog(e.getMessage());
                person.setBirthdate(edit.getOldValue());
        }
        tableViewTable.refresh();
    }

    @FXML
    void closeTableView(ActionEvent event) {
        App.tableViewStage.close();
    }

    @FXML
    void removeSelectedPerson(ActionEvent event) {
        Person person = tableViewTable.getSelectionModel().getSelectedItem();
        Registry.getRegistry().remove(person);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterByChoice.getItems().addAll("Navn",
                "Alder",
                "Fødselsdato",
                "Epost",
                "Telefonnummer");
        filterByChoice.setValue("Navn");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        dobColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        dobColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        filterField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String searchText = newValue.toLowerCase().trim();

            tableViewTable.setItems(
                    Registry.getRegistry().stream().filter(person -> {
                        if(searchText.isBlank() || searchText.isEmpty()){
                            return true;
                        }
                        else {
                            switch (filterByChoice.getSelectionModel().getSelectedItem()){
                                case "Navn":
                                    return person.getName().toLowerCase().contains(searchText);
                                case "Fødselsdato":
                                    return person.getBirthdate().toLowerCase().contains(searchText);
                                case "Alder":
                                    return person.getAge().equals(searchText);
                                case "Epost":
                                    return person.getEmail().toLowerCase().contains(searchText);
                                case "Telefonnummer":
                                    return person.getNumber().toLowerCase().contains(searchText);
                            }
                        }
                        return false;
                    }
            ).collect(Collectors.toCollection(FXCollections::observableArrayList)));
        });

        tableViewTable.setItems(Registry.getRegistry());
        tableViewTable.setEditable(true);
    }
}
