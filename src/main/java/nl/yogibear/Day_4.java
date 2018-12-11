package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
class guard {
    private Integer guardId;
    private Long dutyDate;
    private Integer[] dutyHour;


    public void setGuard(ArrayList<String> guardRecords) {
        int previousMinute = 0;
        int MinuteOfHour = 0;
        int sleepValue = 0;

        this.dutyHour = new Integer[59];


        for (String guardRecord : guardRecords) {  //                 [1518-11-01 00:00] Guard #10 begins shift

            if (guardRecord.contains("Guard")) {
                String temp = "";

                String[] el1 = guardRecord.split(" Guard ");    // el1[0] = "[1516-11-01 00:00]"  el1[1] = "#10 begins shift"


                temp = el1[1].replace("#", "");      // temp = "10 begins shift"
                String[] el2 = temp.split(" ");                // el2[0] = "10" el2[1] = "begins"el2[2] = "shift"
                this.guardId = Integer.parseInt(el2[0]);

                temp = el1[0].replace("[", "");
                temp = temp.replace("]", "");   // temp = 1516-11-01 00:00

                String[] el3 = temp.split(" ");            //el3[0] = 1516-11-01   el3[1] = 00:00
                String[] el4 = el3[0].split("-");          //el4[0] = 1516 el4[1] = 11 el4[2] = 01
                this.dutyDate = Long.valueOf(el3[0].replace("-", ""));

                for (int x = 0; x < 59; x++) this.dutyHour[x] = 0;
                continue;
            }

            if (guardRecord.contains("falls asleep")) sleepValue = 0;
            if (guardRecord.contains("wakes up")) sleepValue = 1;

            String temp = "";

            if (sleepValue == 1) {
                String[] el1 = guardRecord.split(" wakes up");    // el1[0] = "[1516-11-01 00:05]"
                temp = el1[0];
            } else {
                String[] el1 = guardRecord.split(" falls asleep");    // el1[0] = "[1516-11-01 00:05]"
                temp = el1[0];
            }

            String[] el2 = temp.split(":");                      // el2[0] = "1516-11-01 00:"  el2[1]= "05]"

            previousMinute = MinuteOfHour;
            MinuteOfHour = Integer.parseInt(el2[1].replace("]", ""));

            for (int i = previousMinute; i < MinuteOfHour; i++) dutyHour[i] = sleepValue;
        }
    }
}

@Data
class guardSleepRecord {

    private int Id;
    private int totalSleep;
    private int mostAsleepAt;
    private Integer[] sleepArray;



    public void setGuardSleepRecord(int id){
        this.Id = id;
        sleepArray = new Integer[59];
        for (int x=0; x<59; x++) this.sleepArray[x] = 0;
        totalSleep = 0;
        mostAsleepAt = 0;
    }

    public void addSleep(int index) {

        this.sleepArray[index]++;
    }



    public void addTotalSleep(int sleep) {
        this.totalSleep = this.totalSleep + sleep;
    }

    public void calculateMostAsleepAt () {
        int max = sleepArray[0];
        for (int x=1; x < sleepArray.length; x++) {
            if (sleepArray[x] > max) {
                max = sleepArray[x];
            }
        }
        this.mostAsleepAt = max;
    }
}

public class Day_4 {

