package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
class claim {
    private Integer claimId;
    private int offsetLeft;
    private int offsetTop;
    private int width;
    private int height;

    public void setClaim(String claimDescription) {

        String temp = "";

        String[] elements1 = claimDescription.split("@");    // elements1[0] = "#1 "  elements1[1] = " 1,3: 4x4"
        temp = elements1[0].replace("#", "");
        temp = temp.replace(" ", "");
        claimId = Integer.parseInt(temp);


        String[] elements2 = elements1[1].split(",");  // elements2[0] = " 1"  elements2[1] = "3: 4x4"
        temp = elements2[0].replace(" ", "");
        offsetLeft = Integer.parseInt(temp);

        String[] elements3 = elements2[1].split(":"); // elements3[0] = "3" elements3[1] = " 4x4"
        temp = elements3[0].replace(" ", "");
        ;
        offsetTop = Integer.parseInt(temp);

        String[] elements4 = elements3[1].split("x");  //elements4[0] = "4" elements4[1] = "4";
        temp = elements4[0].replace(" ", "");
        height = Integer.parseInt(temp);
        temp = elements4[1].replace(" ", "");
        ;
        width = Integer.parseInt(temp);

    }
}

public class Day_3 {
    static int howManySquareInchesAreOverlapping() throws IOException{
        int arraySize = 1010;
        //    int arraySize = 10;
        Set<Integer>[][] fabric = new HashSet[arraySize][arraySize];
        List<String> claims = null;

        claims = Files.readLines(new File("src/main/resources/day3.txt"), Charset.forName("utf-8"));
        for (int m = 0; m < arraySize; m++) {
            for (int n = 0; n < arraySize; n++) {
                fabric[m][n] = new HashSet<Integer>();
            }
        }

        Set<Integer> claimIds = new HashSet<>();
        for (int i=0; i < claims.size(); i++){
            claim thisClaim = new claim();
            thisClaim.setClaim(claims.get(i));
            claimIds.add(thisClaim.getClaimId());
            for (int m = thisClaim.getOffsetLeft(); m < ( thisClaim.getOffsetLeft() + thisClaim.getHeight()); m++) {
                for (int n = thisClaim.getOffsetTop(); n < ( thisClaim.getOffsetTop() + thisClaim.getWidth()); n++) {
                    fabric[m][n].add(thisClaim.getClaimId());
                }
            }
        }

        int count = 0;

        for (int m = 0; m < arraySize; m++) {
            for (int n = 0; n < arraySize; n++) {
                if (fabric[m][n].size() > 1) {
                    for (Integer p :fabric[m][n]) {
                        claimIds.remove(p);
                    }
                    count++;
                }
            }
        }

        for (Integer p: claimIds) {
            System.out.println("The id of the only claim that doesn't overlap : " + p);
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        LocalTime start;
        LocalTime finish;
        start = LocalTime.now();
        System.out.println("Number of overlapping square inches  " + howManySquareInchesAreOverlapping());
        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
