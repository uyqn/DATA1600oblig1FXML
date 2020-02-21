package fileManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Alerts;
import person.Person;
import person.Registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileOpenerJobj implements FileOpener {
    @Override
    public void open(FileManager manager){
        ObservableList<Person> list = FXCollections.observableArrayList();

        try (InputStream inputStream = Files.newInputStream(manager.getPath());
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            ArrayList<Person> serializableList = (ArrayList<Person>) objectInputStream.readObject();
            list.addAll(serializableList);
        } catch (ClassNotFoundException | IOException e) {
            manager.setSaved(false);
            Alerts.infoDialog(e.getMessage());
        }
        Registry.replaceWith(list);
    }
}
