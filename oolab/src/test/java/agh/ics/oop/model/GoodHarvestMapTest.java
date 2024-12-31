package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodHarvestMapTest {
    @Test
    public void testBigGrassPlacementAndRemoval() throws IncorrectPositionException {
        GoodHarvestMap map = new GoodHarvestMap(10, 10, 0);
        map.addBigPlant();

        List<Vector2d> foundPositions = map.getOccupiedGrassPositions();

        System.out.println(foundPositions.size());
        for (Vector2d vector : foundPositions) {
            System.out.println(vector);
        }

        Vector2d Position = foundPositions.getFirst();

        List<Integer> genotype = List.of(0, 0, 0, 0);
        AbstractAnimal basicAnimal = new Animal(Position, MapDirection.NORTH, genotype, 10);
        map.place(basicAnimal);

        map.plantEating(0, 10);

        map.moveAllAnimals();
        map.moveAllAnimals();
        map.moveAllAnimals();

        assertFalse(map.isOccupied(foundPositions.get(0)));
        assertFalse(map.isOccupied(foundPositions.get(1)));
        assertFalse(map.isOccupied(foundPositions.get(2)));
        assertFalse(map.isOccupied(foundPositions.get(3)));
    }
    //Nie działa dla przypadku gdy trawa jest rozdzielona na granicy mapy
    
}