package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Data
class Processor {
    int[] registers;

    public Processor() {
        this.registers = new int[4];
        for(int i : this.registers)  i = 0 ;
    }

    public void setRegisters(int[] reg) {
        for (int x =0; x < reg.length; x++ ) {
            this.registers[x] = reg[x];
        }
    }

    public void addr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];


        this.registers[c] = this.registers[a] + this.registers[b];
    }

    public void addi(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] + b;
    }

    public void mulr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] * this.registers[b];
    }

    public void muli(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] * b;
    }

    public void banr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] & this.registers[b];
    }

    public void bani(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] & b;
    }

    public void borr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] | this.registers[b];
    }

    public void bori(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        this.registers[c] = this.registers[a] | b;
    }

    public void setr(int[] instr){
        int a = instr[1];
        int c = instr[3];
        this.registers[c] = this.registers[a];
    }

    public void seti(int[] instr){
        int a = instr[1];
        int c = instr[3];
        this.registers[c] = a;
    }

    public void gtir(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        if (a > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void gtri(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        if (this.registers[a] > b) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void gtrr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
        if (this.registers[a] > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void eqir(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];

//        System.out.println(a + " == " + this.registers[b]);
        if (a == this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void eqri(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
//        System.out.println(this.registers[a] + " == " + b);
        if (this.registers[a] == b){
            this.registers[c] = 1;
        }  else {
            this.registers[c] = 0;
        }
    }

    public void eqrr(int[] instr){
        int a = instr[1];
        int b = instr[2];
        int c = instr[3];
//        System.out.println(this.registers[a] + " == " + this.registers[b]);
        if (this.registers[a] == this.registers[b]) {
            this.registers[c] = 1;
        } else  {
            this.registers[c] = 0;
        }
    }

}

@Data
class Testcase {
   int[] before;
   int[] instruction;
   int[] after;
   int numMatches;

   public Testcase(String sBefore, String sInstruction, String sAfter) {
        this.numMatches = 0;
        this.before = new int[4];
        this.instruction = new int[4];
        this.after = new int[4];

        String[] e = sBefore.split("\\[");    // e[0] = "Before: "  e[1] = "3, 2, 1, 1]"
        e[1] = e[1].replace("]", "");   //e[1] = "3, 2, 1, 1"
        e[1] = e[1].replace(" ", "");
        String[] f = e[1].split(",");
        this.before[0] = Integer.parseInt(f[0]);
        this.before[1] = Integer.parseInt(f[1]);
        this.before[2] = Integer.parseInt(f[2]);
        this.before[3] = Integer.parseInt(f[3]);

        f = sInstruction.split(" ");
        this.instruction[0] = Integer.parseInt(f[0]);
        this.instruction[1] = Integer.parseInt(f[1]);
        this.instruction[2] = Integer.parseInt(f[2]);
        this.instruction[3] = Integer.parseInt(f[3]);

        e = sAfter.split("\\[");    // e[0] = "After: "  e[1] = "3, 2, 1, 1]"
        e[1] = e[1].replace("]", "");   //e[1] = "3, 2, 1, 1"
        e[1] = e[1].replace(" ", "");
        f = e[1].split(",");
        this.after[0] = Integer.parseInt(f[0]);
        this.after[1] = Integer.parseInt(f[1]);
        this.after[2] = Integer.parseInt(f[2]);
        this.after[3] = Integer.parseInt(f[3]);
    }

    public void printTestcase(String s, Processor p) {
        System.out.println(s);
        System.out.println("Before      : [" + before[0] + "] [" + before[1] + "] [" +  before[2] + "] ["  + before[3] + "]");
        System.out.println("Instructions: [" + instruction[0] + "] [" + instruction[1] + "] [" +  instruction[2] + "] ["  + instruction[3] + "]");
        System.out.println("After       : [" + after[0] + "] [" + after[1] + "] [" +  after[2] + "] ["  + after[3] + "]");
        System.out.println("\nProcessor   : [" + p.getRegisters()[0] + "] [" + p.getRegisters()[1] + "] [" +  p.getRegisters()[2] + "] ["  + p.getRegisters()[3] + "]");
        System.out.println();
    }

    public int test(Processor p) {
//        addr
        p.setRegisters(before);
        p.addr(instruction);
//        printTestcase("\n--- ADDR ---", p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
            printTestcase("\n--- ADDR ---", p);
        }
//        addi
        p.setRegisters(before);
        p.addi(instruction);
//        printTestcase("\n--- ADDI ---", p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
            printTestcase("\n--- ADDI ---", p);
        }
//        mulr
        p.setRegisters(before);
        p.mulr(instruction);
//        printTestcase("\n--- MULR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
            printTestcase("\n--- MULR ---",p);
        }
//        muli
        p.setRegisters(before);
        p.muli(instruction);
//        printTestcase("\n--- MULI ---", p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
            printTestcase("\n--- MULI ---", p);
        }
//        banr
        p.setRegisters(before);
        p.banr(instruction);
//        printTestcase("\n--- BANR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- BANR ---",p);
            this.numMatches++;
        }
//        bani
        p.setRegisters(before);
        p.bani(instruction);
//        printTestcase("\n--- BANI ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- BANI ---",p);
            this.numMatches++;
        }
