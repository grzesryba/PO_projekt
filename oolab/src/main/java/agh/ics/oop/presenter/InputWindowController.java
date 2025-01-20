package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.AnimalType;
import agh.ics.oop.model.GoodHarvestMap;
import agh.ics.oop.model.SimpleWorldMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.List;

public class InputWindowController {
    @FXML
    public TextField widthField;
    @FXML
    public TextField heightField;
    @FXML
    public TextField animalGenLengthField;
    @FXML
    public TextField animalNoTextField;
    @FXML
    public TextField startEnergyField;
    @FXML
    public TextField sexRequiredEnergyField;
    @FXML
    public TextField reproduceRequiredEnergyField;
    @FXML
    public TextField minMutationField;
    @FXML
    public TextField maxMutationField;
    @FXML
    public TextField plusGrassField;
    @FXML
    public TextField extraEnergyField;
    @FXML
    public TextField extraEnergyBigGrassField;
    @FXML
    public TextField grassNumberTextField;
    @FXML
    public TextField animalTypeTextField;
    @FXML
    public GridPane gridPane;
    @FXML
    public TextField mapTypeField;
    public ChoiceBox<String> animalTypeChoiceBox;
    @FXML
    private ChoiceBox<String> mapTypeChoiceBox;
    @FXML
    private CheckBox saveAsExampleCheckBox;
    @FXML
    private TextField exampleNameField;


    private SimulationWindowController simulationController;
    private Stage simulationStage;

    @FXML
    public void initialize() {
        animalTypeChoiceBox.setItems(FXCollections.observableArrayList("CRAZY", "NORMAL"));
        mapTypeChoiceBox.setItems(FXCollections.observableArrayList("GoodHarvestMap", "SimpleWorldMap"));

        animalTypeChoiceBox.setValue("NORMAL");
        mapTypeChoiceBox.setValue("GoodHarvestMap");
    }

    public void setSimulationController(SimulationWindowController controller) {
        this.simulationController = controller;
    }

    public void setSimulationStage(Stage stage) {
        this.simulationStage = stage;
    }

    @FXML
    private void startSimulation(ActionEvent event) {
        if (!validateInput()) {
            return;
        }
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int grassNo = Integer.parseInt(grassNumberTextField.getText());

            String mapTypeInput = mapTypeChoiceBox.getValue();
            AbstractWorldMap map;
            if (mapTypeInput.equalsIgnoreCase("GoodHarvestMap")) {
                map = new GoodHarvestMap(width, height, grassNo);
            } else if (mapTypeInput.equalsIgnoreCase("SimpleWorldMap")) {
                map = new SimpleWorldMap(width, height, grassNo);
            } else {
                System.out.println("Invalid map type: " + mapTypeInput);
                return;
            }
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

            if(saveAsExampleCheckBox.isSelected()){
                saveCurrentConfiguration();
            }

            String animalTypeInput = animalTypeChoiceBox.getValue();
            AnimalType animalType;
            try {
                animalType = AnimalType.valueOf(animalTypeInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid animal type: " + animalTypeInput);
                return;
            }

//            map.addListener(this);

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
                return;
            }
            FXMLLoader simulationLoader = new FXMLLoader();
            simulationLoader.setLocation(simUrl);
            BorderPane simulationRoot = simulationLoader.load();
            SimulationWindowController newSimController = simulationLoader.getController();

            newSimController.setWorldMap(map);
            newSimController.setSimulation(simulation);
            setSimulationController(newSimController);

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
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int grassNo = Integer.parseInt(grassNumberTextField.getText());
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


            if (width <= 0 || height <= 0) {
                showError("Width and height must be positive.");
                return false;
            }
            if (grassNo < 0) {
                showError("Grass number must be non-negative.");
                return false;
            }
            if (sexRequiredEnergy <= 0 || reproduceRequiredEnergy <= 0 || startEnergy <= 0) {
                showError("Energy values must be positive.");
                return false;
            }
            if (plusGrass < 0) {
                showError("Grass growth per step (plusGrass) must be non-negative.");
                return false;
            }
            if (extraEnergy <= 0) {
                showError("Extra energy from grass must be positive.");
                return false;
            }
            if (extraEnergyBigGrass <= 0) {
                showError("Extra energy from big grass must be positive.");
                return false;
            }
            if (minMutationNo < 0 || maxMutationNo < 0 || minMutationNo > maxMutationNo) {
                showError("Mutation numbers must be valid (min <= max).");
                return false;
            }
            if (animalNo <= 0) {
                showError("Animal number must be positive.");
                return false;
            }
            if (animalGenLength <= 0) {
                showError("Animal gene length must be positive.");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values.");
            return false;
        }
    }

    public void loadFromCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select csv file");

        File initialDir = new File("./Przykladowe_konfiguracje");
        if (initialDir.exists()) {
            fileChooser.setInitialDirectory(initialDir);
        }

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    // Odczytaj pierwszą linię z pliku CSV
                    if ((line = reader.readLine()) != null) {
                        String[] data = line.split(","); // Rozdziel dane na podstawie przecinków
                        if (data.length == 15) { // Sprawdź, czy liczba parametrów jest prawidłowa
                            // Uzupełnij pola tekstowe danymi
                            widthField.setText(data[0]);
                            heightField.setText(data[1]);
                            animalGenLengthField.setText(data[2]);
                            animalNoTextField.setText(data[3]);
                            startEnergyField.setText(data[4]);
                            sexRequiredEnergyField.setText(data[5]);
                            reproduceRequiredEnergyField.setText(data[6]);
                            minMutationField.setText(data[7]);
                            maxMutationField.setText(data[8]);
                            plusGrassField.setText(data[9]);
                            extraEnergyField.setText(data[10]);
                            extraEnergyBigGrassField.setText(data[11]);
                            grassNumberTextField.setText(data[12]);
                            animalTypeChoiceBox.setValue(data[13]);
                            mapTypeChoiceBox.setValue(data[14]);
                        } else {
                            showErrorDialog("Invalid CSV format. Please ensure the file has exactly 15 values.");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showErrorDialog("An error occurred while reading the file.");
                }
            }
        }

    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveCurrentConfiguration() {
        String exampleName = exampleNameField.getText();

        if (exampleName == null || exampleName.isBlank()) {
            showErrorDialog("Please provide a name for the configuration.");
            return;
        }

        File file = new File("./Przykladowe_konfiguracje/" + exampleName + ".csv");

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Pobierz dane z pól tekstowych i zapisz jako wiersz CSV
                String csvLine = String.join(",",
                        widthField.getText(),
                        heightField.getText(),
                        animalGenLengthField.getText(),
                        animalNoTextField.getText(),
                        startEnergyField.getText(),
                        sexRequiredEnergyField.getText(),
                        reproduceRequiredEnergyField.getText(),
                        minMutationField.getText(),
                        maxMutationField.getText(),
                        plusGrassField.getText(),
                        extraEnergyField.getText(),
                        extraEnergyBigGrassField.getText(),
                        grassNumberTextField.getText(),
                        animalTypeChoiceBox.getValue(),
                        mapTypeChoiceBox.getValue()
                );
                writer.write(csvLine);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
                showErrorDialog("An error occurred while saving the configuration.");
            }
        }
    }

    @FXML
    private void toggleExampleNameField() {
        boolean isSelected = saveAsExampleCheckBox.isSelected();
        exampleNameField.setVisible(isSelected);
    }

    private void configureStage(Stage stage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation app");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        stage.setOnCloseRequest(event -> {
            simulationController.getSimulation().close();
        });
    }
}
