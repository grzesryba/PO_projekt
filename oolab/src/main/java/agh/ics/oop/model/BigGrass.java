package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class BigGrass {

    private final List<BigGrassPiece> pieces = new ArrayList<>();

    public BigGrass(Vector2d bottomLeftCorner) {
        pieces.add(new BigGrassPiece(bottomLeftCorner,this));
        pieces.add(new BigGrassPiece(bottomLeftCorner.add(new Vector2d(1,0)),this));
        pieces.add(new BigGrassPiece(bottomLeftCorner.add(new Vector2d(1,-1)),this));
        pieces.add(new BigGrassPiece(bottomLeftCorner.add(new Vector2d(0,-1)),this));
    }

    public List<BigGrassPiece> getPieces() {
        return pieces;
    }
}
