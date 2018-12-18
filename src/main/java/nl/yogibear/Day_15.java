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
        this.type = "";
        this.turnDone = false;
    }

    private boolean elve;
    private boolean goblin;
    private boolean free;
    private boolean wall;
    private boolean turnDone;

    private String type;

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
        c.setType("#");
    }

    public void addElve(int x, int y, Coordinate c) {
        c.setElve(true);
        c.setAttackPower(3);
        c.setHitPoints(200);

        gameGrid[x][y] = c;
        elves++;
        c.setType("E");

    }

    public void addGoblin(int x, int y, Coordinate c) {
        c.setGoblin(true);
        c.setAttackPower(3);
        c.setHitPoints(200);
        gameGrid[x][y] = c;
        goblins++;
        c.setType("G");
    }

    public void addFree(int x, int y, Coordinate c) {
        c.setFree(true);
        gameGrid[x][y] = c;
        c.setType(".");
    }

    public void resetTurn() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                gameGrid[x][y].setTurnDone(false);
            }
        }
    }

    public void printGrid(int s) {
        String line = "";
        String line2 = "";
        System.out.println("\nAfter " + s + " rounds:");

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                line = line + gameGrid[x][y].getType();
                if (gameGrid[x][y].isElve()){
                   line2 = line2 + " E(" +  gameGrid[x][y].getHitPoints() + ") ";
                }
                if (gameGrid[x][y].isGoblin()) {
                    line2 = line2 + " G(" +  gameGrid[x][y].getHitPoints() + ") ";
                }
            }
            System.out.print(line);
            System.out.println(line2);
            line = "";
            line2 = "";
        }
        System.out.println();
    }

    public void attack(int x, int y, int a, int b) {
        gameGrid[a][b].setHitPoints(gameGrid[a][b].getHitPoints() - gameGrid[x][y].getAttackPower());
        if (gameGrid[a][b].getHitPoints() <= 0) {
            gameGrid[a][b].setGoblin(false);
            gameGrid[a][b].setElve(false);
            gameGrid[a][b].setFree(true);
            gameGrid[a][b].setType(".");
        }
    }

    public boolean isBlocked(int a, int b, int x, int y) {   // a = closestX , b = closestY, x = x, y =y
        boolean blocked = false;
        if ( b == y ) {
            if (a < x) {
                for (int t = a; t < x; t++) {
                    blocked = (gameGrid[t][y].isWall());
                }
            } else {
                if (x < a) {
                    for (int t =x; t < a; t++) {
                        blocked = (gameGrid[t][y].isWall());
                    }
                }
            }
        } else {
            if (b < y) {
                for (int t = b ; t < y; t++) {
                    blocked = (gameGrid[x][t].isWall());
                }
            } else {
                if (y < b) {
                    for (int t = y; t < b; t++) {
                        blocked = (gameGrid[x][t].isWall());
                    }
                }
            }

        }
        return blocked;
    }

    public void doElveMove(int x, int y) {
        int closestX = 100000;
        int closestY = 100000;
        int closestDistance = 100000;
        boolean blocked = false;
        for (int a = 0; a < maxY; a++) {
            for (int b = 0; b < maxY; b++) {
                if ((gameGrid[a][b].isGoblin()) && (!isBlocked(a, b, x, y))){
                    if (Util.distance(x, y, a, b ) < closestDistance) {
                        closestX = a;
                        closestY = b;
                        closestDistance = Util.distance(x, y, a, b);
                    }
                }
            }

        }

        int d = 0;
        int e = 0;
        int distance = 500000;
        int a = closestX - 1;
        int b = closestY;
        int c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c) && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX;
        b = closestY + 1;
        c = Util.distance(x , y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c) && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX + 1;
        b = closestY;
        c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c) && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX;
        b = closestY - 1;
        c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c) && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        closestX = d;
        closestY = e;
        closestDistance = distance;



        d = 0;
        e = 0;
        distance = 500000;
        boolean goMove = false;

        a = x;
        b = y - 1;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x - 1;
        b = y;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x ;
        b = y + 1;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x + 1;
        b = y;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        if (goMove) {
            gameGrid[x][y].setFree(true);
            gameGrid[x][y].setElve(false);
            gameGrid[x][y].setType(".");
            gameGrid[d][e].setElve(true);
            gameGrid[d][e].setFree(false);
            gameGrid[d][e].setType("E");
            gameGrid[d][e].setHitPoints(gameGrid[x][y].getHitPoints());
            gameGrid[d][e].setAttackPower(gameGrid[x][y].getAttackPower());
            gameGrid[d][e].setTurnDone(true);
            gameGrid[x][y].setHitPoints(0);
            gameGrid[x][y].setAttackPower(0);
        }


    }

    public void doGoblinMove(int x, int y) {
        int closestX = 100000;
        int closestY = 100000;
        int closestDistance = 100000;
        for (int a = 0; a < maxX; a++) {
            for (int b = 0; b < maxY; b++) {
                if ((gameGrid[a][b].isElve()) && (!isBlocked(a, b, x, y))){
                    if (Util.distance(x, y, a, b) < closestDistance) {
                        closestX = a;
                        closestY = b;
                        closestDistance = Util.distance(x, y, a, b);
                    }
                }
            }
        }

        int d = 0;
        int e = 0;
        int distance = 500000;
        int a = closestX - 1;
        int b = closestY;
        int c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)  && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX;
        b = closestY + 1;
        c = Util.distance(x , y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)  && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX + 1;
        b = closestY;
        c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)  && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        a = closestX;
        b = closestY - 1;
        c = Util.distance(x, y, a, b);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)  && !isBlocked(a, b, x, y)) {
            distance = c;
            d = a;
            e = b;
        }

        closestX = d;
        closestY = e;
        closestDistance = distance;

        d = 0;
        e = 0;
        distance = 500000;
        boolean goMove = false;

        a = x;
        b = y - 1;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x - 1;
        b = y;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x ;
        b = y + 1;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }

        a = x + 1;
        b = y;
        c = Util.distance(a, b, closestX, closestY);
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && gameGrid[a][b].isFree() && (distance >= c)) {
            distance = c;
            goMove = true;
            d = a;
            e = b;
        }



        if (goMove) {
            gameGrid[x][y].setFree(true);
            gameGrid[x][y].setGoblin(false);
            gameGrid[x][y].setType(".");
            gameGrid[d][e].setGoblin(true);
            gameGrid[d][e].setType("G");
            gameGrid[d][e].setFree(false);
            gameGrid[d][e].setHitPoints(gameGrid[x][y].getHitPoints());
            gameGrid[d][e].setAttackPower(gameGrid[x][y].getAttackPower());
            gameGrid[d][e].setTurnDone(true);
            gameGrid[x][y].setHitPoints(0);
            gameGrid[x][y].setAttackPower(0);
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

            a = x;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x ;
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

//            a = x + 1;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x - 1;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x + 1;
//            b = y;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isGoblin()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }

            if (goAttack) {
                attack(x, y, d, e) ;
            } else {
                doElveMove(x , y);
            }
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


            a = x;
            b = y - 1;
            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
                goAttack = true;
                hitPoints = gameGrid[a][b].getHitPoints();
                d = a;
                e = b;
            }

            a = x ;
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
//
//            a = x + 1;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x - 1;
//            b = y + 1;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }
//
//            a = x + 1;
//            b = y;
//            if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY) && (gameGrid[a][b].isElve()) && (hitPoints >= gameGrid[a][b].getHitPoints())) {
//                goAttack = true;
//                hitPoints = gameGrid[a][b].getHitPoints();
//                d = a;
//                e = b;
//            }

            if (goAttack) {
                attack(x, y, d, e) ;
            } else {
                doGoblinMove(x , y);
            }
        }


    }

    public void gameTurn() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                if ( (gameGrid[x][y].isElve()) && (!(gameGrid[x][y].isTurnDone()))) doElve(x, y);
                if ( (gameGrid[x][y].isGoblin()) && (!(gameGrid[x][y].isTurnDone()))) doGoblin(x, y);
            }
        }
        resetTurn();

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

        grid.printGrid(0);
        for (int x = 0; x < 50; x++ ) {
            grid.gameTurn();
            grid.printGrid(x + 1);
        }
    }
}