    static void guardIdxMinute() throws IOException{

        List<String> guardRecords = null;

        guardRecords = Files.readLines(new File("src/main/resources/day4.txt"), Charset.forName("utf-8"));

        Collections.sort(guardRecords);

        ArrayList<guard> guardHourNotes = new ArrayList<>();


        ArrayList<String> guardInputRecords = new ArrayList<>();

        for (String g : guardRecords) {
            //System.out.println(g);
            if (g.contains("Guard")) {
                if ( guardInputRecords.size() > 0 ) {
                    guard thisGuard = new guard();
                    thisGuard.setGuard(guardInputRecords);
                    guardHourNotes.add(thisGuard);
                }
                guardInputRecords = new ArrayList<>();
                guardInputRecords.add(g);
            } else {
                guardInputRecords.add(g);
            }
        }

        guard thisGuard = new guard();                          // and the last need to be added
        thisGuard.setGuard(guardInputRecords);
        guardHourNotes.add(thisGuard);



        System.out.println("-- Date       Guard  Minute");
        System.out.println("                     000000000011111111112222222222333333333344444444445555555555");
        System.out.println("                     012345678901234567890123456789012345678901234567890123456789");

        ArrayList<guardSleepRecord> sleepList = new ArrayList<>();

        for (guard thisG : guardHourNotes) {
            boolean found = false;

            for (guardSleepRecord thisList : sleepList) {
                if (thisG.getGuardId() == thisList.getId()) {
                    found = true;
                    int countSleep = 0;
                    for (int x = 0; x < 59; x++) {
                        if (thisG.getDutyHour()[x] == 1) thisList.addSleep(x);
                        countSleep = countSleep + thisG.getDutyHour()[x];
                    }

                    thisList.addTotalSleep(countSleep);
                }
            }

            if (!found) {
                guardSleepRecord thisList = new guardSleepRecord();
                thisList.setGuardSleepRecord(thisG.getGuardId());
                int countSleep = 0;
                for (int x = 0; x < 59; x++) {
                    if (thisG.getDutyHour()[x] == 1) thisList.addSleep(x);
                    countSleep = countSleep + thisG.getDutyHour()[x];
                }

                thisList.addTotalSleep(countSleep);
                sleepList.add(thisList);
            }
        }

        int countSleep = 0;

        for (guard thisG : guardHourNotes) {
            String temp = "";
            for (int x=0; x < 59; x++) {
                countSleep = countSleep + thisG.getDutyHour()[x];
                temp = temp + thisG.getDutyHour()[x].toString();

            }
            System.out.println("  " +  thisG.getDutyDate() + "    " + thisG.getGuardId() + "     " + temp + " " + countSleep);
        }

        int maxAsleep = 0;
        int foundGuardId = 0;
        int maxAt = 0;
        int maxAtIndex = 0;


        for (guardSleepRecord thisList : sleepList) {
            thisList.calculateMostAsleepAt();
            if (thisList.getTotalSleep() > maxAsleep)  {
                maxAsleep = thisList.getTotalSleep();
                foundGuardId = thisList.getId();
                for (int x = 0; x < 59; x++) {
                    if (thisList.getSleepArray()[x] > maxAt) {
                        maxAt = thisList.getSleepArray()[x];
                        maxAtIndex = x;
                    }
                }

            }

            String temp = "";
            for (int x=0; x < 59; x++) {
                temp = temp + " " + thisList.getSleepArray()[x];

            }
//            System.out.println(" Guard :" +  thisList.getId() + " Total sleep:" + thisList.getTotalSleep() + " Most asleep at:" + thisList.getMostAsleepAt());
//            System.out.println(" " + temp);
        }


        int foundGuardId2 = 0;
        int maxAt2 = 0;
        int maxAtIndex2 = 0;

        for (guardSleepRecord thisList : sleepList) {
            for (int x = 0; x < 59 ; x++) {
                if (thisList.getSleepArray()[x] > maxAt2) {
                    maxAt2 =  thisList.getSleepArray()[x];
                    maxAtIndex2 = x;
                    foundGuardId2 = thisList.getId();
                }
            }
        }

        System.out.println("Part1 : The ID of the guard (" + foundGuardId +") multiplied by the minute I choose (" + maxAtIndex + ") is  " + (foundGuardId * maxAtIndex));
        System.out.println("Part2 : The ID of the guard (" + foundGuardId2 +") multiplied by the minute I choose (" + maxAtIndex2 + ") is  " + (foundGuardId2 * maxAtIndex2));


    }


    public static void main(String[] args) throws IOException{
        LocalTime start = LocalTime.now();

        guardIdxMinute();

        LocalTime finish = LocalTime.now();

        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    };

}
