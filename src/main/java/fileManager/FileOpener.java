package fileManager;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Alerts;
import main.App;

import java.io.File;
import java.io.IOException;

public interface FileOpener {
    default void open(FileManager manager) throws IOException {
        File pathToFile = App.fileChooser.showOpenDialog(new Stage());

        if (pathToFile != null) {
            manager.setPath(pathToFile);
            manager.setSaved(true);
            readFile(manager);
        } else {
            manager.setSaved(false);
        }
    }

    private void readFile(FileManager manager){
        FileOpener opener = null;

        switch (manager.getExtension()){
            case ".txt":
            case ".csv":
                opener = new FileOpenerCSV();
                break;
            case ".jobj":
                opener = new FileOpenerJobj();
                break;
            default:
                Alerts.infoDialog("Denne filtypen (" + manager.getExtension() + ") er ikke tilgjengelig for lagring");
        }

        if(opener != null){
            try {
                opener.open(manager);
            } catch (IOException e) {
                Alerts.infoDialog("Åpning av fil feilet: \n" + e.getMessage());
            }
        }
    }
}
