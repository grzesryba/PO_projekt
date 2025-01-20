package agh.ics.oop;


import agh.ics.oop.presenter.InputWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader inputLoader = new FXMLLoader();
        inputLoader.setLocation(getClass().getClassLoader().getResource("inputWindow.fxml"));
        Parent inputRoot = inputLoader.load();
        InputWindowController inputController = inputLoader.getController();
        Stage inputStage = new Stage();
        inputStage.setTitle("Wprowadź dane");
        inputStage.setScene(new Scene(inputRoot));
        inputStage.show();
    }


    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

}
