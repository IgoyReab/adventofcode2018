package nl.yogibear;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_1 {

    static List<String> freqShiftsList = null;
    static Integer[] freqShifts;

    static long resultingFrequency(long n, int i) {               //recursive function to add all elements together
        if (i >= freqShifts.length) return n;                    // stop if last element has been added
        return freqShifts[i] + resultingFrequency(n, i + 1);  //recursive call to this function with the next element
    }

    static long firstRecuringFrequency() {

        Set<Long> foundFrequencies = new HashSet<Long>();         // set of Longs, if I add an element that already exists it returns false

        long sum = 0;
        boolean found = false;

        do {
            for (int i = 0; i < freqShifts.length; i++) {     // loop through the array
                sum = sum + freqShifts[i];
                found = !(foundFrequencies.add(sum));        // found becomes true if  an element that already exist is added, if that happens the recuring Frequency has been found
                if (found) break;
            }
        } while (!found);                                     // try agian if not found

        return sum;                                           // return the found value (the one that was double!
    }

    public static void main(String[] args) throws IOException {

        LocalTime start = LocalTime.now();

        freqShiftsList = Files.readLines(new File("src/main/resources/day1.txt"), Charset.forName("utf-8"));

        freqShifts = new Integer[freqShiftsList.size()];

        int count = 0;

        for (String s: freqShiftsList) {
            s =s.replace("+","");
            freqShifts[count] = Integer.parseInt(s);
            count ++;
        }

        System.out.println("Final frequency is " + resultingFrequency(0, 0));
        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());


        
        start = LocalTime.now();
        System.out.println("First recuring frequency is " + firstRecuringFrequency());

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
