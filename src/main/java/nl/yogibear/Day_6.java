package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
class coordinate {
    private int id;
    private int x;
    private int y;

    public coordinate(int id, int x, int y) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}

@Data
class gridCoordinate {
    private int x;
    private int y;
    private int closestByCoordinate;
    private int distance;
    private boolean base;
    private boolean notClosestToAny;
    private boolean finite;
    private int distanceToAllCoordinates;
    private boolean inRegion;

    private int simpleManhattanDistance(int x, int y, int a, int b ) {
        return (Math.abs(x - a) + Math.abs(y - b) );
    }

    public gridCoordinate(int x, int y, ArrayList<coordinate> Coordinates ) {
        this.x = x;
        this.y = y;

        int temp = 800;
        int foundId = 0;
        int mD = 0;

        for (int i=0; i<Coordinates.size(); i++) {

            mD = simpleManhattanDistance( Coordinates.get(i).getX(), Coordinates.get(i).getY(), x, y);
            if ( mD < temp) {
                temp = mD;
                foundId = Coordinates.get(i).getId();
            }
        }

        this.base = (temp == 0);
        this.finite = true;

        this.distance = temp;
        this.closestByCoordinate = foundId;


        int totalDinstance = 0;
        for (int i=0; i<Coordinates.size(); i++) {

            mD = simpleManhattanDistance( Coordinates.get(i).getX(), Coordinates.get(i).getY(), x, y);
            totalDinstance = totalDinstance + mD;
            if ( (mD != 0) && ( mD == temp) && (Coordinates.get(i).getId() != foundId)) {
                this.notClosestToAny = true;
            }
        }

        this.distanceToAllCoordinates = totalDinstance;
        this.inRegion =  (totalDinstance < 10000);
    }
}


public class Day_6 {
    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        int arraySize = 400;
        List<String> coordinates = null;

        coordinates = Files.readLines(new File("src/main/resources/day6.txt"), Charset.forName("utf-8"));

        ArrayList<coordinate> coordinateList = new ArrayList<>();

        int count = 0;
        for (String c : coordinates) {
            count++;

            c = c.replace(" ","");

            String[] xy = c.split(",");
            coordinate thisCoordinate = new coordinate(count, Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            coordinateList.add(thisCoordinate);
        }

        gridCoordinate[][] grid = new gridCoordinate[arraySize][arraySize];

        for (int x = 0; x < arraySize; x++ ) {
            for (int y = 0; y < arraySize; y++) {
                gridCoordinate gC = new gridCoordinate(x, y, coordinateList);
                grid[x][y] = gC;
            }
        }

        int close = 0;
        for (int x = 0; x < arraySize; x++ ) {
            close = grid[x][0].getClosestByCoordinate();
            for (int a = 0; a < arraySize; a++ ) {
                for (int b = 0; b < arraySize; b++) {
                    if (grid[a][b].getClosestByCoordinate() == close) {
                        grid[a][b].setFinite(false);
                    }
                }
            }
        }

        for (int z = 0; z < arraySize; z++ ) {
            close = grid[0][z].getClosestByCoordinate();
            for (int a = 0; a < arraySize; a++ ) {
                for (int b = 0; b < arraySize; b++) {
                    if (grid[a][b].getClosestByCoordinate() == close) {
                        grid[a][b].setFinite(false);
                    }
                }
            }
        }


        for (int x = 0; x < arraySize; x++ ) {
            close = grid[x][arraySize - 1].getClosestByCoordinate();
            for (int a = 0; a < arraySize; a++ ) {
                for (int b = 0; b < arraySize; b++) {
                    if ( grid[a][b].getClosestByCoordinate() == close) {
                        grid[a][b].setFinite(false);
                    }
                }
            }
        }

        for (int z = 0; z < arraySize; z++ ) {
            close = grid[arraySize - 1][z].getClosestByCoordinate();
            for (int a = 0; a < arraySize; a++ ) {
                for (int b = 0; b < arraySize; b++) {
                    if (grid[a][b].getClosestByCoordinate() == close) {
                        grid[a][b].setFinite(false);
                    }
                }
            }
        }

        int maxCount = 0;
        int regionCount = 0;

        int foundId = 0;

        for (int i=0; i<coordinateList.size(); i++) {
            count = 0;
            for (int x = 0; x < arraySize; x++ ) {
                for (int y = 0; y < arraySize; y++) {
                    if ((grid[x][y].getClosestByCoordinate() == coordinateList.get(i).getId()) && (grid[x][y].isFinite()) && (!grid[x][y].isNotClosestToAny())) {
                        count++;
                    }
                }
            }
            if (count > maxCount) {
                maxCount  = count;
                foundId = coordinateList.get(i).getId();
            }
        }

        count = 0;
        for (int x = 0; x < arraySize; x++ ) {
            for (int y = 0; y < arraySize; y++) {
                if (grid[x][y].isBase()) count++;
                if (grid[x][y].isInRegion()) regionCount++;
            }
        }

        String line1 = "   ";

        for (int x = 0; x < arraySize; x++) {
            line1 = line1 + String.format("%3d", x) + " ";
        }

        System.out.println(line1);


        String line2 = "";
        for (int y = 0; y < arraySize; y++ ) {

            line2 = String.format("%3d", y);
            for (int x = 0; x < arraySize; x++) {
                if (grid[x][y].isBase()) {
                    line2 = line2 + "  * ";
                } else {
                    if (grid[x][y].isNotClosestToAny()) {
                        line2 = line2 + "  . ";
                    } else {
                        if (grid[x][y].isFinite()) {
                            line2 = line2 + String.format("%3d", grid[x][y].getClosestByCoordinate()) + " ";
                        } else {
                            line2 = line2 + "  - ";
                        }
                    }
                }
            }

            System.out.println(line2);
        }

        System.out.println("\n\nOpdracht 2\n");
        System.out.println(line1);

        line2 = "";
        for (int y = 0; y < arraySize; y++ ) {

            line2 = String.format("%3d", y);
            for (int x = 0; x < arraySize; x++) {
                if (grid[x][y].isBase()) {
                    line2 = line2 + "  * ";
                } else {
                    if (grid[x][y].isInRegion()) {
                        line2 = line2 + String.format("%3d", grid[x][y].getDistanceToAllCoordinates()) + " ";
                    } else {
                        line2 = line2 + " -- ";
                    }
                }
            }

            System.out.println(line2);
        }



        System.out.println("\nThe number of coordinates is " + coordinateList.size() + " and the number of base coordinates in the grid is " + count);

        System.out.println("\nThe largest area that is not infinite is " + foundId + " and the size is " + maxCount);

        System.out.println("\nThe size of the region containing all locations which have a total\ndistance to all given coordinates of less than 10000 is " + regionCount);


        LocalTime finish = LocalTime.now();

        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    };
}
