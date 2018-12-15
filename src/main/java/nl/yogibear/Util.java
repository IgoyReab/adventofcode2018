package nl.yogibear;

public class Util {
    public static int distance(int x, int y, int a, int b) {
        return (Math.abs(x - a) + Math.abs(y - b));
    }

    public static void log(String line, boolean nl) {
        if (nl) {
            System.out.println(line);
        } else {
            System.out.print(line);
        }
    }
}
