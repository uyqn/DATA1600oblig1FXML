package fileManager;

import javafx.collections.ObservableList;
import person.Person;
import person.Registry;

import java.io.IOException;
import java.nio.file.Files;

public class FileSaverCSV implements FileSaver{
    @Override
    public void save(FileManager manager, ObservableList<Person> registry) throws IOException {
        StringBuilder listOfPeople = new StringBuilder();

        for(Person person : registry){
            listOfPeople.append(person.csvString()).append("\n");
        }

        Files.write(manager.getPath(), listOfPeople.toString().getBytes());
    }
}
