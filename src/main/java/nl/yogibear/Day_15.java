package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Data
class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        this.elve = false;
        this.goblin = false;
        this.free = false;
        this.wall = false;
    }

    private boolean elve;
    private boolean goblin;
    private boolean free;
    private boolean wall;

    private int hitPoints;
    private int attackPower;
}

@Data
class Grid {
    Coordinate[][] gameGrid;

    int maxX;
    int maxY;
    int goblins;
    int elves;

    public Grid(int x, int y) {
        gameGrid = new Coordinate[x][y];
        maxX = x;
        maxY = y;
        goblins = 0;
        elves = 0;
    }

    public void addWall(int x, int y, Coordinate c) {
        c.setWall(true);
        gameGrid[x][y] = c;
    }

    public void addElve(int x, int y, Coordinate c) {
        c.setElve(true);
        c.setAttackPower(3);
        c.setHitPoints(200);

        gameGrid[x][y] = c;
        elves++;

    }

    public void addGoblin(int x, int y, Coordinate c) {
        c.setGoblin(true);
        c.setAttackPower(3);
        c.setHitPoints(200);
        gameGrid[x][y] = c;
        goblins++;
    }

    public void addFree(int x, int y, Coordinate c) {
        c.setFree(true);
        gameGrid[x][y] = c;
    }


    public void printGrid() {
        String line = "";

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                if (gameGrid[x][y].isElve()) line = line + "E";
                if (gameGrid[x][y].isGoblin()) line = line + "G";
                if (gameGrid[x][y].isWall()) line = line + "#";
                if (gameGrid[x][y].isFree()) line = line + ".";
            }
            System.out.println(line);
            line = "";
        }

//        System.out.println(line);
    }

    public Coordinate findNearestElve(int x, int y) {
        int nearest = 100000;
        for (int a = 0; a < maxY; a++) {
            for (int b = 0; b < maxY; b++) {
                if (gameGrid[x][y].isElve()) {
                    if (nearest > Util.distance(x, y, a, b)) nearest = Util.distance(x, y, a, b);
                }
            }
        }
        return null;
    }

    public void attack(int x, int y, int a, int b) {
        gameGrid[a][b].setHitPoints(gameGrid[a][b].getHitPoints() - gameGrid[x][y].getAttackPower());
        if (gameGrid[a][b].getHitPoints() <= 0) {
            gameGrid[a][b].setGoblin(false);
            gameGrid[a][b].setElve(false);
            gameGrid[a][b].setFree(true);
        }
    }


    public void doElve(int x, int y) {
        if (goblins > 0) {
            int hitPoints = 201;
            boolean goAttack = false;
            int d = 0;
            int e = 0;

            int a = x - 1;
            int b = y;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x ;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x - 1;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x - 1;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            if (goAttack) attack(x, y, d, e);
        }

    }


    public void doGoblin(int x, int y) {
        if (elves > 0) {
            int hitPoints = 201;
            boolean goAttack = false;
            int d = 0;
            int e = 0;

            int a = x - 1;
            int b = y;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x ;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x - 1;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x - 1;
            b = y + 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x + 1;
            b = y;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            if (goAttack) attack(x, y, d, e);
        }


    }

    public void gameTurn() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {

                if (gameGrid[x][y].isElve()) doElve(x, y);
                if (gameGrid[x][y].isGoblin()) doGoblin(x, y);

            }
        }
    }
}


public class Day_15 {

    public static void main(String[] args) throws IOException {

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day15-tst.txt"), Charset.forName("utf-8"));

        Grid grid = new Grid(input.get(0).length(), input.size());

        for (int y = 0; y < input.size(); y++) {
            String s = input.get(y);
            for (int x = 0; x < s.length(); x++) {

                Coordinate coordinate = new Coordinate(x, y);

                if (s.charAt(x) == '#') {
                    grid.addWall(x, y, coordinate);
                }

                if (s.charAt(x) == 'E') {
                    grid.addElve(x, y, coordinate);
                }

                if (s.charAt(x) == 'G') {
                    grid.addGoblin(x, y, coordinate);
                }

                if (s.charAt(x) == '.') {
                    grid.addFree(x, y, coordinate);
                }
            }
        }

        grid.printGrid();
        for (int x = 0; x < 202; x++ ) {
            grid.gameTurn();
            grid.printGrid();
        }
    }
}
