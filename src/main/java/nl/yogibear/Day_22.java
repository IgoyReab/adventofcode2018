package nl.yogibear;

import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

@Data
class Rescuer {
    private int x;
    private int y;

    private boolean torch;
    private boolean climbingGear;
    private boolean neither;

    private int timeSpent;

    public Rescuer() {
        this.x = 0;
        this.y = 0;
        this.torch = true;
        this.climbingGear = false;
        this.neither = false;
        this.timeSpent = 0;
    }

    public void moveRescuer (int x, int y, boolean torch, boolean climbingGear, boolean neither) {
        this.timeSpent =  this.timeSpent + Util.distance(this.x, this.y, x , y);
        if (neither && !(this.neither)) {
            this.timeSpent = this.timeSpent + 7;
        } else {
            if (climbingGear && !(this.climbingGear)) {
                this.timeSpent = this.timeSpent + 7;
            } else {
                if (torch && !(this.torch)) {
                    this.timeSpent = this.timeSpent + 7;
                }
            }
        }
        this.torch = torch;
        this.climbingGear = climbingGear;
        this.neither = neither;

        this.x = x;
        this.y = y;
    }


}

@Data
class CaveCoordinate {
    int x;
    int y;
    String type;

    private boolean narrow;
    private boolean wet;
    private boolean rocky;
    private boolean mouth;
    private boolean target;

    private int geologicIndex;
    private int erosionLevel;

    public CaveCoordinate(int x, int y) {
        this.x = x;
        this.y = y;

        this.type = "";

        this.narrow = false;
        this.wet = false;
        this.rocky = false;

        this.geologicIndex = 0;
        this.erosionLevel = 0;
    }
}

@Data
class Cave {
    CaveCoordinate[][] caveCoordinates;

    private int maxX;
    private int maxY;
    private int wet;
    private int narrow;
    private int rocky;

    public Cave(int x, int y) {
        caveCoordinates = new CaveCoordinate[x][y];
        maxX = x;
        maxY = y;
        wet = 0;
        narrow = 0;
        rocky = 0;

        for (int b = 0; b < y; b++) {
            for (int a = 0; a < x; a++) {
                CaveCoordinate coordinate = new CaveCoordinate(a, b);
                this.caveCoordinates[a][b] = coordinate;
            }
        }
    }

    public void printCave(String description) {
        String line = "";
        System.out.println("\n---- " + description + " ----");

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                if (caveCoordinates[x][y].isTarget()){
                    line = line + "T";
                    continue;
                }
                if (caveCoordinates[x][y].isMouth()){
                    line = line + "M";
                    continue;
                }
                if (caveCoordinates[x][y].isNarrow()){
                    line = line + "|";
                    continue;
                }
                if (caveCoordinates[x][y].isWet()){
                    line = line + "=";
                    continue;
                }
                if (caveCoordinates[x][y].isRocky()){
                    line = line + ".";
                    continue;
                }

                line = line + "?";

            }
            System.out.println(line);
            line = "";
        }
    }
}

public class Day_22 {

//    public static int DEPTH = 3558;
//    public static int TARGET_X = 15;
//    public static int TARGET_Y = 740;

    public static int DEPTH = 510;
    public static int TARGET_X = 10;
    public static int TARGET_Y = 10;

    public static int MAZE = TARGET_Y + 20;

