package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class InputWindowController  {
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
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int grassNo = Integer.parseInt(grassNumberTextField.getText());
            AbstractWorldMap map = new GoodHarvestMap(width, height, grassNo);
            map.addListener(simulationController);;
            simulationController.setWorldMap(map);

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

            Simulation simulation = new Simulation(map, animalGenLength, animalNo, startEnergy,
                    sexRequiredEnergy, reproduceRequiredEnergy,
                    minMutationNo, maxMutationNo, plusGrass,
                    extraEnergy, extraEnergyBigGrass, animalType);
            simulationController.setSimulation(simulation);
            SimulationEngine engine = new SimulationEngine(List.of(simulation));

            new Thread(() -> {
                engine.runSync();
            }).start();

            // Otwórz okno symulacji
            if (simulationStage != null) {
                simulationStage.show();
            }
            // Zamknij okno wprowadzania danych
            Stage currentStage = (Stage) widthField.getScene().getWindow();
            currentStage.close();

        } catch (NumberFormatException e) {
            System.out.println("Wprowadź poprawne dane liczbowe.");
        }
    }
}
