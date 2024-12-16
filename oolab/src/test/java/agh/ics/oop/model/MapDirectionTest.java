package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void doesNextWork() {
//        given
        MapDirection e = MapDirection.EAST;
        MapDirection w = MapDirection.WEST;
        MapDirection n = MapDirection.NORTH;
        MapDirection s = MapDirection.SOUTH;

//        when

        MapDirection e1 = e.next();
        MapDirection w1 = w.next();
        MapDirection n1 = n.next();
        MapDirection s1 = s.next();

//        then
        assertEquals(MapDirection.SOUTH, e1);
        assertEquals(MapDirection.NORTH, w1);
        assertEquals(MapDirection.EAST, n1);
        assertEquals(MapDirection.WEST, s1);
    }


    @Test
    void doesPreviousWork() {
//        given
        MapDirection e = MapDirection.EAST;
        MapDirection w = MapDirection.WEST;
        MapDirection n = MapDirection.NORTH;
        MapDirection s = MapDirection.SOUTH;

//        when

        MapDirection e1 = e.previous();
        MapDirection w1 = w.previous();
        MapDirection n1 = n.previous();
        MapDirection s1 = s.previous();

//        then
        assertEquals(MapDirection.NORTH, e1);
        assertEquals(MapDirection.SOUTH, w1);
        assertEquals(MapDirection.WEST, n1);
        assertEquals(MapDirection.EAST, s1);
    }

}