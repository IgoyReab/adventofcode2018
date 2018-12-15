package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Util {
    public static int distance(int x, int y, int a, int b) {
        return (Math.abs(x - a) + Math.abs(y - b));
    }

    public static void log(String line, boolean nl) {
        if (nl) {
           System.out.println(line);
        } else {
            System.out.print(line);
        }
    }
}


@Data
class Coordinate {
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private int x;
    private int y;
}

@Data
class Soldier {
    private int x;
    private int y;

    private boolean elve;
    private boolean goblin;

    private int hitPoints;
    private int attackPower;

    public Soldier(int x, int y, boolean elve, boolean goblin) {
        this.x = x;
        this.y = y;
        this.elve = elve;
        this.goblin = goblin;
        this.hitPoints = 200;
        this.attackPower = 3;
    }
}


@Data
class Grid {
    private List<Coordinate> gridList;
    private List<Coordinate> wallList;
    private List<Soldier> elveList;
    private List<Soldier> goblinList;
    private List<Coordinate> freeList;

    public Grid() {
        this.gridList = new ArrayList<>();
        this.wallList = new ArrayList<>();
        this.elveList = new ArrayList<>();
        this.goblinList = new ArrayList<>();
        this.freeList = new ArrayList<>();
    }

    public void addWall(Coordinate c) {
        gridList.add(c);
        wallList.add(c);
    }

    public void addElve(Coordinate c) {
        gridList.add(c);
        elveList.add(c);
    }

    public void addGoblin(Coordinate c) {
        gridList.add(c);
        goblinList.add(c);
    }

    public void addFree(Coordinate c) {
        gridList.add(c);
        freeList.add(c);
    }

    public void removeElve(int index) {
        elveList.remove(index);
    }

    public void removeGoblin(int index) {
        elveList.remove(index);
    }

    public void removeFree(int index) {
        freeList.remove(index)



    public Coordinate findCoordinate(int x, int y) {
        for (Coordinate c : this.gridList) {
            if ((x == c.getX()) && (y == c.getY())) return c;
        }
        return null;
    }

    public Soldier findElve(int x, int y) {
        for (Soldier s : this.elveList) {
            if ((x == s.getX()) && (y == s.getY())) return s;
        }
        return null;
    }

    public Soldier findGoblin(int x, int y) {
        for (Soldier s : this.goblinList) {
            if ((x == s.getX()) && (y == s.getY())) return s;
        }
        return null;
    }

    public boolean isWall(int x, int y) {
        for (Coordinate c : this.wallList) {
            if ((x == c.getX()) && (y == c.getY())) return true;
        }
        return false;
    }

    public boolean isFree(int x, int y) {
        for (Coordinate c : this.freeList) {
            if ((x == c.getX()) && (y == c.getY())) return true;
        }
        return false;
    }

    public boolean isGoblin(int x, int y) {
        for (Soldier s : this.goblinList) {
            if ((x == s.getX()) && (y == s.getY())) return true;
        }
        return false;
    }

    public boolean isElve(int x, int y) {
        for (Soldier s : this.elveList) {
            if ((x == s.getX()) && (y == s.getY())) return true;
        }
        return false;
    }
}

public class Day_15 {

    public static void main(String[] args) throws IOException {

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day12.txt"), Charset.forName("utf-8"));

        Grid grid = new Grid();

        int y = 0;
        for (String s : input) {
            for (int x=0; x<s.length(); x++) {

                if (s.charAt(x).equals('#')) {
                    Coordinate coordinate = new Coordinate(x,y);
                    grid.



                        }
                    };
                }
            }


        }
    }
}
