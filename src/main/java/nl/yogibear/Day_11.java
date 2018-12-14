package nl.yogibear;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_11 {
    //   final static int INPUT = 3613;
    final static int INPUT = 3613;

    static public int Calculatelevel(int x, int y) {

        int rackId = x + 10;
        int result = rackId * y;
        result = result + INPUT;
        result = result * rackId;
//        System.out.print(result + " ");
        result = result % 1000;
        result = result / 100;
//        System.out.println(result);

        result = result - 5;

        return result;

    }

    public static void main(String[] args) throws IOException {

        LocalTime start = LocalTime.now();

        int[][] powerGrid = new int[300][300];

        for (int x=0; x < 300; x++) {
            for (int y=0; y < 300; y++) {

                powerGrid[x][y] = Calculatelevel(x,y);
            }
        }

//        for (int x=33; x < 38; x++) {
//            for (int y=45; y < 50; y++) {
////                System.out.print(" " + powerGrid[x][y]);
//            }
////            System.out.println();
//        }


        int max = -100000;
        int power = 0;
        int maxX = 0;
        int maxY = 0;
        for (int x=0; x< 297; x++) {
            for (int y=0; y < 297; y++) {
                power = 0;
                for (int a=x;a < x+3; a++) {
                    for (int b=y;b < y + 3; b++) {
                        power = power + powerGrid[a][b];
                    }
                }
//                System.out.println("Power : = " + power);
                if (power > max) {
                    max = power;
                    maxX = x;
                    maxY = y;
                }
            }
        }
        System.out.println("The maximum powerlevel is " + max + " at (" + maxX + "," + maxY + ").");

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        // Part 2

        start = LocalTime.now();

        max = -100000;
        power = 0;
        maxX = 0;
        maxY = 0;
        int maxC = 0;
        for (int c=0; c<300; c++ ) {
            for (int x = 0; x < 300-c; x++) {
                for (int y = 0; y < 300-c; y++) {
                    power = 0;
                    for (int a = x; a < x + c; a++) {
                        for (int b = y; b < y + c; b++) {
                            power = power + powerGrid[a][b];
                        }
                    }
//                System.out.println("Power : = " + power);
                    if (power > max) {
                        max = power;
                        maxX = x;
                        maxY = y;
                        maxC = c;
                    }
                }
            }
        }
        System.out.println("The maximum powerlevel is " + max + " at (" + maxX + "," + maxY + ") and size  " + maxC);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());



    }
}

