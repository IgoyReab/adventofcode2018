package nl.yogibear;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class Map {
    int maxx = 0, maxy = 0, minx = 2000, miny = 2000;
    int[][] dist = new int[2000][2000];
    boolean[][] visited = new boolean[2000][2000];
    char[][] map = new char[2000][2000];
    String regex;  //puzzle input without first and last character go here
    int sum = 0;
    int maxDistance = 0;

    private void calcPath(int x, int y, int i, int d) {
        while (i < regex.length()) {
            if (regex.charAt(i) == 'E') {
                x++;
                map[x][y] = '|';
                x++;
                map[x][y] = '.';
                if (x > maxx) {
                    maxx = x;
                }
            } else if (regex.charAt(i) == 'W') {
                x--;
                map[x][y] = '|';
                x--;
                map[x][y] = '.';
                if (x < minx) {
                    minx = x;
                }
            } else if (regex.charAt(i) == 'N') {
                y--;
                map[x][y] = '-';
                y--;
                map[x][y] = '.';
                if (y < miny) {
                    miny = y;
                }
            } else if (regex.charAt(i) == 'S') {
                y++;
                map[x][y] = '-';
                y++;
                map[x][y] = '.';
                if (y > maxy) {
                    maxy = y;
                }
            } else if (regex.charAt(i) == '(') {
                int parenLevel = 0;
                boolean newCond = true;
                while (i < regex.length()) {
                    i++;
                    if (regex.charAt(i) == '(') {
                        parenLevel++;
                    } else if (regex.charAt(i) == ')') {
                        parenLevel--;
                        if (parenLevel < 0) {
                            calcPath(x, y, i + 1, d);
                            return;
                        }
                    } else if (regex.charAt(i) == '|') {
                        if (parenLevel == 0) {
                            newCond = true;
                        }
                    } else if (parenLevel == 0) {
                        if (newCond) {
                            calcPath(x, y, i, d);
                            newCond = false;
                        }
                    }
                }
            } else {
                return;
            }
            i++;
            d++;
            if (d >= 1000 && !visited[x][y]) {
                visited[x][y] = true;
                sum++;
            }
            if (dist[x][y] == 0 || dist[x][y] > d) {
                dist[x][y] = d;
                if (d > maxDistance) {
                    maxDistance = d;
                }
            }
        }
    }

    private void printMap() {
        for (int j = minx; j < maxx; j++) {
            System.out.print("#");
        }
        System.out.println("##");
        for (int i = miny; i < maxy; i++) {
            System.out.print("#");
            for (int j = minx; j < maxx; j++) {
                if (map[j][i] == 0) {
                    System.out.print("#");
                } else {
                    System.out.print(map[j][i]);
                }
            }
            System.out.print("#");
            System.out.println();
        }
        for (int j = minx; j < maxx; j++) {
            System.out.print("#");
        }
        System.out.println("##");
    }

    public void calculateMap(String i) {
        this.regex = i;
        this.regex = this.regex.replace("^", "");
        this.regex = this.regex.replace("$", "");
        int x = 1000, y = 1000;
        this.map[x][y] = 'X';
        calcPath(x, y, 0, 0);
        printMap();
        System.out.println("Part 1: " + this.maxDistance);
        System.out.println("Part 2: " + this.sum);
    }
}

public class Day_20 {


    public static void main(String[] args) throws IOException {

        LocalTime start = LocalTime.now();

        List<String> input = null;

        List<String> f = new ArrayList<>();

        input = Files.readLines(new File("src/main/resources/day20.txt"), Charset.forName("utf-8"));

//        System.out.println(input.get(0));

        Map map = new Map();
        map.calculateMap(input.get(0));

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }

}
