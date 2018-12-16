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
        System.out.println("Before      : [" + before[0] + "] [" + before[1] + "] [" + before[2] + "] [" + before[3] + "]");
        System.out.println("Instructions: [" + instruction[0] + "] [" + instruction[1] + "] [" + instruction[2] + "] [" + instruction[3] + "]");
        System.out.println("After       : [" + after[0] + "] [" + after[1] + "] [" + after[2] + "] [" + after[3] + "]");
        System.out.println("\nProcessor   : [" + p.getRegisters()[0] + "] [" + p.getRegisters()[1] + "] [" + p.getRegisters()[2] + "] [" + p.getRegisters()[3] + "]");
        System.out.println();
    }

    public void doSubTest(String oper, Processor p) {

        p.setRegisters(before);

        switch (oper) {
            case "ADDR": {
                p.addr(instruction);
                break;
            }
            case "ADDI": {
                p.addi(instruction);
                break;
            }
            case "MULR":{
                p.mulr(instruction);
                break;
            }
            case "MULI":{
                p.muli(instruction);
                break;
            }
            case "BANR":{
                p.banr(instruction);
             break;
            }
            case "BANI":{
                p.bani(instruction);
                break;
            }
            case "BORR":{
                p.borr(instruction);
                break;
            }
            case "BORI":{
                p.bori(instruction);
                break;
            }
            case "SETR":{
                p.setr(instruction);
                break;
                    }
            case "SETI": {
                p.seti(instruction);
                break;
            }
            case "GTIR":{
                p.gtir(instruction);
                break;
            }
            case "GTRI":{
                p.gtri(instruction);
                break;
            }
            case "GTRR":{
                p.gtrr(instruction);
                break;
            }
            case "EQIR":{
                p.eqir(instruction);
                break;
            }
            case "EQRI":{
                p.eqri(instruction);
                break;
            }
            case "EQRR":{
                p.eqrr(instruction);
                break;
            }
        }

        if ((this.after[0] == p.getRegisters()[0]) &&
                (this.after[1] == p.getRegisters()[1]) &&
                (this.after[2] == p.getRegisters()[2]) &&
                (this.after[3] == p.getRegisters()[3])) {
            this.numMatches++;
        }
    }

    public int test(Processor p) {
        doSubTest("MULR", p);
        doSubTest("MULI", p);
        doSubTest("ADDR", p);
        doSubTest("ADDI", p);
        doSubTest("BANR", p);
        doSubTest("BANI", p);
        doSubTest("BORR", p);
        doSubTest("BORI", p);
        doSubTest("SETR", p);
        doSubTest("SETI", p);
        doSubTest("GTIR", p);
        doSubTest("GTRI", p);
        doSubTest("GTRR", p);
        doSubTest("EQIR", p);
        doSubTest("EQRI", p);
        doSubTest("EQRR", p);
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

        System.out.println("The number of samples that behave like three or more opcodes is " + result + ".");

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        start = LocalTime.now();

        for (int x = 0; x <= input.size() - 3; x = x + 3) {

            Testcase testcase = new Testcase(input.get(x), input.get(x+1), input.get(x + 2));
            if (testcase.test(processor) == 1) {
                result++;
            }
        }

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());



    }
}
