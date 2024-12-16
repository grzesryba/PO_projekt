package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassTest {

    @Test
    void testGetPosition() {
        Grass grass = new Grass(new Vector2d(4, 9));
        assertEquals(grass.getPosition(), new Vector2d(4, 9));
    }

    @Test
    void testToString() {
        Grass grass = new Grass(new Vector2d(2, 2));
        assertEquals(grass.toString(),"*");
    }

}