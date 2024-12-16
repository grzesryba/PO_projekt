package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest {

    @Test
    void rightParse() {
        String[] s = {"r"};
        List<MoveDirection> result = List.of(MoveDirection.RIGHT);
        assertEquals(result, OptionParser.parse(s));
    }

    @Test
    void leftParse() {
        String[] s = {"l"};
        List<MoveDirection> result = List.of(MoveDirection.LEFT);
        assertEquals(result, OptionParser.parse(s));
    }

    @Test
    void forwardParse() {
        String[] s = {"f"};
        List<MoveDirection> result = List.of(MoveDirection.FORWARD);
        assertEquals(result, OptionParser.parse(s));
    }

    @Test
    void backwardParse() {
        String[] s = {"b"};
        List<MoveDirection> result = List.of(MoveDirection.BACKWARD);
        assertEquals(result, OptionParser.parse(s));
    }

    @Test
    void notAppropriateOptionParse() {
        String[] s = {"x"};
        String[] s1 = {};
        List<MoveDirection> result = new ArrayList<>();
        assertThrows(IllegalArgumentException.class,() -> OptionParser.parse(s));
        assertEquals(result, OptionParser.parse(s1));
    }

    @Test
    void fullTrackParse() {
        String[] s = {"r", "f", "b", "x", "k", "l", "t", "f"};
        List<MoveDirection> result = List.of(
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD
        );

        assertThrows(IllegalArgumentException.class,() -> OptionParser.parse(s));
    }

}