package agh.ics.oop;

import agh.ics.oop.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static void main(String[] args) {

        System.out.println("System wystartował");
        Instant start = Instant.now();

        ConsoleMapDisplay mapDisplay = new ConsoleMapDisplay();
        List<MoveDirection> directions = OptionParser.parse(args);
        List<Simulation> simulations = new ArrayList<>();
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        for (int i = 0; i < 1000; i++) {
            Random rand = new Random();
            int mapType = rand.nextInt(2);
            mapType = i%2;
            if(mapType == 0){
                int a = rand.nextInt(4,13);
                int b = rand.nextInt(4,13);
                AbstractWorldMap rectangularMap = new RectangularMap(10,10);
                rectangularMap.addListener(mapDisplay);
                Simulation simulation = new Simulation(positions, directions, rectangularMap);
                simulations.add(simulation);
            } else{
                int a = rand.nextInt(4,20);
                AbstractWorldMap grassField = new GrassField(17);
                grassField.addListener(mapDisplay);
                Simulation simulation = new Simulation(positions, directions, grassField);
                simulations.add(simulation);
            }
        }
        SimulationEngine engine = new SimulationEngine(simulations);
//        engine.runSync();
//        engine.runAsync();
        engine.runAsyncInThreadPool();

        System.out.println(Duration.between(start, Instant.now()).toSeconds());
        System.out.println("System zakończył działanie");
    }
}
