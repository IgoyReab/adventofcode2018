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
class Pot {
    public Pot(int idValue) {
        this.idValue = idValue;
        this.plant = false;
        this.nextPlant = plant;
    }
    private int idValue;

    boolean plant;
    boolean nextPlant;
}
@Data
class PlantRule {
    private String combo;
    private boolean becomesPlant;

    public PlantRule(String combo, boolean becomesPlant) {
        this.combo = combo;
        this.becomesPlant = becomesPlant;
    }
}

    public class Day_12 {
    public static void printRow(List<Pot> l, int lineCount) {
        String line = " " + lineCount + " ";
        for (Pot p : l) {
            if ( p.getIdValue() > -21 ) {
                if (p.isPlant()) {
                    line = line + "#";
                } else {
                    line = line + ".";
                }
            }
        }
        System.out.println(line);
    }

    public static int doIterations(List<Pot> potRow, List<PlantRule> ruleList, int iterations) {
        int result = 0;
        int lowCount = 0;

        for (int x = 0; x < iterations; x++)
        {
            List<Pot> newPots  = new ArrayList<>();
            List<Pot> newLowPots = new ArrayList<>();

            for (Pot p : potRow)
            {
                String compare = "";


                if ((potRow.indexOf(p) == 0) && (x==0)) {
                    compare = compare + "..";

                } else {
                    if ((potRow.indexOf(p) - 2) < 0) {
                        lowCount++;
                        compare = compare + ".";
                        Pot lowPot = new Pot(- 25 - lowCount);
                        //                     newLowPots.add(lowPot);
                    } else {
                        if (potRow.get(potRow.indexOf(p) - 2).isPlant()) {
                            compare = compare + "#";
                        } else {
                            compare = compare + ".";
                        }
                    }

                    if ((potRow.indexOf(p) - 1) < 0) {
                        compare = compare + ".";
                        lowCount++;
                        compare = compare + ".";
                        Pot lowPot = new Pot(- 25 - lowCount);
                        //                       newLowPots.add(lowPot);
                    } else {
                        if (potRow.get(potRow.indexOf(p) - 1).isPlant()) {
                            compare = compare + "#";
                        } else {
                            compare = compare + ".";
                        }
                    }
                }

                if (p.isPlant()) {
                    compare = compare + "#";
                } else {
                    compare = compare + ".";
                }

                if ((potRow.indexOf(p) + 1) >= potRow.size() ){
                    Pot pot = new Pot(potRow.size() + newPots.size() + potRow.get(0).getIdValue());
                    newPots.add(pot);
                    compare = compare + ""; } else
                {
                    if (potRow.get(potRow.indexOf(p) + 1).isPlant()) {
                        compare = compare + "#";
                    } else {
                        compare = compare + ".";
                    }
                }

                if ((potRow.indexOf(p) + 2) >= potRow.size() ){
                    compare = compare + "";
                    Pot pot = new Pot(potRow.size() + newPots.size() + potRow.get(0).getIdValue());
                    newPots.add(pot);
                } else {
                    if (potRow.get(potRow.indexOf(p) + 2).isPlant()) {
                        compare = compare + "#";
                    } else {
                        compare = compare + ".";
                    }
                }

                for (PlantRule pR : ruleList) {
                    if (compare.equals(pR.getCombo())) {
//                        if ((x % 5) == 0) {
//                            System.out.println(" pot :" + p.getIdValue() + " Compare : " + compare + " => " + pR.isBecomesPlant());
//                        }

                        p.setNextPlant(pR.isBecomesPlant());
                        break;
                    }
                }
            }

//            for (Pot p : potRow) {
//                if (p.getIdValue() > -3) System.out.println(" pot :" + p.getIdValue() + " index : " + potRow.indexOf(p) + " isPlant " + p.isPlant() + " isNextPlant " + p.isNextPlant());
//            }

            for (Pot p : potRow) {
                p.setPlant(p.isNextPlant());
                p.setNextPlant(p.isPlant());
            }

            for (Pot p : newPots) {
                potRow.add(p);
            }

            for (Pot p : newLowPots) {
                potRow.add(0, p);
            }
//            for (Pot p : potRow) {
//                if (p.isPlant()) {
//                    System.out.print(p.getIdValue() + " ");
//
//                }
//            }
//            System.out.println();

            //printRow(potRow, x + 1);
        }

        for (Pot p : potRow) {
            if (p.isPlant()) {
  //              System.out.print(p.getIdValue() + " ");
                result = result + p.getIdValue();
            }

        }
  //      System.out.println();

        return result;

    }

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();
//        String initialState = "#..#.#..##......###...###";

        String initialState =     ".##..#.#..##..##..##...#####.#.....#..#..##.###.#.####......#.......#..###.#.#.##.#.#.###...##.###.#";
// LLCRR => N

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day12.txt"), Charset.forName("utf-8"));

        List<PlantRule> ruleList = new ArrayList<>();

        for (String s : input) {
            String[] xy = s.split(" ");
            PlantRule plantRule = new PlantRule(xy[0], (xy[2].charAt(0) == '#'));
            ruleList.add(plantRule);
        }

//        for (PlantRule pR: ruleList) {
//            if (pR.isBecomesPlant()) {
//                System.out.println(pR.getCombo() + " => #");
//            } else {
//                System.out.println(pR.getCombo() + " => .");
//            }
//        }

        List<Pot> potRow = new ArrayList<Pot>();

        for (int i = -25; i < 0; i++) {
            Pot pot = new Pot(i);
            potRow.add(pot);
        }

        for (int i = 0; i < initialState.length() + 40; i++) {
            Pot pot = new Pot(i);
            if (i >= initialState.length() ) {
                pot.setPlant(false);
            } else {
                pot.setPlant(initialState.charAt(i) == '#');
            }
            pot.setNextPlant(pot.isPlant());
            potRow.add(pot);
        }

        // printRow(potRow, 0);

        int result = doIterations (potRow, ruleList, 20);

        System.out.println();

        System.out.println(" After 20 generations, the sum of the numbers of all pots which contain a plant is : " + result);

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        // Part 2

        start = LocalTime.now();

        List<Pot> potRow2 = new ArrayList<Pot>();

        for (int i = -25; i < 0; i++) {
            Pot pot = new Pot(i);
            potRow2.add(pot);
        }

        for (int i = 0; i < initialState.length() + 40; i++) {
            Pot pot = new Pot(i);
            if (i >= initialState.length() ) {
                pot.setPlant(false);
            } else {
                pot.setPlant(initialState.charAt(i) == '#');
            }
            pot.setNextPlant(pot.isPlant());
            potRow2.add(pot);
        }

        result = doIterations(potRow2, ruleList, 200);

        long generations = 50000000000L;
        long addFactor = generations - 200;

        long lResult = 0;
        int potCount = 0;
        for (Pot p : potRow2) {
            if (p.isPlant()) {
   //             System.out.print(p.getIdValue() + " ");
                lResult += p.getIdValue();
                potCount++;
            }


        }
        lResult += (generations - 200)  * potCount;


        System.out.println("After fifty billion (50000000000) generations, the sum of the numbers of all pots which contain a plant is :" + lResult);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());


    }
}

