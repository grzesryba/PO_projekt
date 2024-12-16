package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.List;

public class OptionParser {
    public static List<MoveDirection> parse(String[] s){

        List<MoveDirection> directions = new ArrayList<>();

        for (int i = 0; i < s.length; i++) {
            if (s[i].equals("f")){
                directions.add(MoveDirection.FORWARD);
            }
            else if (s[i].equals("b")){
                directions.add(MoveDirection.BACKWARD);
            }
            else if (s[i].equals("r")){
                directions.add(MoveDirection.RIGHT);
            }
            else if (s[i].equals("l")){
                directions.add(MoveDirection.LEFT);
            }
            else{
                throw new IllegalArgumentException(s[i] + " is not legal move specification");
            }
        }
        return directions;
    }
}
