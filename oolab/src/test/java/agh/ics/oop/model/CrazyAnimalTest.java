package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CrazyAnimalTest {
    @Test
    void testAnimalInitialization() {
        List<Integer> genotype = List.of(3, 3, 3, 3);
        CrazyAnimal crazyAnimal = new CrazyAnimal(new Vector2d(2, 2), MapDirection.NORTH, genotype, 10);

        assertEquals(new Vector2d(2, 2),crazyAnimal.getPosition());
        assertEquals(10, crazyAnimal.getAnimalStats().getEnergy());
        assertTrue(crazyAnimal.isAlive());
    }

    @Test
    void testAnimalMovement() throws IncorrectPositionException {
        List<Integer> genotype = List.of(3, 3, 3, 3);
        CrazyAnimal basicAnimal = new CrazyAnimal(new Vector2d(5, 5), MapDirection.NORTH, genotype, 10);
        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);

        regularWorld.place(basicAnimal);
        regularWorld.move(basicAnimal);

        assertEquals(new Vector2d(6, 4 ), basicAnimal.getPosition());

        regularWorld.move(basicAnimal);

        assertEquals(new Vector2d(5, 4 ), basicAnimal.getPosition());
    }

    @Test
    public void testCrazyAnimalDirectionChange() throws IncorrectPositionException {
        // GIVEN
        Vector2d startPosition = new Vector2d(5, 5);
        MapDirection startDirection = MapDirection.NORTH;
        List<Integer> genList = List.of(2);
        int energy = 50;

        CrazyAnimal animal = new CrazyAnimal(startPosition, startDirection, genList, energy);
        SimpleWorldMap map = new SimpleWorldMap(10, 10,0);
        map.place(animal);

        // WHEN
        animal.move(map);

        // THEN
        assertEquals(MapDirection.EAST, animal.getAnimalStats().getDirection());
    }

}