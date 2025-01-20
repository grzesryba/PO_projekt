package agh.ics.oop.model.Map;

import agh.ics.oop.model.Map.Grass.Grass;
import agh.ics.oop.model.Others.Vector2d;
import agh.ics.oop.model.util.RandomPositionGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SimpleWorldMap extends AbstractWorldMap {

    public SimpleWorldMap(int width, int height, int grassNo) {
        this.width = width;
        this.height = height;
        this.maxX = width-1;
        this.maxY = height-1;
        this.minX = 0;
        this.minY = 0;

        mapProbability();
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(width, height, grassNo, this.probability, Collections.emptySet());
        this.extraProbabilityGrassFreePositions = randomPositionGenerator.getExtraGrassFreePositions();
        this.normalProbabilityGrassFreePositions = randomPositionGenerator.getNormalGrassFreePositions();
        for (Vector2d grassPosition : randomPositionGenerator) {
            this.grasses.put(grassPosition, new Grass(grassPosition));
        }
    }


    private void mapProbability() {
        boolean[][] map = new boolean[this.height][this.width];

        int n = width * height;
        int fullRows = Math.round((float) n / 5) / width;
        if ((height % 2 == 0 && fullRows % 2 == 1) || (width % 2 == 1 && fullRows % 2 == 0)) {
            fullRows -= 1;
        }
        int left = Math.round((float) (n / 5)) - fullRows * width;

        int a;
        int b;
        if (height % 2 == 0) {
            b = height / 2;
            a = b - 1;
        } else {
            a = height / 2;
            b = a;
        }

        while (fullRows > 0) {
            for (int i = 0; i < width; i++) {
                map[a][i] = true;
                map[b][i] = true;
            }
            fullRows -= 2;
            a -= 1;
            b += 1;
        }

        if (left > 0) {
            Set<Vector2d> complement = RandomPositionGenerator.generateEquatorComplement(width, a, b, left);
            for (Vector2d g : complement) {
                map[g.getY()][g.getX()] = true;
            }
        }
        this.probability = map;
    }

    public List<Grass> getAllGrass() {
        return new ArrayList<>(grasses.values());
    }

}
