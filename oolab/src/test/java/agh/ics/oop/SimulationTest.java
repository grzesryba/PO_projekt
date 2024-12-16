package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void animalMove() {
        Animal animal = new Animal(new Vector2d(3, 2));
        WorldMap map = new RectangularMap(5,5);

        animal.move(MoveDirection.FORWARD, map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(3, 3), animal.getPosition());

        animal.move(MoveDirection.RIGHT, map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(3, 3), animal.getPosition());

        animal.move(MoveDirection.BACKWARD, map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(MoveDirection.LEFT, map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());
    }

    @Test
    void animalOutOffBoundaries() {
        Animal animal = new Animal(new Vector2d(3, 2));
        WorldMap map = new RectangularMap(5,5);

        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);

        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(3, 4), animal.getPosition());


        animal = new Animal(new Vector2d(3, 2));
        map = new RectangularMap(5,5);

        animal.move(MoveDirection.RIGHT, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);

        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(4, 2), animal.getPosition());


        animal = new Animal(new Vector2d(3, 2));
        map = new RectangularMap(5,5);

        animal.move(MoveDirection.LEFT, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);
        animal.move(MoveDirection.FORWARD, map);

        assertEquals(MapDirection.WEST, animal.getDirection());
        assertEquals(new Vector2d(0, 2), animal.getPosition());


        animal = new Animal(new Vector2d(3, 2));
        map = new RectangularMap(5,5);

        animal.move(MoveDirection.BACKWARD, map);
        animal.move(MoveDirection.BACKWARD, map);
        animal.move(MoveDirection.BACKWARD, map);
        animal.move(MoveDirection.BACKWARD, map);
        animal.move(MoveDirection.BACKWARD, map);
        animal.move(MoveDirection.BACKWARD, map);

        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(3, 0), animal.getPosition());

    }

    @Test
    void simulationTest() {
        WorldMap map = new RectangularMap(5,5);
        List<MoveDirection> directions = List.of(
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        );
        List<Vector2d> positions = List.of(new Vector2d(2, 2), new Vector2d(3, 4));
        Simulation simulation = new Simulation(positions, directions, map);
        simulation.run();
        List<Animal> animals = simulation.getAnimals();

        assertEquals(MapDirection.SOUTH, animals.get(0).getDirection());
        assertEquals(new Vector2d(2, 0), animals.get(0).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(1).getDirection());
        assertEquals(new Vector2d(3, 4), animals.get(1).getPosition());

    }

}