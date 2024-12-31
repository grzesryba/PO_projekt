package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractAnimal {

    public Animal(Vector2d position,MapDirection direction, int genLength, int energy) {
        this.animalType = AnimalType.NORMAL;
        this.position = position;
        this.direction = direction;
        this.energy = energy;
        Random rand = new Random();
        for (int i = 0; i <genLength; i++) {
            genList.add(rand.nextInt(8));
        }
    }

    public Animal(Vector2d position,MapDirection direction, List<Integer> genList, int energy) {
        this.animalType = AnimalType.NORMAL;
        this.position = position;
        this.direction = direction;
        this.genList.addAll(genList);
        this.energy = energy;
    }

}
