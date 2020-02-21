package fileManager;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Alerts;
import main.App;
import person.Person;

import java.io.File;
import java.io.IOException;

public interface FileSaver {
    default void save(FileManager manager, ObservableList<Person> registry) throws IOException {
        if(!manager.isSaved() || manager.getPath() == null){
            File saveToPath = App.fileChooser.showSaveDialog(new Stage());
            if(saveToPath != null) {
                manager.setPath(saveToPath);
                manager.setSaved(true);
                writeFile(manager, registry);
            }
        }
        else{
            writeFile(manager, registry);
        }
    }

    default void saveAs(FileManager manager, ObservableList<Person> registry){
        File saveToPath = App.fileChooser.showSaveDialog(new Stage());
        if(saveToPath != null){
            manager.setPath(saveToPath);
            manager.setSaved(true);
            writeFile(manager, registry);
        }
    }

    private void writeFile(FileManager manager, ObservableList<Person> registry){
        FileSaver saver = null;
        switch (manager.getExtension()){
            case ".txt":
            case ".csv":
                saver = new FileSaverCSV();
                break;
            case ".jobj":
                saver = new FileSaverJobj();
                break;
            default:
                Alerts.infoDialog("Denne filtypen (" + manager.getExtension() + ") er ikke tilgjengelig for lagring");
        }

        if(saver != null){
            try {
                saver.save(manager, registry);
                Alerts.infoDialog("Vellykket lagring!");
            } catch (IOException e) {
                Alerts.infoDialog("Lagring til fil feilet: \n" + e.getMessage());
            }
        }
    }
}
