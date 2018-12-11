package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
class Point {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private Point nextPoint;
    private Point previousPoint;

    public Point(int x, int y, int vx, int vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }


}

public class Day_10 {

    static void printMessage(Point p) {

        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i <= Math.abs(thgieh) + Math.abs(height) + 2; i++) {
            String l = "";
            for (int j = 0; j <= Math.abs(width) + Math.abs(htdiw) + 2; j++) {
                l = l + ".";
            }
            lines.add(l);
        }

        String line;

        do
        {
            if (p != null) {
                String newLine = "";
                int cY = (lines.size() / 2) + p.getY();
                line = lines.get(cY);

                for (int x = 1; x < line.length(); x++) {
                    int cX = (line.length() / 2) + p.getX();
                    if (x == cX) {
                        newLine = newLine + "#";
                    } else {
                        newLine = newLine + line.charAt(x);
                    }
                }
                lines.remove(cY);
                lines.add(cY, newLine);
                p = p.getNextPoint();
            }

        } while ( p != null);

        for (String s: lines) System.out.println(s);

    }

    static void advancePoint(Point p) {
            p.setX( p.getX() + p.getVx());
            p.setY( p.getY() + p.getVy());
    }

    static int width = 0;
    static int height = 0;
    static int htdiw = 0;
    static int thgieh = 0;

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day10-tst.txt"), Charset.forName("utf-8"));
        Point point = null;
        Point previous = null;
        Point startPoint = null;




        for (String s: input) {
            String[] e = s.split("[<>,]");
            point  = new Point(Integer.parseInt(e[1].trim()), Integer.parseInt(e[2].trim()), Integer.parseInt(e[4].trim()), Integer.parseInt(e[5].trim()));
            if (startPoint == null) startPoint = point;
            point.setPreviousPoint(previous);
            if (previous != null) previous.setNextPoint(point);
            previous = point;
            if (Integer.parseInt(e[1].trim()) > width) width = Integer.parseInt(e[1].trim());
            if (Integer.parseInt(e[2].trim()) > height) height = Integer.parseInt(e[1].trim());
            if (Integer.parseInt(e[1].trim()) < htdiw) htdiw = Integer.parseInt(e[1].trim());
            if (Integer.parseInt(e[2].trim()) < thgieh) thgieh = Integer.parseInt(e[1].trim());
        }


        point = startPoint;

        width = 10;
        height = 25;
        htdiw = 10;
        thgieh = 25;

        printMessage(point);
        System.out.println("\n\n");





        for (int x=0; x<10; x++) {
            point = startPoint;
            do {
                advancePoint(point);
                point = point.getNextPoint();

            } while (point != null);
            point  = startPoint;
            printMessage(point);
            System.out.println("\n\n");
        }
    }
}
