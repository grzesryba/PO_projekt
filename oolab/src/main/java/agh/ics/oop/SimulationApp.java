package agh.ics.oop;

import agh.ics.oop.model.GrassField;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.presenter.InputWindowController;
import agh.ics.oop.presenter.SimulationPresenter;
import agh.ics.oop.presenter.SimulationWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SimulationApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Ładowanie okna wprowadzania danych
        FXMLLoader inputLoader = new FXMLLoader();
        inputLoader.setLocation(getClass().getClassLoader().getResource("inputWindow.fxml"));
        Parent inputRoot = inputLoader.load();
        InputWindowController inputController = inputLoader.getController();
        Stage inputStage = new Stage();
        inputStage.setTitle("Wprowadź dane");
        inputStage.setScene(new Scene(inputRoot));

        // Przygotowanie okna symulacji, ale bez wyświetlania
        FXMLLoader simulationLoader = new FXMLLoader();
        simulationLoader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane simulationRoot = simulationLoader.load();
        SimulationPresenter simulationController = simulationLoader.getController();
        configureStage(primaryStage, simulationRoot);
        // Nie pokazujemy primaryStage tutaj; będzie pokazany po wprowadzeniu danych

        // Przekazanie referencji do kontrolera wejściowego
        inputController.setSimulationController((SimulationWindowController) simulationController);
        inputController.setSimulationStage(primaryStage);

        // Wyświetlenie okna wprowadzania danych
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
