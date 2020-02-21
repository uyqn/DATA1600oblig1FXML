package main;

import fileManager.FileManager;
import fileManager.FileOpener;
import fileManager.FileSaver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import person.Registry;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {
    public static FileManager fileManager = new FileManager();
    public static FileChooser fileChooser = new FileChooser();
    public static Stage tableViewStage;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Person register");
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);

        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);

        FileChooser.ExtensionFilter textExtensions =
                new FileChooser.ExtensionFilter("Plain Text File (*.txt)", "*.txt");
        FileChooser.ExtensionFilter csvExtensions =
                new FileChooser.ExtensionFilter("Comma Separated Values File (*.csv)", "*.csv");
        FileChooser.ExtensionFilter jobjExtensions =
                new FileChooser.ExtensionFilter("Binary java object (*.jobj)", "*.jobj");
        fileChooser.getExtensionFilters().addAll(textExtensions, csvExtensions, jobjExtensions);

        stage.setOnCloseRequest(closeRequest -> {
            closeRequest.consume();
            if(fileManager.isSaved()){
                FileSaver fileSaver = new FileSaver() {};
                try {
                    fileSaver.save(fileManager, Registry.getRegistry());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Platform.exit();
            }
            else{
                Alerts.saveBeforeExit();
            }
        });

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}