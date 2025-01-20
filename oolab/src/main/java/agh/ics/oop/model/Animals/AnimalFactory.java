package agh.ics.oop.model.Animals;

import agh.ics.oop.model.Others.MapDirection;
import agh.ics.oop.model.Others.Vector2d;

import java.util.List;

public class AnimalFactory {
    public static AbstractAnimal createAnimal(AnimalType animalType, Vector2d position, MapDirection direction, int animalGenLength, int startEnergy){
        switch (animalType){
            case NORMAL: return new Animal(position,direction,animalGenLength,startEnergy);
            case CRAZY: return new CrazyAnimal(position,direction,animalGenLength,startEnergy);
            default: throw new RuntimeException("Invalid animal type");
        }
    }

    public static AbstractAnimal createAnimal(AnimalType animalType, Vector2d position, MapDirection direction, List<Integer> genList, int startEnergy){
        switch (animalType){
            case NORMAL: return new Animal(position,direction,genList,startEnergy);
            case CRAZY: return new CrazyAnimal(position,direction,genList,startEnergy);
            default: throw new RuntimeException("Invalid animal type");
        }
    }

}


