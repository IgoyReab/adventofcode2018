package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
class Coordinate23 {
    private long x;
    private long y;
    private long z;
    private long inRange;
    private long distanceToStart;

    public Coordinate23(long x, long y, long z, long inRange, long distanceToStart) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.inRange = inRange;
        this.distanceToStart = distanceToStart;
    }
}

@Data
class Nanobot {
    private long x;
    private long y;
    private long z;

    private long radius;

    private long distanceAway;

    private long howManyAreInRange;


    public Nanobot(String input) {
        String[] el = input.split(", ");  //el[0] = pos=<0,0,0>   el[1]=r=4
        String[] em = el[1].split("=");   // em[0]=r=   em[1]=4
        this.radius = Long.parseLong((em[1]));

        String[] en = el[0].split("=<");   // en[0] = pos    en[1] = 0,0,0>
        en[1] = en[1].replace(">", "");
        String[] ek = en[1].split(",");

        this.x = Long.parseLong(ek[0]);
        this.y = Long.parseLong(ek[1]);
        this.z = Long.parseLong(ek[2]);


    }

    public Nanobot(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public long distance(long a, long b, long c) {
        return (Math.abs(this.x - a) + Math.abs(this.y - b) + Math.abs(this.z - c));
    }

    public void setDistanceAway(long a, long b, long c) {
        this.distanceAway = this.distance(a, b, c);
    }
}

public class Day_23 {

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();
        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day23.txt"), Charset.forName("utf-8"));

        ArrayList<Nanobot> bots = new ArrayList<>();


        long maxRadius = 0;
        long maxX = 0;
        long maxY = 0;
        long maxZ = 0;
        long countInRange = 0;

        long smallestX = 0;
        long largestX = 0;
        long smallestY = 0;
        long largestY = 0;
        long smallestZ = 0;
        long largestZ = 0;

        for (String s : input) {
            Nanobot nanobot = new Nanobot(s);
            bots.add(nanobot);
            if (nanobot.getRadius() > maxRadius) {
                maxRadius = nanobot.getRadius();
                maxX = nanobot.getX();
                maxY = nanobot.getY();
                maxZ = nanobot.getZ();
            }

            if (nanobot.getX() < smallestX) smallestX = nanobot.getX();
            if (nanobot.getY() < smallestX) smallestY = nanobot.getY();
            if (nanobot.getZ() < smallestZ) smallestZ = nanobot.getZ();
            if (nanobot.getX() > largestX) largestX = nanobot.getX();
            if (nanobot.getY() > largestY) largestY = nanobot.getY();
            if (nanobot.getZ() > largestZ) largestZ = nanobot.getZ();
        }



        for (Nanobot n : bots) {
            n.setDistanceAway(maxX, maxY, maxZ);
            if (n.getDistanceAway() <= maxRadius) {
                countInRange++;
  //              System.out.println("The nanobot at " + n.getX() + "," + n.getY() + "," + n.getZ() + " is distance " + n.getDistanceAway() + " away, and so it is in range.");
            } else {
  //              System.out.println("The nanobot at " + n.getX() + "," + n.getY() + "," + n.getZ() + " is distance " + n.getDistanceAway() + " away, and so it is not in range.");
            }
        }



        System.out.println("There are " + countInRange + " nanobots in range of its signals?");

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        //pqart two

        start = LocalTime.now();
        long mostInRange = 0;
        ArrayList<Coordinate23> coordinates = new ArrayList<>();
        ArrayList<Coordinate23> foundCoordinates = new ArrayList<>();

        for (Nanobot m : bots) {
            countInRange = 0;
            for (Nanobot n : bots) {
                if (n.getRadius() >= ((Math.abs(m.getX() - n.getX()) + (Math.abs(m.getY() - n.getY()) + (Math.abs(m.getZ() - n.getZ()))))))
                    countInRange++;
            }
            if (countInRange > mostInRange) {
                mostInRange = countInRange;
                Coordinate23 c = new Coordinate23(m.getX(),m.getY(),m.getZ(),countInRange, (Math.abs(m.getX()) + Math.abs(m.getY()) + Math.abs(m.getZ())));
                if (countInRange > 0) coordinates.add(c);
            }
        }

        long MaxInRange = 0;
        for (Coordinate23 c: coordinates) {
            if (c.getInRange() > MaxInRange) {
                MaxInRange = c.getInRange();
                maxX = c.getX();
                maxY = c.getY();
                maxZ = c.getZ();
            }
        }

        mostInRange = 0;

        for (long x = maxX - 1000; x < maxX + 1000; x++ ){
            for (long y = maxY - 1000; y < maxY + 1000; y++ ) {
                for (long z = maxZ - 1000; z < maxZ  + 1000; z++) {
//                    System.out.println(x + ";"+ y + ";" + z);
                    countInRange = 0;
                    for (Nanobot n : bots) {
                        if (n.getRadius() >= ((Math.abs(x - n.getX()) + (Math.abs(y - n.getY()) + (Math.abs(z - n.getZ()))))))
                            countInRange++;
                    }
                    if (countInRange > mostInRange) {
                        mostInRange = countInRange;
                        Coordinate23 c = new Coordinate23(x,y,z,countInRange, (Math.abs(x) + Math.abs(y) + Math.abs(z)));
                        if (countInRange > 0) foundCoordinates.add(c);
                    }
                }
            }
        }

        long answer =  Long.MAX_VALUE;

        for (Coordinate23 c: foundCoordinates) {
            if (c.getInRange() == mostInRange) {
                if ((Math.abs(c.getX()) + Math.abs(c.getY()) + Math.abs(c.getZ())) < answer) {
                    answer = Math.abs(c.getX()) + Math.abs(c.getY()) + Math.abs(c.getZ());
                }
            }
        }

        System.out.println("The shortest manhattan distance between any of those points and 0,0,0 is " + answer);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
