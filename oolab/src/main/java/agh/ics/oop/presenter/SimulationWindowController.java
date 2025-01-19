package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import agh.ics.oop.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class SimulationWindowController extends SimulationPresenter implements MapChangeListener {
    @FXML public GridPane gridPane;
    public TextArea textArea;
    public TextArea animalTextArea;
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

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
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
                Node content = null;  // Używamy Node zamiast Text

                if (worldElements != null && !worldElements.isEmpty()) {
                    String repr = worldElements.getLast().toString();

                    if (repr.equals("*")) {
                        cellPane.setStyle("-fx-background-color: green;");
                        Circle dot = new Circle(3, Color.DARKGREEN);
                        content = dot;
                    }
                    else if (repr.equals("$")){
                        cellPane.setStyle("-fx-background-color: green;");
                        Circle dot = new Circle(5, Color.DARKGREEN);
                        content = dot;
                    } else if ((repr.length() <= 2) && !repr.equals("*") && !repr.equals("$")) {
                        cellPane.setStyle("-fx-background-color: #524a2f;");

                        double outerRadius = Math.min(cellWidth, cellHeight) * 0.3;
                        double innerRadius = outerRadius * 0.5;
                        double centerX = 0;
                        double centerY = 0;
                        double energy = 0;

                        for (WorldElement element : worldElements) {
                            if (element instanceof AbstractAnimal) {
                                energy = ((AbstractAnimal) element).getAnimalStats().getEnergy();
                                break;
                            }
                        }

                        double avgEnergy = worldMap.getStatistics().getAvgEnergy();


                        Color starColor;
                        if (energy > avgEnergy) {
                            starColor = Color.GREEN;
                        } else if (energy > avgEnergy * 0.5) {
                            starColor = Color.ORANGE;
                        } else {
                            starColor = Color.RED;
                        }

                        Polygon star = new Polygon(
                                centerX, centerY - outerRadius,
                                centerX + innerRadius, centerY - innerRadius,
                                centerX + outerRadius, centerY,
                                centerX + innerRadius, centerY + innerRadius,
                                centerX, centerY + outerRadius,
                                centerX - innerRadius, centerY + innerRadius,
                                centerX - outerRadius, centerY,
                                centerX - innerRadius, centerY - innerRadius
                        );
                        star.setFill(starColor);
                        content = star;
                    } else {
                        content = new Text(repr);
                    }

                    if (selectedAnimal != null && worldElements.contains(selectedAnimal)) {
                        cellPane.setStyle("-fx-background-color: yellow; -fx-border-color: red; -fx-font-weight: bold;");
                    }
                } else {
                    content = new Text(" ");
                }

                if (content != null) {
                    cellPane.getChildren().add(content);
                }

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
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            gridPane.getColumnConstraints().clear();
            gridPane.getRowConstraints().clear();
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

        List<WorldElement> worldElements = worldMap.objectsAt(position);
        if (worldElements != null) {
            for (WorldElement element : worldElements) {

                if (element instanceof AbstractAnimal) {
                    selectedAnimal = (AbstractAnimal) element;
                    updateSelectedAnimalStatistics();
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

//    public List<AbstractAnimal> getAnimalsWithDominantGen(WorldMap worldMap, int dominantGen) {
//        List<AbstractAnimal> result = new ArrayList<>();
//        int maxCount = -1;
//
//        int minX = worldMap.getCurrentBounds().leftBottom().getX();
//        int minY = worldMap.getCurrentBounds().leftBottom().getY();
//        int maxX = worldMap.getCurrentBounds().rightTop().getX();
//        int maxY = worldMap.getCurrentBounds().rightTop().getY();
//
//        for (int x = minX; x <= maxX; x++) {
//            for (int y = minY; y <= maxY; y++) {
//                Vector2d position = new Vector2d(x, y);
//                List<WorldElement> elements = worldMap.objectsAt(position);
//                if (elements != null) {
//                    for (WorldElement element : elements) {
//                        if (element instanceof AbstractAnimal) {
//                            AbstractAnimal animal = (AbstractAnimal) element;
//                            int count = countGeneOccurrences( animal, dominantGen);
//                            if (count > maxCount) {
//                                maxCount = count;
//                                result.clear();
//                                result.add(animal);
//                            } else if (count == maxCount) {
//                                result.add(animal);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return result;
//    }
//
//    private int countGeneOccurrences(AbstractAnimal animal, int gene) {
//        int count = 0;
//        for (int g : animal.getAnimalStats().getGenList()) {
//            if (animal.isAlive()) {
//                if (g == gene) count++;
//            }
//        }
//        return count;
//    }
//    @FXML
//    private void showMoreDominantGen() {
//        int dominantGen = worldMap.getStatistics().getMostCommonGen();
//
//        List<AbstractAnimal> dominantAnimals = getAnimalsWithDominantGen(worldMap, dominantGen);
//
//        for (AbstractAnimal animal : dominantAnimals) {
//            Vector2d position = animal.getPosition();
//            highlightAnimalAt(position);
//        }
//    }
//
//    private void highlightAnimalAt(Vector2d position) {
//        for (Node node : gridPane.getChildren()) {
//            Integer colIndex = GridPane.getColumnIndex(node);
//            Integer rowIndex = GridPane.getRowIndex(node);
//            if (colIndex != null && rowIndex != null
//                    && colIndex.equals(position.getX())
//                    && rowIndex.equals(position.getY())) {
//
//                node.setStyle("-fx-background-color: blue;");
//
//            }
//        }
//    }
}
