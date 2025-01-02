package agh.ics.oop.presenter;

import agh.ics.oop.OptionParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    public TextArea textArea;
    public GridPane gridPane;
    public TextField movesArea;
    public Label moveDescription;
    private WorldMap worldMap;
    private final int mapHeight = 350;
    private final int mapWidth = 350;
    private int cellWidth;
    private int cellHeight;

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void drawMap() {
        int minX = worldMap.getCurrentBounds().leftBottom().getX();
        int minY = worldMap.getCurrentBounds().leftBottom().getY();
        int maxX = worldMap.getCurrentBounds().rightTop().getX();
        int maxY = worldMap.getCurrentBounds().rightTop().getY();
        cellHeight = mapHeight/(maxY-minY+1);
        cellWidth = mapWidth/(maxX-minX+1);

        columns(maxX, minX);
        rows(maxY, minY);

        int y_idx = maxY - minY + 1;
        int x_idx = 1;
        for (int i = minY; i <= maxY; i++) {
            x_idx = 1;
            for (int j = minX; j <= maxX; j++) {
                List<WorldElement> worldElements = worldMap.objectsAt(new Vector2d(j, i));
                if (worldElements != null) {
                    Text node = new Text(worldElements.getLast().toString());
                    gridPane.add(node, x_idx, y_idx);
                    GridPane.setHalignment(node, HPos.CENTER);
                    GridPane.setValignment(node, VPos.CENTER);
                } else {
                    gridPane.add(new Text(" "), x_idx, y_idx);
                }
                x_idx += 1;
            }
            y_idx -= 1;
        }

        gridPane.setStyle("-fx-text-alignment: CENTER");
        gridPane.setGridLinesVisible(false);
        gridPane.setGridLinesVisible(true);

        //        textArea.setText(worldMap.toString());
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
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
            moveDescription.setText(message);
            drawMap();
        });
    }

    public void StartSimulation(ActionEvent actionEvent) {
        AbstractWorldMap map = new GoodHarvestMap(20,20,79);
        String text = movesArea.getText();
        map.addListener(this);
        this.setWorldMap(map);
        int sexRequiredEnergy = 6;
        int reproduceRequiredEnergy = 3;
        int minMutationNo = 0;
        int maxMutationNo = 7;
        int startEnergy = 100;
        int plusGrass = 10;
        int extraEnergy = 2;
        int extraEnergyBigGrass = 2;
        int animalNo = 5;
        int animalGenLength = 50;
        AnimalType type = AnimalType.CRAZY;
        Simulation simulation = new Simulation(map, animalGenLength, animalNo, startEnergy, sexRequiredEnergy, reproduceRequiredEnergy, minMutationNo, maxMutationNo, plusGrass, extraEnergy, extraEnergyBigGrass,type);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        new Thread(() -> {
            engine.runSync();
        }).start();
    }
}