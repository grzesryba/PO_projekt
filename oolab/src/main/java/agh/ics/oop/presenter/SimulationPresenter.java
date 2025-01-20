package agh.ics.oop.presenter;

import agh.ics.oop.OptionParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {

    public TextArea animalTextArea;
    public TextField animalTypeTextField;
    public TextField grassNumberTextField;
    public TextField extraEnergyBigGrassField;
    public TextField extraEnergyField;
    public TextField plusGrassField;
    public TextField maxMutationField;
    public TextField minMutationField;
    public TextField reproduceRequiredEnergyField;
    public TextField sexRequiredEnergyField;
    public TextField startEnergyField;
    public TextField animalGenLengthField;
    public TextField heightField;
    public TextField animalNoTextField;
    public TextField widthField;
    public TextArea textArea;
    public GridPane gridPane;
    //public TextField movesArea;
    //public Label moveDescription;
    private WorldMap worldMap;
    private final int mapHeight = 350;
    private final int mapWidth = 350;
    private int cellWidth;
    private int cellHeight;
    private Simulation simulation;
    private AbstractAnimal selectedAnimal = null;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void drawMap() {
        int minX = worldMap.getCurrentBounds().leftBottom().getX();
        int minY = worldMap.getCurrentBounds().leftBottom().getY();
        int maxX = worldMap.getCurrentBounds().rightTop().getX();
        int maxY = worldMap.getCurrentBounds().rightTop().getY();
        cellHeight = mapHeight / (maxY - minY + 1);
        cellWidth = mapWidth / (maxX - minX + 1);

        columns(maxX, minX);
        rows(maxY, minY);

        int yIdx = maxY - minY + 1;
        int xIdx;
        for (int i = minY; i <= maxY; i++) {
            xIdx = 1;
            for (int j = minX; j <= maxX; j++) {
                Vector2d position = new Vector2d(j, i);
                List<WorldElement> worldElements = worldMap.objectsAt(position);

                StackPane cellPane = new StackPane();
                Text node;
                if (worldElements != null && !worldElements.isEmpty()) {
                    String repr = worldElements.getLast().toString();
                    node = new Text(repr);

                    if (repr.equals("*") || repr.equals("$")) {
                        cellPane.setStyle("-fx-background-color: darkgreen;");
                    }

                    if (selectedAnimal != null && worldElements.contains(selectedAnimal)) {
                        cellPane.setStyle("-fx-background-color: yellow; -fx-border-color: red; -fx-font-weight: bold;");
                    }
                } else {
                    node = new Text(" ");
                }

                cellPane.getChildren().add(node);

                cellPane.setOnMouseClicked(event -> handleCellClick(position));

                gridPane.add(cellPane, xIdx, yIdx);
                GridPane.setHalignment(cellPane, HPos.CENTER);
                GridPane.setValignment(cellPane, VPos.CENTER);
                xIdx += 1;
            }
            yIdx -= 1;
        }

        gridPane.setStyle("-fx-text-alignment: CENTER; -fx-background-color: lightgreen;");
        gridPane.setGridLinesVisible(false);
        gridPane.setGridLinesVisible(true);
    }

    private void rows(int maxY, int minY) {
        int row_idx = maxY;
        for (int i = 0; i <= maxY - minY + 1; i++) {
            RowConstraints row = new RowConstraints(cellHeight);
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
            if (i != 0) {
                Label node = new Label(Integer.toString(row_idx));
                row_idx -= 1;
                gridPane.add(node, 0, i);
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
            }
        }
    }

    private void columns(int maxX, int minX) {
        int column_idx = minX;
        for (int i = 0; i <= maxX - minX + 1; i++) {
            ColumnConstraints column = new ColumnConstraints(cellWidth);
            column.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column);
            if (i != 0) {
                Label node = new Label(Integer.toString(column_idx));
                column_idx += 1;
                gridPane.add(node, i, 0);
                GridPane.setHalignment(node, HPos.CENTER);
                GridPane.setValignment(node, VPos.CENTER);
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        //        textArea.setText("");
        Platform.runLater(() -> {
//            gridPane.getChildren().clear();
//            gridPane.getColumnConstraints().clear();
//            gridPane.getRowConstraints().clear();
            //moveDescription.setText(message);
            drawMap();
            updateStatisticsDisplay();
            updateSelectedAnimalStatistics();
        });
    }

    private void updateSelectedAnimalStatistics() {
        if (selectedAnimal != null && selectedAnimal.isAlive()) {
            String statsText = String.valueOf(selectedAnimal.getAnimalStats());
            System.out.println(statsText);
            animalTextArea.setText(statsText);
        }
        else{
            animalTextArea.setText("Nie wybrałeś zwierzaka!");
        }
    }

    private void handleCellClick(Vector2d position) {
        System.out.println("Kliknięto na pozycję: " + position);

        List<WorldElement> worldElements = worldMap.objectsAt(position);
        if (worldElements != null) {
            System.out.println("Znaleziono elementy: " + worldElements);
            for (WorldElement element : worldElements) {
                System.out.println("Sprawdzanie elementu: " + element + ", typ: " + element.getClass().getName());

                if (element instanceof AbstractAnimal) {
                    selectedAnimal = (AbstractAnimal) element;
                    System.out.println("Wybrano zwierzaka: " + selectedAnimal);
                    updateSelectedAnimalStatistics();
                    drawMap();
                    return;
                }
            }
        } else {
            System.out.println("Brak elementów na tej pozycji.");
        }

        updateSelectedAnimalStatistics();
        drawMap();
    }


    private void updateStatisticsDisplay(){
        if (worldMap == null || worldMap.getStatistics() == null) {
            return;
        }
        String statsText = String.valueOf(worldMap.getStatistics());

        // Zmiana formatu stringa, aby było bardziej czytelne
        statsText = statsText.replaceAll("\\{", "\n")
                .replaceAll("\\}", "")
                .replaceAll(",", "\n")
                .replaceAll("=", ": ");


        // Ustawienie sformatowanego tekstu w TextArea
        textArea.setText(statsText);
    }

    public void StartSimulation(ActionEvent actionEvent) {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int grassNo = Integer.parseInt(grassNumberTextField.getText());
        AbstractWorldMap map = new GoodHarvestMap(width,height,grassNo);
        //String text = movesArea.getText();
        map.addListener(this);
        this.setWorldMap(map);
        int sexRequiredEnergy = Integer.parseInt(sexRequiredEnergyField.getText());
        int reproduceRequiredEnergy = Integer.parseInt(reproduceRequiredEnergyField.getText());
        int minMutationNo = Integer.parseInt(minMutationField.getText());
        int maxMutationNo = Integer.parseInt(maxMutationField.getText());
        int startEnergy = Integer.parseInt(startEnergyField.textProperty().getValue());
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

        Simulation simulation = new Simulation(map, animalGenLength, animalNo, startEnergy, sexRequiredEnergy, reproduceRequiredEnergy, minMutationNo, maxMutationNo, plusGrass, extraEnergy, extraEnergyBigGrass,animalType);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        this.simulation = simulation;

        new Thread(() -> {
            engine.runSync();
        }).start();
    }

    @FXML
    public void PauseSimulation() {
        if (simulation != null) {
            simulation.PauseSimulation();
            System.out.println("Simulation paused from Presenter.");
        }
    }

    @FXML
    public void ResumeSimulation() {
        if (simulation != null) {
            simulation.ResumeSimulation();
            System.out.println("Simulation resumed from Presenter.");
        }
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}