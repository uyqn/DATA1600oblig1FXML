package main;

import exceptions.InvalidPersonException;
import fileManager.FileOpener;
import fileManager.FileSaver;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import person.Registry;

import java.io.IOException;

import static javafx.geometry.Pos.CENTER;

public class Alerts {
    public static boolean newRegistryCancelled = true;
    public static void addOrReplace(){
        Stage addOrReplaceAlert = new Stage();
        addOrReplaceAlert.initModality(Modality.APPLICATION_MODAL);
        Label alert = new Label();
        alert.setText("Registeret ditt er ikke tom!\n" +
                "Ønsker du å erstatte din nåværende register eller \n" +
                "legge til din nåværende register?");

        Button replace = new Button("Erstatt");
        Button add = new Button("Legg til");
        Button cancel = new Button("Avbryt");

        replace.setOnAction(actionEvent -> {
            try {
                FileOpener fileOpener = new FileOpener() {};
                fileOpener.open(App.fileManager);
            } catch (IOException | InvalidPersonException e) {
                App.fileManager.setSaved(false);
            }
            addOrReplaceAlert.close();
        });

        add.setOnAction(actionEvent -> {
            try {
                FileOpener fileOpener = new FileOpener() {};
                fileOpener.open(App.fileManager);
            } catch (IOException | InvalidPersonException e) {
                App.fileManager.setSaved(false);
            }
            addOrReplaceAlert.close();
        });

        cancel.setOnAction(actionEvent -> {
            addOrReplaceAlert.close();
            newRegistryCancelled = true;
        });

        VBox vLayout = new VBox(5);
        vLayout.setAlignment(CENTER);
        vLayout.getChildren().add(alert);

        HBox hLayout = new HBox(5);
        hLayout.setAlignment(CENTER);
        hLayout.getChildren().addAll(replace, add, cancel);

        vLayout.getChildren().add(hLayout);

        Scene scene = new Scene(vLayout, 300, 200);
        addOrReplaceAlert.setScene(scene);
        addOrReplaceAlert.show();
    }
    public static void newRegistryAlert(){
        Stage newRegistryAlert = new Stage();

        newRegistryAlert.initModality(Modality.APPLICATION_MODAL);

        Label message = new Label("Registeret ditt er ikke tom!\n" +
                "vil du lagre din eksisterende register\n" +
                "før du starter en ny register?");

        Button yes = new Button("Lagre");
        yes.setMinWidth(30);
        yes.setOnAction(actionEvent -> {
            FileSaver fileSaver = new FileSaver(){};
            fileSaver.saveAs(App.fileManager, Registry.getRegistry());
            if(App.tableViewStage.isShowing()){
                App.tableViewStage.close();
            }
            App.fileManager.setSaved(false);
            Registry.getRegistry().clear();
        });

        Button no = new Button("Ikke Lagre");
        no.setMinWidth(30);
        no.setOnAction(actionEvent -> {
            if(App.tableViewStage.isShowing()) {
                App.tableViewStage.close();
            }
            Registry.getRegistry().clear();
            newRegistryAlert.close();
        });

        Button cancel = new Button ("Avbryt");
        cancel.setMinWidth(30);
        cancel.setOnAction(actionEvent -> newRegistryAlert.close());

        HBox options = new HBox(10);
        options.setSpacing(10);
        options.setAlignment(CENTER);
        options.getChildren().addAll(yes, no, cancel);

        VBox layout = new VBox(10);
        layout.setAlignment(CENTER);
        layout.getChildren().addAll(message, options);

        Scene scene = new Scene(layout, 300,150);
        newRegistryAlert.setScene(scene);
        newRegistryAlert.show();
    }
    public static void infoDialog(String errorMessage){
        Stage invalidInputAlert = new Stage();
        invalidInputAlert.setTitle("Ugyldig input!");
        invalidInputAlert.initModality(Modality.APPLICATION_MODAL);

        Label error = new Label(errorMessage);
        Button close = new Button("Lukk");

        close.setOnAction(actionEvent -> invalidInputAlert.close());

        VBox layout = new VBox(10);
        layout.setAlignment(CENTER);
        layout.getChildren().addAll(error, close);

        Scene scene = new Scene(layout, 300, 200);
        invalidInputAlert.setScene(scene);
        invalidInputAlert.show();
    }

    public static void saveBeforeExit(){
        Stage saveBeforeExit = new Stage();

        saveBeforeExit.initModality(Modality.APPLICATION_MODAL);

        Label message = new Label("Vil du lagre endringer før du lukker programmet?");

        Button yes = new Button("Lagre");
        yes.setMinWidth(30);
        yes.setOnAction(actionEvent -> {
            FileSaver fileSaver = new FileSaver(){};
            try {
                fileSaver.save(App.fileManager, Registry.getRegistry());
                if(App.fileManager.getPath() != null){
                    Platform.exit();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button no = new Button("Ikke Lagre");
        no.setMinWidth(30);
        no.setOnAction(actionEvent -> {
            Registry.getRegistry().clear();
            Platform.exit();
        });

        Button cancel = new Button ("Avbryt");
        cancel.setMinWidth(30);
        cancel.setOnAction(actionEvent -> saveBeforeExit.close());

        HBox options = new HBox(10);
        options.setSpacing(10);
        options.setAlignment(CENTER);
        options.getChildren().addAll(yes, no, cancel);

        VBox layout = new VBox(10);
        layout.setAlignment(CENTER);
        layout.getChildren().addAll(message, options);

        Scene scene = new Scene(layout, 300,150);
        saveBeforeExit.setScene(scene);
        saveBeforeExit.show();
    }
}
