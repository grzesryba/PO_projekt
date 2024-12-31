package agh.ics.oop.model;

import agh.ics.oop.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testAnimalInitialization() {
        List<Integer> genotype = List.of(1,2,3,4);

        AbstractAnimal basicAnimal = new Animal(new Vector2d(2, 2), MapDirection.NORTH, genotype, 10);

        assertEquals(new Vector2d(2, 2), basicAnimal.getPosition());
        assertEquals(10, basicAnimal.getEnergy());
    }

    @Test
    void testAnimalMovement() throws IncorrectPositionException {
        List<Integer> genotype = List.of(1, 2, 2, 5);
        Animal basicAnimal = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotype, 10);
        basicAnimal.changeDirection(0);
        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);

        regularWorld.place(basicAnimal);
        regularWorld.move(basicAnimal);

        assertEquals(new Vector2d(5, 5 ), basicAnimal.getPosition());

        regularWorld.move(basicAnimal);

        assertEquals(new Vector2d(6, 4 ), basicAnimal.getPosition());
    }

    @Test
    void testAnimalReproduction() throws IncorrectPositionException {
        List<Integer> genotypeA = List.of(1, 2, 3, 4);
        List<Integer> genotypeB = List.of(4,5, 6, 7);
        AbstractAnimal parentA = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotypeA, 100);
        AbstractAnimal parentB = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotypeB, 80);

        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);

        regularWorld.place(parentA);
        regularWorld.place(parentB);
        regularWorld.reproduce(50, 20, 0, 0);

        //Sprawdzenie energii rodziców
        assertEquals(80, parentA.getEnergy()); //100-20
        assertEquals(60, parentB.getEnergy()); //60-20

        List<WorldElement> animals = regularWorld.objectsAt(new Vector2d(4, 4));
        assertEquals(3, animals.size()); // 2 rodziców + 1 dziecko

        AbstractAnimal child = (AbstractAnimal) animals.stream().filter(animal -> animal != parentA && animal != parentB).findFirst().orElse(null);
        assertNotNull(child);

        assertEquals(40, child.getEnergy());

        List<Integer> childGenotype = child.getGenList();
        assertEquals(4, childGenotype.size());


        assertTrue(Objects.equals(child.getGenList(), List.of(1, 2, 6, 7)) || Objects.equals(child.getGenList(), List.of(4, 5, 3, 4)));
        //assertEquals(1, parentA.getNumberOfDescendants());
        assertEquals(1, parentB.getChildNo());
    }

    @Test
    void testAnimalLifecycleAndDeath() throws IncorrectPositionException {
        List<Integer> genotypeA = List.of(1, 2, 3, 4);
        List<Integer> genotypeB = List.of(4,5, 6, 7);
        AbstractAnimal parentA = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotypeA, 80);
        AbstractAnimal parentB = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotypeB, 40);

        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);

        regularWorld.place(parentA);
        regularWorld.place(parentB);
        regularWorld.reproduce(50, 1, 0, 0);

        List<WorldElement> animals = regularWorld.objectsAt(new Vector2d(4, 4));
        AbstractAnimal child = (AbstractAnimal) animals.stream().filter(animal -> animal != parentA && animal != parentB).findFirst().orElse(null);

        for (int i = 0; i < 5; i++) {
            regularWorld.moveAllAnimals();
        }
        regularWorld.deleteDeathAnimals();

        assertFalse(child.isAlive());
        assertEquals(5, parentA.getAge());
        assertEquals(5, parentB.getAge());
        //assertEquals(5, child.getDayOfDeath());
        assertTrue(parentA.isAlive());
        assertEquals(34, parentB.getEnergy());
    }

    @Test
    void testAnimalFeeding() throws IncorrectPositionException {
        List<Integer> genotype = List.of(0, 1, 1, 1);
        Animal basicAnimal = new Animal(new Vector2d(4, 4), MapDirection.NORTH, genotype, 10);
        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);


        regularWorld.place(basicAnimal);
        regularWorld.addPlantAtPosition(new Vector2d(4, 4));
        regularWorld.plantEating(20,20);

        assertEquals(30, basicAnimal.getEnergy());
    }

    @Test
    public void testAnimalWrapAroundEdges() throws IncorrectPositionException {

        List<Integer> genotype = List.of(0, 0, 0, 0);
        Animal basicAnimal = new Animal(new Vector2d(9, 9), MapDirection.EAST, genotype, 10);
        SimpleWorldMap regularWorld = new SimpleWorldMap(10, 10,0);

        regularWorld.place(basicAnimal);
        regularWorld.move(basicAnimal);

        assertEquals(new Vector2d(0, 9), basicAnimal.getPosition());
    }
}