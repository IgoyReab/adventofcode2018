package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
class LumberCoordinate {
    int x;
    int y;
    String type;

    private boolean openGround;
    private boolean tree;
    private boolean lumberyard;

    private int numberOfAdjacentTrees;
    private int numberOfAdjacentOpenGrounds;
    private int numberOfAdjacentLumberyards;


    public LumberCoordinate(int x, int y) {
        this.x = x;
        this.y = y;

        this.type = "";

        this.openGround = false;
        this.tree = false;
        this.lumberyard = false;

        this.numberOfAdjacentOpenGrounds = 0;
        this.numberOfAdjacentTrees = 0;
        this.numberOfAdjacentLumberyards = 0;
    }
}

@Data
class LumberGrid {
    LumberCoordinate[][] gameGrid;

    int maxX;
    int maxY;
    int trees;
    int lumberyards;
    int opengrounds;

    public LumberGrid(int x, int y) {
        gameGrid = new LumberCoordinate[x][y];
        maxX = x;
        maxY = y;
        trees = 0;
        lumberyards = 0;
        opengrounds = 0;
    }

    public void addLumberyard(int x, int y, LumberCoordinate c) {
        c.setLumberyard(true);
        c.setType("#");
        gameGrid[x][y] = c;
        this.lumberyards++;
    }

    public void addTree(int x, int y, LumberCoordinate c) {
        c.setTree(true);
        c.setType("|");
        gameGrid[x][y] = c;
        this.trees++;

    }

    public void addOpenground(int x, int y, LumberCoordinate c) {
        c.setOpenGround(true);
        c.setType(".");
        gameGrid[x][y] = c;
        this.opengrounds++;
    }

    public void printGrid(String description) {
        String line = "";
        System.out.println("\n---- " + description + " ----");

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                if (gameGrid[x][y].isTree()) line = line + "|";
                if (gameGrid[x][y].isOpenGround()) line = line + ".";
                if (gameGrid[x][y].isLumberyard()) line = line + "#";
            }
            System.out.println(line);
            line = "";
        }
    }

    public void doTree(int x, int y) {
        if (this.gameGrid[x][y].getNumberOfAdjacentLumberyards() >= 3) {
            this.gameGrid[x][y].setTree(false);
            this.gameGrid[x][y].setLumberyard(true);
            this.gameGrid[x][y].setType("#");
            this.trees--;
            this.lumberyards++;
        }
    }

    public void doOpenground(int x, int y) {
        if (this.gameGrid[x][y].getNumberOfAdjacentTrees() >= 3) {
            this.gameGrid[x][y].setTree(true);
            this.gameGrid[x][y].setType("|");
            this.gameGrid[x][y].setOpenGround(false);
            this.trees++;
            this.opengrounds--;
        }
    }

    public void doLumberyard(int x, int y) {
        if (!((this.gameGrid[x][y].getNumberOfAdjacentLumberyards() >= 1) && (this.gameGrid[x][y].getNumberOfAdjacentTrees() >= 1))) {
            this.gameGrid[x][y].setLumberyard(false);
            this.gameGrid[x][y].setOpenGround(true);
            this.gameGrid[x][y].setType(".");
            this.lumberyards--;
            this.opengrounds++;
        }
    }

    public void minuteTurn() {

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
 //               System.out.println(" x = " + x + " y=" + y + " tree:" + gameGrid[x][y].isTree() + " lumberyard:" + gameGrid[x][y].isLumberyard() + " open:" + gameGrid[x][y].isOpenGround() + " #AdjTrees=" + gameGrid[x][y].getNumberOfAdjacentTrees() + " #AdjLumberyards =" + gameGrid[x][y].getNumberOfAdjacentLumberyards() + " #AdjOpen =" + gameGrid[x][y].getNumberOfAdjacentOpenGrounds());
                if (gameGrid[x][y].isTree()) {
                    doTree(x, y);
                } else {
                    if (gameGrid[x][y].isOpenGround()) {
                        doOpenground(x, y);
                    } else {
                        doLumberyard(x, y);
                    }
                }
            }
        }

        this.calculateGrid();
    }

    public void calculateAdjacentAcres(int x, int y, int a, int b) {
        if ((a >= 0) && (a < maxX) && (b >= 0) && (b < maxY)) {
            if (this.gameGrid[a][b].isTree()) {
                this.gameGrid[x][y].setNumberOfAdjacentTrees(this.gameGrid[x][y].getNumberOfAdjacentTrees() + 1);
            }
            if (this.gameGrid[a][b].isLumberyard()) {
                this.gameGrid[x][y].setNumberOfAdjacentLumberyards(this.gameGrid[x][y].getNumberOfAdjacentLumberyards() + 1);
            }
            if (this.gameGrid[a][b].isOpenGround()) {
                this.gameGrid[x][y].setNumberOfAdjacentOpenGrounds(this.gameGrid[x][y].getNumberOfAdjacentOpenGrounds() + 1);
            }
        }
    }

    public void calculateGrid() {
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxY; x++) {
                this.gameGrid[x][y].setNumberOfAdjacentTrees(0);
                this.gameGrid[x][y].setNumberOfAdjacentOpenGrounds(0);
                this.gameGrid[x][y].setNumberOfAdjacentLumberyards(0);

                calculateAdjacentAcres(x, y, x - 1, y);
                calculateAdjacentAcres(x, y, x + 1, y - 1);
                calculateAdjacentAcres(x, y, x, y - 1);
                calculateAdjacentAcres(x, y, x - 1, y - 1);
                calculateAdjacentAcres(x, y, x + 1, y + 1);
                calculateAdjacentAcres(x, y, x, y + 1);
                calculateAdjacentAcres(x, y, x - 1, y + 1);
                calculateAdjacentAcres(x, y, x + 1, y);
            }
        }
    }
}

