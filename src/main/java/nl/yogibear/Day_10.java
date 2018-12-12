package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
class Point {
    private int x;
    private int y;
    private int vx;
    private int vy;

    public Point(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void advancePoint() {
        this.x = this.x + this.vx;
        this.y = this.y + this.vy;
    }
}



public class Day_10 {
    static int prevHeight = 100000;
    static int prevWidth = 1000000;

    static int getMaxHeight(Collection<Point> l) {
        int result = 0;
        for( Point p : l) if (result < p.getY()) result = p.getY();
        return Math.abs(result);
    }

    static int getMaxWidth(Collection<Point> l) {
        int result = 0;
        for( Point p : l) if (result < p.getX()) result = p.getX();
        return Math.abs(result);
    }

    static int getMinHeight(Collection<Point> l) {
        int result = 0;
        for( Point p : l) if (result > p.getY()) result = p.getY();
        return Math.abs(result);
    }

    static int getMinWidth(Collection<Point> l) {
        int result = 0;
        for( Point p : l) if (result > p.getX()) result = p.getX();
        return Math.abs(result);
    }

    static int getHeight(Collection<Point> l) {
        int max = getMaxHeight(l);
        int min = getMinHeight(l);
        if (max >= min) {
            return(max * 2);
        } else {
            return (min * 2);
        }
    }

    static int getWidth(Collection<Point> l) {
        int max = getMaxWidth(l);
        int min = getMinWidth(l);
        if (max >= min) {
            return(max * 2);
        } else {
            return (min * 2);
        }
    }


    static void printMessage(Collection<Point> list) {

        ArrayList<String> lines = new ArrayList<>();
        int h = getHeight(list);
        int w = getWidth(list);

        if (!((h < prevHeight) && (w < prevWidth))) {

            System.out.println("height " + h + " Width " + w);

            for (int i = 0; i <= h; i++) {
                String l = "";
                for (int j = 0; j <= w; j++) {
                    //System.out.println(j);
                    l = l + ".";
                }
                lines.add(l);
            }

            String line;


            for (Point p : list) {
                LocalTime start = LocalTime.now();
                int cY = (lines.size() / 2) + p.getY();
                line = lines.get(cY);
                String newLine = "";

                for (int x = 0; x < line.length(); x++) {
                    int cX = (line.length() / 2) + p.getX();
                    if (x == cX) {
                        newLine = newLine + "#";
                    } else {
                        newLine = newLine + line.charAt(x);
                    }
                }
                lines.remove(cY);
                lines.add(cY, newLine);
                LocalTime finish = LocalTime.now();
                System.out.println("Duration one line (ms): " + Duration.between(start, finish).toMillis());
            }

            for (String s : lines) {
                System.out.println(s);
            }
        }
        prevHeight = h;
        prevWidth = w;
    }

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;
        Collection<Point> points = new ArrayList<>();

        input = Files.readLines(new File("src/main/resources/day10.txt"), Charset.forName("utf-8"));

        for (String s: input) {
            String[] e = s.split("[<>,]");
            Point point  = new Point(Integer.parseInt(e[1].trim()), Integer.parseInt(e[2].trim()), Integer.parseInt(e[4].trim()), Integer.parseInt(e[5].trim()));

            points.add(point);
        }



        int loop = 0;
        do {
            loop ++;
            for (Point p : points) p.advancePoint();
            printMessage(points);
            System.out.println(String.format("\n\n"));
        } while (true);
    }
}
