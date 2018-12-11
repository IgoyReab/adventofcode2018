package nl.yogibear;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;

import java.util.List;

import static java.lang.Character.getNumericValue;

public class Day_5 {

    static int resultingPolymer(String polymer) {

        boolean Found = true;
        while (Found) {
            Found = false;
            for (int x = 0; x < (polymer.length() - 1); x++) {
                if ( ( getNumericValue(polymer.charAt(x)) == getNumericValue(polymer.charAt(x + 1))) && (polymer.charAt(x) != polymer.charAt(x+1) )) {
                    polymer = polymer.replace(String.valueOf(polymer.charAt(x) + String.valueOf(polymer.charAt(x+1))),  "");
                    Found = true;
                    break;
                }
            }
        }
        return polymer.length();
    }

    public static void main(String[] args) throws IOException{
        // Part one
        LocalTime start = LocalTime.now();

        List<String> polymerList = null;
        polymerList = Files.readLines(new File("src/main/resources/day5.txt"), Charset.forName("utf-8"));

        System.out.println("Size before : " + polymerList.get(0).length());
        System.out.println("Day5 part 1 : Size after     : " + resultingPolymer(polymerList.get(0)));

        LocalTime finish = LocalTime.now();

        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        // Pert two

        start = LocalTime.now();
        int minLength = polymerList.get(0).length();
        String temp = "";
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            int tempLength = 0;
            temp = polymerList.get(0).replace(String.valueOf(alphabet), "");
            temp = temp.replace(String.valueOf(Character.toLowerCase(alphabet)), "");
            tempLength = resultingPolymer(temp);
            if ( tempLength < minLength ) minLength = tempLength;
        }
        System.out.println("Day5 part 2 : Minimum size     : " + minLength);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
