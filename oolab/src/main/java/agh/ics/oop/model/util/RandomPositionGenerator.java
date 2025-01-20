package agh.ics.oop.model.util;

import agh.ics.oop.model.Others.Vector2d;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d>, Iterator<Vector2d> {
    private final List<Vector2d> positions;
    private final List<Vector2d> normalGrassFreePositions;
    private final List<Vector2d> extraGrassFreePositions;
    private int idx = 0;

    public List<Vector2d> getNormalGrassFreePositions() {
        return normalGrassFreePositions;
    }

    public List<Vector2d> getExtraGrassFreePositions() {
        return extraGrassFreePositions;
    }
//    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount) {
//        List<Vector2d> allPositions = new ArrayList<>();
//        for (int i = 0; i < maxWidth; i++) {
//            for (int j = 0; j < maxHeight; j++) {
//                allPositions.add(new Vector2d(i, j));
//            }
//        }
//        Collections.shuffle(allPositions);
//        this.positions = allPositions.subList(0, grassCount);
//    }

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassNo, boolean[][] mapProbability, Set<Vector2d> occupiedPositions) {
        List<Vector2d> allPositions = new ArrayList<>();
        List<Vector2d> normalGrassPositions = new ArrayList<>();
        List<Vector2d> extraGrassPositions = new ArrayList<>();
        positions = new ArrayList<>();

        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                boolean flag = true;
                Vector2d v = new Vector2d(i, j);
                for (Vector2d p : occupiedPositions) {
                    if (p.equals(v)) {
                        flag = false;
                    }
                }
                if (flag) {
                    if (mapProbability[j][i]) {
                        extraGrassPositions.add(v);
                    } else {
                        normalGrassPositions.add(v);
                    }
                    allPositions.add(v);
                }
            }
        }
        Random rand = new Random();
        Collections.shuffle(normalGrassPositions);
        Collections.shuffle(extraGrassPositions);

        int a = 0;      // index z extraGrassPosition
        int b = 0;      // index z normalGrassPosition

        for (int i = 0; i < grassNo; i++) {
            if (rand.nextInt(5) != 0 && a < extraGrassPositions.size()) {
                positions.add(extraGrassPositions.get(a));
                a += 1;
            } else {
                positions.add(normalGrassPositions.get(b));
                b += 1;
            }
        }
        normalGrassFreePositions = normalGrassPositions.subList(b, normalGrassPositions.size());
        extraGrassFreePositions = extraGrassPositions.subList(a, extraGrassPositions.size());


    }

    public static Set<Vector2d> generateEquatorComplement(int width, int a, int b, int n) {
//        do uzupełnienia jest napewno mniej niż 2 szerokości więc można zrobić 2 przemieszania listy z x-ami
//        aż do uzupełnienia listy, losujemy też Y czy a czy b a przy 2 przemieszaniu dajemy do tego pustego
        Set<Vector2d> result = new HashSet<>();
        Random rand = new Random();
        List<Integer> xs = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            xs.add(i);
        }
        Collections.shuffle(xs);

        int startN = n;
        for (int i = 0; i < Math.min(width, startN); i++) {
            int y = rand.nextInt(2) % 2 == 0 ? a : b;
            result.add(new Vector2d(xs.get(i), y));
            n -= 1;
        }
        if (n > 0) {
            Collections.shuffle(xs);
            for (int i = 0; i < n; i++) {
                int y = rand.nextInt(2) % 2 == 0 ? a : b;
                result.add(new Vector2d(xs.get(i), y));
            }
        }
        return result;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        idx = 0;
        return positions.iterator();
    }

    @Override
    public boolean hasNext() {
        return idx < positions.size();
    }

    @Override
    public Vector2d next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Brak kolejnych pozycji.");
        }
        return positions.get(idx++);
    }
}
