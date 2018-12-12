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
class Pot {
    public Pot(int idValue) {
        this.idValue = idValue;
        this.plant =false;
    }
    private int idValue;

    boolean plant;
}

public class Day_12 {
    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();
       String initialState = "#..#.#..##......###...###";
//        String initialState = ".##..#.#..##..##..##...#####.#.....#..#..##.###.#.####......#.......#..###.#.#.##.#.#.###...##.###.#"

// LLCRR => N

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day12-tst.txt"), Charset.forName("utf-8"));

        String[] e = input.get(0).split(" ");
        List<Integer> inputList = new ArrayList<Integer>();

        List<Pot> potRow = new ArrayList<Pot>;


        for (int i = -3; i < 0; i++) {
            Pot pot = new Pot(i);
            potRow.add(pot);
        }

        for (int i = 0; i < initialState.length(); i++) {
            Pot pot = new Pot(i);
            pot.setPlant(initialState.charAt(i) == '#');
            potRow.add(pot);
        }

        Pot workPot;

        for (Pot p: potRow) {
                String compare = "";

                if (potRow.indexOf(p) == 0) {
                   compare = compare + "..";
                   for (int i = -3; i < 0; i++) {
                       Pot pot = new Pot(i);
                       potRow.add(0, pot);
                   }
                } else {
                    if (potRow.get(potRow.indexOf(p) - 2).isPlant()) {
                        compare = compare + "#";
                    } else {
                        compare = compare + ".";
                    }

                    if (potRow.get(potRow.indexOf(p) - 1).isPlant()) {
                        compare = compare + "#";
                    } else {
                        compare = compare + ".";
                    }
                }

                if (workPot.isPlant()) {
                    compare = compare + "#";
                } else {
                    compare = compare + ".";
                }

                if (potRow.get(potRow.indexOf(p) + 1).isPlant()) {
                    compare = compare + "#";
                } else {
                    compare = compare + ".";
                }

                if (potRow.get(potRow.indexOf(p) + 2).isPlant()) {
                    compare = compare + "#";
                } else {
                    compare = compare + ".";
                }



            }
        }











    }


}
