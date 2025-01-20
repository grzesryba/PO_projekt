package agh.ics.oop.model.Animals;

import agh.ics.oop.model.Others.MapDirection;
import agh.ics.oop.model.Statistics.AnimalStatistics;
import agh.ics.oop.model.Others.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractAnimal {

    public Animal(Vector2d position, MapDirection direction, int genLength, int energy) {
        Random rand = new Random();
        List<Integer> genList = new ArrayList<>();
        for (int i = 0; i < genLength; i++) {
            genList.add(rand.nextInt(8));
        }
        animalStats = new AnimalStatistics(direction,position,energy, AnimalType.NORMAL,genList);
    }

    public Animal(Vector2d position,MapDirection direction, List<Integer> genList, int energy) {
        animalStats = new AnimalStatistics(direction,position,energy,AnimalType.CRAZY,genList);
    }

}
