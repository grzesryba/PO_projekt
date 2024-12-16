package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void placeTest() {
        WorldMap map = new GrassField(7);
        Animal animal = new Animal();
        Animal animal2 = new Animal();
        Animal animal3 = new Animal(new Vector2d(10, 3));
        try {
            assertTrue(map.place(animal));
            assertTrue(map.place(animal3));
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));

    }

    @Test
    void objectAtTest() {
        WorldMap map = new GrassField(0);
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
        WorldMap map = new GrassField(0);
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
        WorldMap map = new GrassField(3);
        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        Animal animal = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal);
        } catch (IncorrectPositionException e) {
            fail(e.getMessage());
        }
        assertFalse(map.canMoveTo(new Vector2d(2, 3)));

        assertTrue(map.canMoveTo(new Vector2d(1, 1)));
        assertTrue(map.canMoveTo(new Vector2d(40, 40)));
    }

    @Test
    void moveTest() {
        WorldMap map = new GrassField(45);
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
        WorldMap map = new GrassField(13);
        Animal animal1 = new Animal();
        Animal animal2 = new Animal(new Vector2d(2, 3));
        try {
            map.place(animal1);
            map.place(animal2);
        } catch (IncorrectPositionException e){
            fail(e.getMessage());
        }
        assertEquals(15, map.getElements().size());
    }

}