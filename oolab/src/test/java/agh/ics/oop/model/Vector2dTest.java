package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    Vector2d a = new Vector2d(3,4);
    Vector2d b = new Vector2d(1,2);
    Vector2d c = new Vector2d(-3,-4);
    Vector2d d = new Vector2d(4,1);
    Vector2d e = new Vector2d(2,3);


    @Test
    void equalsTest(){
        assertTrue(a.equals(a));
        assertFalse(a.equals(c));
    }

    @Test
    void toStringTest(){
        assertEquals("(3,4)",a.toString());
    }

    @Test
    void precedesTest(){
        assertTrue(b.precedes(a));
        assertFalse(a.precedes(b));
        assertTrue(a.precedes(a));
    }

    @Test
    void followsTest(){
        assertFalse(b.follows(a));
        assertTrue(a.follows(b));
        assertTrue(a.follows(a));
    }

    @Test
    void upperRightTest(){
        assertEquals(new Vector2d(4,3), e.upperRight(d));
        assertEquals(new Vector2d(4,3), d.upperRight(e));
    }

    @Test
    void lowerLeftTest(){
        assertEquals(new Vector2d(2,1), e.lowerLeft(d));
        assertEquals(new Vector2d(2,1), d.lowerLeft(e));
    }

    @Test
    void addTest(){
        assertEquals(new Vector2d(4,6), a.add(b));
        assertEquals(new Vector2d(4,6), b.add(a));
    }

    @Test
    void subtractTest(){
        assertEquals(new Vector2d(2,2), a.subtract(b));
        assertNotEquals(new Vector2d(2,2), b.subtract(a));
    }

    @Test
    void oppositeTest(){
        assertEquals(c, a.opposite());
    }

}