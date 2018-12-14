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

    public void reversePoint() {
        this.x = this.x - this.vx;
        this.y = this.y - this.vy;
    }
}

@Data
class Projection {
    Collection<Point> points;
    long surfaceArea;
    int width;
    int height;

    public Projection(List<String> input) {

        int maxWidth = 0;
        int maxHeight = 0;

        points = new ArrayList<>();

        for (String s: input) {
            String[] e = s.split("[<>,]");
            Point point  = new Point(Integer.parseInt(e[1].trim()), Integer.parseInt(e[2].trim()), Integer.parseInt(e[4].trim()), Integer.parseInt(e[5].trim()));
            if (Math.abs(point.getX()) > maxWidth) maxWidth = Math.abs(point.getX());
            if (Math.abs(point.getY()) > maxHeight) maxHeight = Math.abs(point.getY());
            this.points.add(point);
        }

        this.width = 2 * maxWidth;
        this.height = 2 * maxHeight;

        long a = this.width;
        long b = this.height;

        this.surfaceArea = a * b;
    }


    void reverseSky () {

        int maxWidth = 0;
        int maxHeight = 0;

        for( Point p : this.points) {
            p.reversePoint();
            if (Math.abs(p.getX())  > maxWidth) maxWidth = Math.abs(p.getX());
            if (Math.abs(p.getY())  > maxHeight) maxHeight = Math.abs(p.getY());

        }

        this.width = maxWidth * 2;
        this.height = maxHeight * 2;

        long a = this.width;
        long b = this.height;

        this.surfaceArea = a * b;
    }

    void advanceSky () {

        int maxWidth = 0;
        int maxHeight = 0;

        for( Point p : this.points) {
            p.advancePoint();
            if (Math.abs(p.getX())  > maxWidth) maxWidth = Math.abs(p.getX());
            if (Math.abs(p.getY())  > maxHeight) maxHeight = Math.abs(p.getY());

        }

        this.width = maxWidth * 2;
        this.height =  maxHeight * 2;

        long a = this.width;
        long b = this.height;

        this.surfaceArea = a * b;

        System.out.println("Width : " + this.width + "  Height : " + this.height);
    }

    void printMessage() {

        ArrayList<String> lines = new ArrayList<>();


        for (int i = 0; i <= this.height; i++) {
                String l = "";
                for (int j = 0; j <= this.width; j++) {
                    //System.out.println(j);
                    l = l + ".";
                }
                lines.add(l);
        }


        String line;


        for (Point p : points) {

            int cY = ((lines.size() / 2) + p.getY());

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
        }

        for (String s : lines) {
            System.out.println(s);
        }

        System.out.println("\n\n");

    }
}

public class Day_10 {


    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;
        Collection<Point> points = new ArrayList<>();

        input = Files.readLines(new File("src/main/resources/day10.txt"), Charset.forName("utf-8"));

        Projection sky = new Projection(input);

        long surface = 0;
        long count = 0;

//        sky.printMessage();

        do {
            surface = sky.getSurfaceArea();
            sky.advanceSky();
            count++;
//            sky.printMessage();
//            System.out.println("Surface : " + surface + " < " + sky.getSurfaceArea());
        }   while (surface > sky.getSurfaceArea()) ;


//        for (int x=0; x < 10; x++) {
//            surface = sky.getSurfaceArea();
//            sky.advanceSky();
//            System.out.println("Surface : " + surface + " < " + sky.getSurfaceArea());
//        }

        sky.reverseSky();
        count--;

        sky.printMessage();
        System.out.println("It took : " + count + " seconds ");

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
