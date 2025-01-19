package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class InputWindowController extends SimulationPresenter {
    @FXML public TextField widthField;
    @FXML public TextField heightField;
    @FXML public TextField animalGenLengthField;
    @FXML public TextField animalNoTextField;
    @FXML public TextField startEnergyField;
    @FXML public TextField sexRequiredEnergyField;
    @FXML public TextField reproduceRequiredEnergyField;
    @FXML public TextField minMutationField;
    @FXML public TextField maxMutationField;
    @FXML public TextField plusGrassField;
    @FXML public TextField extraEnergyField;
    @FXML public TextField extraEnergyBigGrassField;
    @FXML public TextField grassNumberTextField;
    @FXML public TextField animalTypeTextField;
    @FXML public GridPane gridPane;


    private SimulationWindowController simulationController;
    private Stage simulationStage;

    public void setSimulationController(SimulationWindowController controller) {
        this.simulationController = controller;
    }

    public void setSimulationStage(Stage stage) {
        this.simulationStage = stage;
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        try {
            // Pobieranie danych wejściowych
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int grassNo = Integer.parseInt(grassNumberTextField.getText());
            AbstractWorldMap map = new GoodHarvestMap(width, height, grassNo);

            int sexRequiredEnergy = Integer.parseInt(sexRequiredEnergyField.getText());
            int reproduceRequiredEnergy = Integer.parseInt(reproduceRequiredEnergyField.getText());
            int minMutationNo = Integer.parseInt(minMutationField.getText());
            int maxMutationNo = Integer.parseInt(maxMutationField.getText());
            int startEnergy = Integer.parseInt(startEnergyField.getText());
            int plusGrass = Integer.parseInt(plusGrassField.getText());
            int extraEnergy = Integer.parseInt(extraEnergyField.getText());
            int extraEnergyBigGrass = Integer.parseInt(extraEnergyBigGrassField.getText());
            int animalNo = Integer.parseInt(animalNoTextField.getText());
            int animalGenLength = Integer.parseInt(animalGenLengthField.getText());

            String animalTypeInput = animalTypeTextField.getText().trim().toUpperCase();
            AnimalType animalType;
            try {
                animalType = AnimalType.valueOf(animalTypeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid animal type: " + animalTypeInput);
                return;
            }

            map.addListener(this);

            Simulation simulation = new Simulation(
                    map, animalGenLength, animalNo, startEnergy,
                    sexRequiredEnergy, reproduceRequiredEnergy,
                    minMutationNo, maxMutationNo, plusGrass,
                    extraEnergy, extraEnergyBigGrass, animalType
            );

            SimulationEngine engine = new SimulationEngine(List.of(simulation));
            new Thread(() -> engine.runSync()).start();

            URL simUrl = getClass().getClassLoader().getResource("simulation.fxml");
            if (simUrl == null) {
                System.err.println("Nie znaleziono pliku simulation.fxml!");
                return;
            }
            FXMLLoader simulationLoader = new FXMLLoader();
            simulationLoader.setLocation(simUrl);
            BorderPane simulationRoot = simulationLoader.load();
            SimulationWindowController newSimController = simulationLoader.getController();

            newSimController.setWorldMap(map);
            newSimController.setSimulation(simulation);

            Stage newSimulationStage = new Stage();
            configureStage(newSimulationStage, simulationRoot);
            newSimulationStage.show();

            Platform.runLater(() -> {
                map.addListener(newSimController);
            });

        } catch (NumberFormatException | IOException e) {
            System.out.println("Wprowadź poprawne dane liczbowe lub wystąpił błąd ładowania symulacji.");
            e.printStackTrace();
        }
    }
    private void configureStage(Stage stage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation app");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