//        borr
        p.setRegisters(before);
        p.borr(instruction);
//        printTestcase("\n--- BORR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
            printTestcase("\n--- BORR ---",p);
        }
//        bori
        p.setRegisters(before);
        p.bori(instruction);
//        printTestcase("\n--- BORI ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- BORI ---",p);
            this.numMatches++;
        }
//        setr
        p.setRegisters(before);
        p.setr(instruction);
//        printTestcase("\n--- SETR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- SETR ---",p);
            this.numMatches++;
        }
//        seti
        p.setRegisters(before);
        p.seti(instruction);
//        printTestcase("\n--- SETI ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- SETI ---",p);
            this.numMatches++;
        }
//        gtir
        p.setRegisters(before);
        p.gtir(instruction);
//        printTestcase("\n--- GTIR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- GTIR ---",p);
            this.numMatches++;
        }
//        gtri
        p.setRegisters(before);
        p.gtri(instruction);
//        printTestcase("\n--- GTRI ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- GTRI ---",p);
            this.numMatches++;
        }
//        gtrr
        p.setRegisters(before);
        p.gtrr(instruction);
//        printTestcase("\n--- GTRR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- GTRR ---",p);
            this.numMatches++;
        }
//        eqir
        p.setRegisters(before);
        p.eqir(instruction);
//        printTestcase("\n--- EQIR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- EQIR ---",p);
            this.numMatches++;
        }
//        eqri
        p.setRegisters(before);
        p.eqri(instruction);
//        printTestcase("\n--- EQRI ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- EQRI ---",p);
            this.numMatches++;
        }
//        eqrr
        p.setRegisters(before);
        p.eqrr(instruction);
//        printTestcase("\n--- EQRR ---",p);

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            printTestcase("\n--- EQRR ---",p);
            this.numMatches++;
        }

        return this.numMatches;
    }
}

public class Day_16 {


    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;
        List<String> testProgram = null;

        input = Files.readLines(new File("src/main/resources/day16.txt"), Charset.forName("utf-8"));
        testProgram = Files.readLines(new File("src/main/resources/day16-part2.txt"), Charset.forName("utf-8"));

        Processor processor = new Processor();

        int result = 0;

        for (int x = 0; x <= input.size() - 3; x = x + 3) {

            Testcase testcase = new Testcase(input.get(x), input.get(x+1), input.get(x + 2));
            if (testcase.test(processor) >= 3) result++;

//            System.out.println("Next ----");
        }

        System.out.println("The number of samples that behave like three or more opcodes are " + result + ".");



        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        start = LocalTime.now();

        for (int x = 0; x <= input.size() - 3; x = x + 3) {

            Testcase testcase = new Testcase(input.get(x), input.get(x+1), input.get(x + 2));
            if (testcase.test(processor) == 1) {
                result++;

                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            }
        }

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());



    }
}
