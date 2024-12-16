package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;
    private final List<MoveDirection> moves;
    private final WorldMap map;

    public Simulation(List<Vector2d> positions, List<MoveDirection> moves, WorldMap map) {
        this.animals = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            Animal animal = new Animal(positions.get(i));
            try {
                if (map.place(animal)) {
                    animals.add(animal);
                }
            } catch (IncorrectPositionException e) {
                System.out.println("Warning! " + e.getMessage());
            }
        }
        this.moves = moves;
        this.map = map;
    }

    public void run() {
        for (int i = 0; i < moves.size(); i++) {
            int animalIndex = i % animals.size();
            map.move(animals.get(animalIndex), moves.get(i));
//            System.out.println(map);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
