package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
class Processor {
    int[] registers;

    Set<Integer> ADDR;
    Set<Integer> ADDI;
    Set<Integer> MULR;
    Set<Integer> MULI;
    Set<Integer> BANR;
    Set<Integer> BANI;
    Set<Integer> BORR;
    Set<Integer> BORI;
    Set<Integer> SETR;
    Set<Integer> SETI;
    Set<Integer> GTIR;
    Set<Integer> GTRI;
    Set<Integer> GTRR;
    Set<Integer> EQIR;
    Set<Integer> EQRI;
    Set<Integer> EQRR;

    public Processor() {
        this.registers = new int[4];
        for(int i : this.registers)  i = 0 ;
        ADDR = new HashSet<>();
        ADDI = new HashSet<>();
        MULR = new HashSet<>();
        MULI = new HashSet<>();
        BANR = new HashSet<>();
        BANI = new HashSet<>();
        BORR = new HashSet<>();
        BORI = new HashSet<>();
        SETR = new HashSet<>();
        SETI = new HashSet<>();
        GTIR = new HashSet<>();
        GTRI = new HashSet<>();
        GTRR = new HashSet<>();
        EQIR = new HashSet<>();
        EQRI = new HashSet<>();
        EQRR = new HashSet<>();
    }

    public void setRegisters(int[] reg) {
        for (int x =0; x < reg.length; x++ ) {
            this.registers[x] = reg[x];
        }
    }

    public void init(){
        for (int x =0; x < this.registers.length; x++ ) {
            this.registers[x] = 0;
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

    public void printOpers() {
        System.out.print("MULR : ");
        for (Integer i : this.MULR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nMULI : ");
        for (Integer i : this.MULI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nADDR : ");
        for (Integer i : this.ADDR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nADDI : ");
        for (Integer i : this.ADDI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nBANR : ");
        for (Integer i : this.BANR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nBANI : ");
        for (Integer i : this.BANI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nBORI : ");
        for (Integer i : this.BORI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nBORR : ");
        for (Integer i : this.BORR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nSETI : ");
        for (Integer i : this.SETI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nSETR : ");
        for (Integer i : this.SETR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nGTIR : ");
        for (Integer i : this.GTIR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nGTRI : ");
        for (Integer i : this.GTRI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nGTRR : ");
        for (Integer i : this.GTRR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nEQIR : ");
        for (Integer i : this.EQIR) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nEQRI : ");
        for (Integer i : this.EQRI) {
            System.out.print(" [" + i + "] ");
        }
        System.out.print("\n\nEQRR : ");
        for (Integer i : this.EQRR) {
            System.out.print(" [" + i + "] ");
        }

    }

}

@Data
class Testprogram {
    int[] instruction;

    public Testprogram() {
        this.instruction = new int[4];
    }

    public void doInstruction(String sInstruction, Processor p) {

        String[] f = sInstruction.split(" ");

        this.instruction[0] = Integer.parseInt(f[0]);
        this.instruction[1] = Integer.parseInt(f[1]);
        this.instruction[2] = Integer.parseInt(f[2]);
        this.instruction[3] = Integer.parseInt(f[3]);

        switch (this.instruction[0]) {
            case 0: {
                p.borr(this.instruction);
                break;
            }
            case 1: {
                p.seti(this.instruction);
                break;
            }
            case 2: {
                p.mulr(this.instruction);
                break;
            }
            case 3: {
                p.eqri(this.instruction);
                break;
            }
            case 4: {
                p.banr(this.instruction);
                break;
            }
            case 5: {
                p.bori(this.instruction);
                break;
            }
            case 6: {
                p.bani(this.instruction);
                break;
            }
            case 7: {
                p.gtri(this.instruction);
                break;
            }
            case 8: {
                p.addr(this.instruction);
                break;
            }
            case 9: {
                p.muli(this.instruction);
                break;
            }
            case 10: {
                p.addi(this.instruction);
                break;
            }
            case 11: {
                p.eqrr(this.instruction);
                break;
            }
            case 12: {
                p.gtir(this.instruction);
                break;
            }
            case 13: {
                p.eqir(this.instruction);
                break;
            }
            case 14: {
                p.setr(this.instruction);
                break;
            }
            case 15: {
                p.gtrr(this.instruction);
                break;
            }
        }
    }
}

@Data
class Testcase {
    int[] before;
    int[] instruction;
    int[] after;
    int numMatches;
//    Set<Integer>[] operList;
  

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
            switch (oper) {
                case "ADDR": {
                    p.ADDR.add(instruction[0]);
                    break;
                }
                case "ADDI": {
                    p.ADDI.add(instruction[0]);
                    break;
                }
                case "MULR": {
                    p.MULR.add(instruction[0]);
                    break;
                }
                case "MULI": {
                    p.MULI.add(instruction[0]);
                    break;
                }
                case "BANR": {
                    p.BANR.add(instruction[0]);
                    break;
                }
                case "BANI": {
                    p.BANI.add(instruction[0]);
                    break;
                }
                case "BORR": {
                    p.BORR.add(instruction[0]);
                    break;
                }
                case "BORI": {
                    p.BORI.add(instruction[0]);
                    break;
                }
                case "SETR": {
                    p.SETR.add(instruction[0]);
                    break;
                }
                case "SETI": {
                    p.SETI.add(instruction[0]);
                    break;
                }
                case "GTIR": {
                    p.GTIR.add(instruction[0]);
                    break;
                }
                case "GTRI": {
                    p.GTRI.add(instruction[0]);
                    break;
                }
                case "GTRR": {
                    p.GTRR.add(instruction[0]);
                    break;
                }
                case "EQIR": {
                    p.EQIR.add(instruction[0]);
                    break;
                }
                case "EQRI": {
                    p.EQRI.add(instruction[0]);
                    break;
                }
                case "EQRR": {
                    p.EQRR.add(instruction[0]);
                    break;
                }
            }
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
        List<String> program = null;

        input = Files.readLines(new File("src/main/resources/day16.txt"), Charset.forName("utf-8"));
        program = Files.readLines(new File("src/main/resources/day16-part2.txt"), Charset.forName("utf-8"));

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

//        processor.printOpers();

        processor.init();

        Testprogram testprogram = new Testprogram();

        for (int x = 0; x < program.size(); x++) {
            testprogram.doInstruction(program.get(x), processor);
        }

        System.out.println("The value in register 0 is " + processor.getRegisters()[0]);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());



    }
}
