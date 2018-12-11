package nl.yogibear;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Day_11 {
    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day11-tst.txt"), Charset.forName("utf-8"));

        String[] e = input.get(0).split(" ");
        List<Integer> inputList = new ArrayList<Integer>();

    }
}

