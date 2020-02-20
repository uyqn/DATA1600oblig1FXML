package fileManager;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Alerts;
import person.Person;
import person.Registry;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class Save implements FileSaver{
    public static void save(FileManager manager, ObservableList<Person> list) throws IOException {
        StringBuilder listOfPeople = new StringBuilder();

        for(Person person : list){
            listOfPeople.append(person.csvString()).append("\n");
        }

        FileChooser choosePath = new FileChooser();
        File initialDirectory = new File(System.getProperty("user.dir"));
        choosePath.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter textExtensions =
                new FileChooser.ExtensionFilter("Plain Text File (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvExtensions =
                new FileChooser.ExtensionFilter("Comma Separated Values File (*.csv)", "*.csv");
        FileChooser.ExtensionFilter jobjExtensions =
                new FileChooser.ExtensionFilter("Binary java object (*.jobj)", "*.jobj");
        choosePath.getExtensionFilters().addAll(textExtensions, csvExtensions, jobjExtensions);

        if(!manager.isSaved() || manager.getPath() == null){
            File saveToPath = choosePath.showSaveDialog(new Stage());
            if(saveToPath != null) {
                manager.setPath(saveToPath);
                manager.setSaved(true);
                writeFile(manager, listOfPeople);
            }
        }else {
            writeFile(manager, listOfPeople);
        }
    }
    public static void saveAs(FileManager manager, ObservableList<Person> list) throws IOException {
        StringBuilder listOfPeople = new StringBuilder();

        for(Person person : list){
            listOfPeople.append(person.csvString()).append("\n");
        }

        FileChooser choosePath = new FileChooser();
        File initialDirectory = new File(System.getProperty("user.dir"));
        choosePath.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter textExtensions = new FileChooser.ExtensionFilter("Plain Text File (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvExtensions = new FileChooser.ExtensionFilter("Comma Separated Values File (*" +
                ".csv)", "*.csv");
        FileChooser.ExtensionFilter jobjExtensions =
                new FileChooser.ExtensionFilter("Binary java object (*.jobj)", "*.jobj");
        choosePath.getExtensionFilters().addAll(textExtensions, csvExtensions, jobjExtensions);

        File pathToSave = choosePath.showSaveDialog(new Stage());
        if (pathToSave != null) {
            manager.setPath(pathToSave);
            manager.setSaved(true);
            writeFile(manager, listOfPeople);
        }
    }
    private static void writeFile(FileManager manager, StringBuilder listOfPeople) throws IOException {
        switch (manager.getExtension()){
            case ".txt":
            case ".csv":
                Files.write(manager.getPath(), listOfPeople.toString().getBytes());
                break;
            case ".jobj":
                try (OutputStream outputStream = Files.newOutputStream(manager.getPath());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                     objectOutputStream.writeObject(Registry.getSerializableList());
                }
                break;
            default:
                Alerts.invalidInput("Denne filtypen (" + manager.getExtension() + ") er ikke tilgjengelig for lagring");
        }
    }
}
