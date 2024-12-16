package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {

    @Test
    void placeTest() {
        WorldMap map = new RectangularMap(7, 7);
        Animal animal = new Animal();
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(10, 3));
        try {
            assertTrue(map.place(animal));
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
        assertThrows(IncorrectPositionException.class, () -> map.place(animal3));
    }

    @Test
    void objectAtTest() {
        WorldMap map = new RectangularMap(7, 7);
        Animal animal = new Animal(new Vector2d(4, 3));
        assertNull(map.objectAt(new Vector2d(4, 3)));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertEquals(animal, map.objectAt(new Vector2d(4, 3)));
    }

    @Test
    void isOccupiedTest() {
        WorldMap map = new RectangularMap(7, 7);
        assertFalse(map.isOccupied(new Vector2d(2, 3)));
        Animal animal = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertTrue(map.isOccupied(new Vector2d(2, 3)));
    }

    @Test
    void canMoveToTest() {
        WorldMap map = new RectangularMap(7, 7);
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        Animal animal = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));

        assertTrue(map.canMoveTo(new Vector2d(1, 1)));
        assertFalse(map.canMoveTo(new Vector2d(10, 3)));
    }

    @Test
    void moveTest() {
        WorldMap map = new RectangularMap(7, 7);
        Animal animal = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());

        }
        map.move(animal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 4), animal.getPosition());
    }

    @Test
    void getElementsTest() {
        WorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertEquals(2, map.getElements().size());
    }

}