package nl.yogibear;

import com.google.common.base.CharMatcher;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Day_2 {
    static int countChars(String testString, char testChar) {

        return CharMatcher.is(testChar).countIn(testString);   // how many testChars are ther is testString!
    }


    static int checksumBoxIds() {
        char temp;
        int checkSum;

        LinkedList<Character> foundDoubles = new LinkedList<Character>();     // linkedList to save the found doubles, this time I need to be able to add duplicates
        LinkedList<Character> foundTriples = new LinkedList<Character>();     // linkedList to save the found triples, this time I need to be able to add duplicates

        List<String> boxIds = null;

        try {
            boxIds = Files.readLines(new File("src/main/resources/day2.txt"), Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int n = 0; n < boxIds.size(); n++) {
            boolean foundDouble = false;
            boolean foundTriple = false;
            for (int i = 0; i < boxIds.get(n).length(); i++)                    // loop through the array
            {
                temp = boxIds.get(n).charAt(i);                                // the character that needs to be tested
                switch (countChars(boxIds.get(n), temp)) {                        // how many times is this character in the string
                    case 3: {
                        if (!foundTriple) {
                            foundTriples.add(temp);                         // add to list of triplets
                            foundTriple = true;                             // remember that a triplet has been found, so I can stop the iteration later
                        }
                        break;                                              // no need to check for double, it is already a triplet
                    }
                    case 2: {
                        if (!foundDouble) {
                            foundDoubles.add(temp);                         // add to list of doubles
                            foundDouble = true;                             // remember that a double has been found, so I can stop the iteration later
                        }

                    }
                }
                if (foundDouble && foundTriple)
                    break;                     // if a double and a triplet has been found, then sdtop this iteration.
            }
        }

        return (foundDoubles.size() * foundTriples.size());                // compute the checksum
    }

    static String commonLettersBetweenBoxes() throws IOException {

        boolean foundIt = false;
        String resultString = "";
        char resultChar = ' ';
        String foundString = "";

        List<String> boxIds = null;

        boxIds = Files.readLines(new File("src/main/resources/day2.txt"), Charset.forName("utf-8"));

        for (int m = 0; m < boxIds.size(); m++) {                                              // loop through the array
            for (int n = 0; n < boxIds.size(); n++) {                                           // loop through the array a second time, now I have to elements of the array at the same time
                LinkedList<Character> foundDifference = new LinkedList<Character>();           // initialize a list to count different characters
                if (m == n)
                    continue;                                                        // Do not have to check the element against itself
                for (int i = 0; i < boxIds.get(m).length(); i++) {                                  // Loop through both the elements to determine which characters are the same
                    if (boxIds.get(m).charAt(i) != boxIds.get(n).charAt(i)) {                        // if they are not the same add the character to the list
                        resultChar = boxIds.get(m).charAt(i);                                      // save the Char that is not the same for later use
                        foundDifference.add(resultChar);
                    }
                }
                foundIt = (foundDifference.size() == 1);                                     // finaly if there was only one element in the list it is found, set foundIt so I can terminate
                if (foundIt)
                    break;                                                           // terminate the iteration now and I the higher loop.....
            }
            foundString = (boxIds.get(m).replace(String.valueOf(resultChar), ""));    // save the found value , subtract the different character in a variable
            if (foundIt) break;
        }
        return foundString;                                                                   // because here the index is out of scope , I needed a variable
    }

    public static void main(String[] args) throws IOException{
        LocalTime start = LocalTime.now();
        System.out.println("Checkum is " + checksumBoxIds());
        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        start = LocalTime.now();
        System.out.println("Common letters are " + commonLettersBetweenBoxes());
        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }

}
