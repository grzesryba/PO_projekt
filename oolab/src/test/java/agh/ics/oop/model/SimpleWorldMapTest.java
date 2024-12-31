package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleWorldMapTest {
    @Test
    public void testAddPlantAtFreePosition() {
        SimpleWorldMap map = new SimpleWorldMap(10, 10, 0);
        Vector2d position = new Vector2d(5, 5);

        assertTrue(map.addPlantAtPosition(position));
        assertTrue(map.isOccupied(position));
    }

    @Test
    public void testPlantRemove() throws IncorrectPositionException {
        SimpleWorldMap map = new SimpleWorldMap(10, 10, 0);
        Vector2d position = new Vector2d(5, 5);
        map.addPlantAtPosition(position);

        List<Integer> genotype = List.of(1, 1, 1, 1);
        AbstractAnimal animal = new Animal(position, MapDirection.NORTH, genotype, 10);
        map.place(animal);

        map.plantEating(20, 30);

        assertEquals(30, animal.getEnergy());

        map.move(animal);

        assertFalse(map.isOccupied(position));
    }

    //ten test raz działa raz nie
    @Test
    public void testPlantDistributionOnEquator() {
        int width = 10;
        int height = 10;
        SimpleWorldMap map = new SimpleWorldMap(width, height, 50);


        // Obliczenie równika (to ma być równik i okolice dlatego 25 - 75 %)
        int equatorStart = height / 2; // 25% od góry
        int equatorEnd = (3 * height) / 4; // 75% od góry
        int plantsOnEquator = 0;

        // Sprawdzenie pozycji roślin
        for (Grass grass : map.getAllGrass()) {
            int y = grass.getPosition().getY();
            if (y >= equatorStart && y <= equatorEnd) {
                plantsOnEquator++;
            }
        }

        // Test: 80% roślin powinno być na równiku
        double percentageOnEquator = (plantsOnEquator / (double) 20) * 100;
        assertTrue(percentageOnEquator >= 80.0, "Expected at least 80% plants on the equator, but got: " + percentageOnEquator + "%");
    }


}