public class Day_18 {

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();
        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day18.txt"), Charset.forName("utf-8"));

        LumberGrid grid = new LumberGrid(input.get(0).length(), input.size());

        for (int y = 0; y < input.size(); y++) {
            String s = input.get(y);
            for (int x = 0; x < s.length(); x++) {

                LumberCoordinate coordinate = new LumberCoordinate(x, y);

                if (s.charAt(x) == '#') {
                    grid.addLumberyard(x, y, coordinate);
                }

                if (s.charAt(x) == '|') {
                    grid.addTree(x, y, coordinate);
                }

                if (s.charAt(x) == '.') {
                    grid.addOpenground(x, y, coordinate);
                }
            }
        }

        grid.calculateGrid();

  //      grid.printGrid("Initial state:");
        for (long x = 0; x < 10; x++) {
            grid.minuteTurn();
  //          int count = x + 1;
  //          grid.printGrid("After " + count + " minutes:" );
        }

        System.out.println("\nAfter 10 minutes the total resource value is " + grid.getTrees() + " * " + grid.getLumberyards() + " = " + ( grid.getTrees() * grid.getLumberyards()) );

        LocalTime finish = LocalTime.now();
        System.out.println("Part1 Duration (ms): " + Duration.between(start, finish).toMillis());


        start = LocalTime.now();
        //      grid.printGrid("Initial state:");

        // part two

        LumberGrid grid2 = new LumberGrid(input.get(0).length(), input.size());

        for (int y = 0; y < input.size(); y++) {
            String s = input.get(y);
            for (int x = 0; x < s.length(); x++) {

                LumberCoordinate coordinate = new LumberCoordinate(x, y);

                if (s.charAt(x) == '#') {
                    grid2.addLumberyard(x, y, coordinate);
                }

                if (s.charAt(x) == '|') {
                    grid2.addTree(x, y, coordinate);
                }

                if (s.charAt(x) == '.') {
                    grid2.addOpenground(x, y, coordinate);
                }
            }
        }

        grid2.calculateGrid();

        for (long x = 0; x < 503; x++) {
            grid2.minuteTurn();
            //          int count = x + 1;
            //          grid2.printgrid2("After " + count + " minutes:" );

        }

        System.out.println("After 503 minutes the total resource value is " + grid2.getTrees() + " * " + grid2.getLumberyards() + " = " + ( grid2.getTrees() * grid2.getLumberyards()) );

        long count = 1000000000 - 503L;

        count = count % 28;

        for (long x = 0; x < count; x++) {
            grid2.minuteTurn();
            //          int count = x + 1;
            //          grid2.printgrid2("After " + count + " minutes:" );

        }

        System.out.println("After 1000000000 minutes the total resource value is " + grid2.getTrees() + " * " + grid2.getLumberyards() + " = " + ( grid2.getTrees() * grid2.getLumberyards()) );

//
//        ArrayList<LumberGrid> lumberList = new ArrayList<LumberGrid>();
//
//
//        boolean gridIsTheSame = false;
//
//        int count = 0;
//        do {
//            LumberGrid grid3 = new LumberGrid(grid2.maxX, grid2.maxY);
//
//            for (int y = 0; y < grid2.getMaxY(); y++) {
//                for (int x = 0; x < grid2.getMaxX(); x++) {
//                    if (grid2.gameGrid[x][y].getType().equals("#")) {
//                        LumberCoordinate coordinate = new LumberCoordinate(x, y);
//                        grid3.addLumberyard(x, y, coordinate);
//                    }
//
//                    if (grid2.gameGrid[x][y].getType().equals("|")) {
//                        LumberCoordinate coordinate = new LumberCoordinate(x, y);
//                        grid3.addTree(x, y, coordinate);
//                    }
//
//                    if (grid2.gameGrid[x][y].getType().equals(".")) {
//                        LumberCoordinate coordinate = new LumberCoordinate(x, y);
//                        grid3.addOpenground(x, y, coordinate);
//                    }
//                }
//            }
//
//            lumberList.add(grid3);
//
//            count++;
//
//            grid2.minuteTurn();
//
////            grid2.printGrid("After " + count + " minutes:" );
//
//            for(LumberGrid lg : lumberList) {
//
//                for (int y = 0; y < grid2.getMaxY(); y++) {
//                    for (int x = 0; x < grid2.getMaxX(); x++) {
//                        String a = grid2.gameGrid[x][y].getType();
//                        String b = lg.gameGrid[x][y].getType();
//                        gridIsTheSame = (a.equals(b));
//                        if (!gridIsTheSame) break;
//                    }
//                    if (!gridIsTheSame) break;
//                }
//            }
////            System.out.println("Trees : " + grid.getTrees() + " Lumberyards : " + grid.getLumberyards() + " Opengrounds: " + grid.getOpengrounds());
//        } while (!(gridIsTheSame));
//
//        System.out.println("\nAfter 1000000000 minutes the total resource value is " + grid2.getTrees() + " * " + grid2.getLumberyards() + " = " + ( grid2.getTrees() * grid2.getLumberyards()) );

        finish = LocalTime.now();
        System.out.println("Part 2 Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}