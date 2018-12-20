package nl.yogibear;

import br.com.six2six.bfgex.RegexGen;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day_20 {
    public static void main(String[] args) throws IOException {

        LocalTime start = LocalTime.now();

        List<String> input = null;

        List<String> f = new ArrayList<>();

        input = Files.readLines(new File("src/main/resources/day20-tst.txt"), Charset.forName("utf-8"));

//        System.out.println(input.get(0));
        String tmp = input.get(0);
        tmp = tmp.replace("^", "");
        tmp = tmp.replace("$", "");
        int count = 0;
        while ((tmp.contains("(")) && (tmp.contains(")"))) {

            tmp = tmp.replaceFirst("\\(", "&");
            StringBuilder tmp2 = new StringBuilder(tmp);
            tmp2.reverse();
            tmp = tmp2.toString();
            tmp = tmp.replaceFirst("\\)", "&");
            tmp2 = new StringBuilder(tmp);
            tmp2.reverse();
            tmp = tmp2.toString();
            String[] g = tmp.split("&");
            System.out.println(tmp + " wordt : " + g[0] + " --- en --- " + g[1] + "\n");
            if (g.length >= 3) {
                f.add(g[0]);
                f.add(g[1]);
                f.add(g[2]);
            } else {
                f.add(g[0]);
                tmp = g[1];
            }
        }

        String result = "";

        for (String s: f) {
            System.out.println("String = " + s);
            if (s.contains("|")) {
                String[] h = s.split("\\|");
                String largest = "";
                for (String i: h) {
                    if (largest.length() < i.length()) {
                        largest = i;
                        System.out.println("Largest : " + largest);
                    }
                }
                result = result + largest;
            } else {
                result = result + s;
            }
            System.out.println("Result : " + result);
        }

        Pattern pattern  = Pattern.compile(input.get(0));
        


        System.out.println("The answer " + result + " is " + result.length());

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }

}
