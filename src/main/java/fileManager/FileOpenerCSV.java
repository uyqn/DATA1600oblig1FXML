package fileManager;

import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import person.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

public class FileOpenerCSV implements FileOpener {

    @Override
    public void open(FileManager manager) throws IOException {
        ObservableList<Person> list = FXCollections.observableArrayList();

        try (BufferedReader reader = Files.newBufferedReader(manager.getPath())) {
            String line;

            while ((line = reader.readLine()) != null) {
                Person person = reconstructPerson(line);
                list.add(person);
            }
        }
        Registry.replaceWith(list);
    }

    private static Person reconstructPerson(String csv) throws InvalidPersonException {
        String[] attributes = csv.split(Person.getDELIMETER());

        Name name;
        try{
            name = new Name(attributes[0]);
        }
        catch (InvalidNameException e){
            throw new InvalidPersonException(e.getMessage());
        }

        Birthdate birth;
        try{
            birth = new Birthdate(attributes[2]);
        } catch (InvalidDateException e){
            throw new InvalidPersonException(e.getMessage());
        }

        Email mail;
        try {
            mail = new Email(attributes[3]);
        }catch (InvalidEmailException e){
            throw new InvalidPersonException(e.getMessage());
        }

        PhoneNumber number;
        try{
            number = new PhoneNumber(attributes[4]);
        } catch (InvalidPhoneException e){
            throw new InvalidPersonException(e.getMessage());
        }

        return new Person(name, birth, mail, number);
    }
}