    public static void main(String[] args) throws InterruptedException {
        LocalTime start = LocalTime.now();

        Cave cave = new Cave(MAZE, MAZE);

        cave.caveCoordinates[0][0].setMouth(true);
        cave.caveCoordinates[TARGET_X][TARGET_Y].setTarget(true);

        for (int z = 0; z<MAZE; z++) {

            int y = z;
            for (int x = z; x < MAZE; x++) {
                if  ((x==0) && (y == 0)) {
                    cave.caveCoordinates[x][y].setGeologicIndex(0);
                    cave.caveCoordinates[x][y].setErosionLevel(DEPTH % 20183);
                    cave.caveCoordinates[x][y].setRocky((((DEPTH % 20183) % 3)) == 0);
                    cave.caveCoordinates[x][y].setWet((((DEPTH % 20183) % 3)) == 1);
                    cave.caveCoordinates[x][y].setNarrow((((DEPTH % 20183) % 3)) == 2);
                    continue;
                }

                if (y==0) {
                    cave.caveCoordinates[x][y].setGeologicIndex(x * 16807);
                    cave.caveCoordinates[x][y].setErosionLevel(((x * 16807) + DEPTH) % 20183);
                    cave.caveCoordinates[x][y].setRocky((((((x * 16807) + DEPTH) % 20183) % 3)) == 0);
                    cave.caveCoordinates[x][y].setWet((((((x * 16807) + DEPTH) % 20183) % 3)) == 1);
                    cave.caveCoordinates[x][y].setNarrow((((((x * 16807) + DEPTH) % 20183) % 3)) == 2);
                    continue;
                }


                if ((x==TARGET_X) && (y==TARGET_Y)) {
                    cave.caveCoordinates[x][y].setGeologicIndex(0);
                    cave.caveCoordinates[x][y].setErosionLevel((DEPTH) % 20183);
                    cave.caveCoordinates[x][y].setRocky(((((DEPTH) % 20183) % 3)) == 0);
                    cave.caveCoordinates[x][y].setWet(((((DEPTH) % 20183) % 3)) == 1);
                    cave.caveCoordinates[x][y].setNarrow(((((DEPTH) % 20183) % 3)) == 2);
                    continue;
                }

                int erosionUp = cave.caveCoordinates[x][y-1].getErosionLevel();
                int erosionLeft = cave.caveCoordinates[x-1][y].getErosionLevel();

                cave.caveCoordinates[x][y].setGeologicIndex(  erosionLeft * erosionUp);

                int geological = cave.caveCoordinates[x][y].getGeologicIndex();


                cave.caveCoordinates[x][y].setErosionLevel((geological + DEPTH) % 20183);

                int erosionThis = cave.caveCoordinates[x][y].getErosionLevel();

                cave.caveCoordinates[x][y].setRocky(erosionThis % 3 == 0);
                cave.caveCoordinates[x][y].setWet(erosionThis % 3 == 1);
                cave.caveCoordinates[x][y].setNarrow(erosionThis % 3 == 2);


            }

            int x = z;
            for (y = z + 1; y < MAZE; y++) {

                if (x == 0) {
                    cave.caveCoordinates[x][y].setGeologicIndex(y * 48271);
                    cave.caveCoordinates[x][y].setErosionLevel(((y * 48271) + DEPTH) % 20183);
                    cave.caveCoordinates[x][y].setRocky((((((y * 48271) + DEPTH) % 20183) % 3)) == 0);
                    cave.caveCoordinates[x][y].setWet((((((y * 48271) + DEPTH) % 20183) % 3)) == 1);
                    cave.caveCoordinates[x][y].setNarrow((((((y * 48271) + DEPTH) % 20183) % 3)) == 2);
                    continue;
                }

                if ((x==TARGET_X) && (y==TARGET_Y)) {
                    cave.caveCoordinates[x][y].setGeologicIndex(0);
                    cave.caveCoordinates[x][y].setErosionLevel((DEPTH) % 20183);
                    cave.caveCoordinates[x][y].setRocky(((((DEPTH) % 20183) % 3)) == 0);
                    cave.caveCoordinates[x][y].setWet(((((DEPTH) % 20183) % 3)) == 1);
                    cave.caveCoordinates[x][y].setNarrow(((((DEPTH) % 20183) % 3)) == 2);
                    continue;
                }



                int erosionUp = cave.caveCoordinates[x][y - 1].getErosionLevel();
                int erosionLeft = cave.caveCoordinates[x - 1][y].getErosionLevel();

                cave.caveCoordinates[x][y].setGeologicIndex(erosionLeft * erosionUp);

                int geological = cave.caveCoordinates[x][y].getGeologicIndex();

                cave.caveCoordinates[x][y].setErosionLevel((geological + DEPTH) % 20183);

                int erosionThis = cave.caveCoordinates[x][y].getErosionLevel();

                cave.caveCoordinates[x][y].setRocky(erosionThis % 3 == 0);
                cave.caveCoordinates[x][y].setWet(erosionThis % 3 == 1);
                cave.caveCoordinates[x][y].setNarrow(erosionThis % 3 == 2);
            }
        }

        int riskLevel = 0;
        for (int y = 0; y <= TARGET_Y; y++){
            for (int x = 0; x <= TARGET_X; x++){
                if (cave.caveCoordinates[x][y].isNarrow()) {
                    riskLevel = riskLevel + 2;
                    continue;
                }
                if (cave.caveCoordinates[x][y].isWet()) {
                    riskLevel++;
                    continue;
                }
            }
        }


        cave.printCave("Cave");

        System.out.println("The total risk level for the smallest rectangle that includes 0,0 and the target's coordinates is " + riskLevel);

        LocalTime finish = LocalTime.now();
        System.out.println("Part 2 Duration (ms): " + Duration.between(start, finish).toMillis());


        //// PART TWO

        start = LocalTime.now();

        int distance = Util.distance(0,0,TARGET_X, TARGET_Y);

        int timeSpent = distance;

        ArrayList<Coordinate> path = new ArrayList<>();

        Rescuer rescuer = new Rescuer();



        System.out.println("The last rescue mission took " + rescuer.getTimeSpent() + " minutes");

        finish = LocalTime.now();
        System.out.println("Part 2 Duration (ms): " + Duration.between(start, finish).toMillis());

    }
}
