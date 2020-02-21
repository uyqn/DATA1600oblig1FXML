package fileManager;

import javafx.collections.ObservableList;
import person.Person;
import person.Registry;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileSaverJobj implements FileSaver {
    @Override
    public void save(FileManager manager, ObservableList<Person> registry) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(manager.getPath());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(new ArrayList<>(Registry.getRegistry()));
        }
    }
}
