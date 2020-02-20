package fileManager;

import exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Alerts;
import person.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class Open implements FileOpener {
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
    public static ObservableList<Person> open(FileManager manager) throws IOException {
        ObservableList<Person> list = FXCollections.observableList(new ArrayList<>());

        FileChooser openFile = new FileChooser();
        File initialDirectory = new File(System.getProperty("user.dir"));
        openFile.setInitialDirectory(initialDirectory);

        File pathToFile = openFile.showOpenDialog(new Stage());

        if(pathToFile != null) {
            manager.setPath(pathToFile);
            manager.setSaved(true);
            switch(manager.getExtension()) {
                case ".txt":
                case ".csv":
                    try (BufferedReader reader = Files.newBufferedReader(manager.getPath())) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            Person person = reconstructPerson(line);
                            list.add(person);
                        }
                    }
                    break;

                case ".jobj":
                    try (InputStream inputStream = Files.newInputStream(manager.getPath());
                         ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                        ArrayList<Person> serializableList = (ArrayList<Person>) objectInputStream.readObject();
                        list.addAll(serializableList);
                    } catch (ClassNotFoundException e) {
                        manager.setSaved(false);
                        Alerts.invalidInput(e.getMessage());
                    }
                    break;
            }
            return list;
        }
        else{
            manager.setSaved(false);
            return Registry.getRegistry();
        }
    }
}
