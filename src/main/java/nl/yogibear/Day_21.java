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
class Processor21 {
    long[] registers;

    public Processor21() {
        this.registers = new long[6];
        for (long i : this.registers) i = 0;
    }

    public void setRegisters(long a, long b, long c, long d, long e, long f){
            this.registers[0] = a;
            this.registers[1] = b;
            this.registers[2] = c;
            this.registers[3] = d;
            this.registers[4] = e;
            this.registers[5] = f;
    }

    public void setRegisters(long[] reg) {
        for (int x = 0; x < reg.length; x++) {
            this.registers[x] = reg[x];
        }
    }

    public void init() {
        for (int x = 0; x < this.registers.length; x++) {
            this.registers[x] = 0;
        }
    }

    public void printRegisters(String s, Processor21 p) {
        System.out.print(s);
        System.out.print("  Processor   : [" + p.getRegisters()[0] + "] [" + p.getRegisters()[1] + "] [" + p.getRegisters()[2] + "] [" + p.getRegisters()[3] + "] ["  + p.getRegisters()[4] + "] [" +  + p.getRegisters()[5] + "]");
//        System.out.println();
    }

    public void addr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] + this.registers[b];

        if (print) {
            System.out.print(" addr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " = " + d + " + " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void addi(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        // long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] + b;

        if (print) {
            System.out.print(" addi\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " = " + d + " + " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void mulr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] * this.registers[b];
        if (print) {
            System.out.print(" mulr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " * " + e + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void muli(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        // long e = this.registers[b];
        long f = this.registers[c];

        f = d * b;
        this.registers[c] = f;
        if (print) {
            System.out.print(" muli\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " * " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void banr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] & this.registers[b];
        if (print) {
            System.out.print(" banr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " & " + e + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void bani(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
//        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] & b;
        if (print) {
            System.out.print(" bani\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " & " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void borr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] | this.registers[b];
        if (print) {
            System.out.print(" borr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " | " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void bori(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
//        long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a] | b;
        if (print) {
            System.out.print(" bori\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " | " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void setr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        //long e = this.registers[b];
        long f = this.registers[c];
        this.registers[c] = this.registers[a];
        if (print) {
            System.out.print(" setr\t" + a + " " + "na"  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void seti(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int c = Integer.parseInt(instr[3]);
//        long d = this.registers[a];
//        long e = this.registers[b];
//        long f = this.registers[c];
        this.registers[c] = a;
        if (print) {
            System.out.print(" seti\t" + a + " " + "na"  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + a + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void gtir(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
//        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        if (a > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" gtir\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + a + " > " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void gtri(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
//        long e = this.registers[b];
        long f = this.registers[c];
        if (this.registers[a] > b) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" gtri\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " > " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void gtrr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        if (this.registers[a] > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" gtrr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " =  " + d + " > " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void eqir(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
//        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        if (a == this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" eqir\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " ==  " + a + " == " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void eqri(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
//        long e = this.registers[b];
        long f = this.registers[c];
        if (this.registers[a] == b) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" eqri\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " ==  " + d + " == " + b  + " = " + this.registers[c]);
//            System.out.println();
        }
    }

    public void eqrr(String[] instr, boolean print) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        long d = this.registers[a];
        long e = this.registers[b];
        long f = this.registers[c];
        if (this.registers[a] == this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
        if (print) {
            System.out.print(" eqrr\t" + a + " " + b  + " " + c );
            System.out.print("\t\t\tRegister\t" + c + " ==  " + d + " == " + e  + " = " + this.registers[c]);
//            System.out.println();
        }
    }
}

@Data
class Program21 {
    String[] instruction;

    public Program21() {
        this.instruction = new String[4];
    }

    public void doInstruction(String sInstruction, Processor21 p, boolean print) {

        String[] f = sInstruction.split(" ");

        this.instruction[0] = f[0];
        this.instruction[1] = f[1];
        this.instruction[2] = f[2];
        this.instruction[3] = f[3];

//        System.out.print(f[0] + " " + f[1] + " " + f[2] + " " + f[3] + " ");


        switch (this.instruction[0]) {
            case "borr": {
                p.borr(this.instruction, print);
                break;
            }
            case "seti": {
                p.seti(this.instruction, print);
                break;
            }
            case "mulr": {
                p.mulr(this.instruction, print);
                break;
            }
            case "eqri": {
                p.eqri(this.instruction, print);
                break;
            }
            case "banr": {
                p.banr(this.instruction, print);
                break;
            }
            case "bori": {
                p.bori(this.instruction, print);
                break;
            }
            case "bani": {
                p.bani(this.instruction, print);
                break;
            }
            case "gtri": {
                p.gtri(this.instruction, print);
                break;
            }
            case "addr": {
                p.addr(this.instruction, print);
                break;
            }
            case "muli": {
                p.muli(this.instruction, print);
                break;
            }
            case "addi": {
                p.addi(this.instruction, print);
                break;
            }
            case "eqrr": {
                p.eqrr(this.instruction, print);
                break;
            }
            case "gtir": {
                p.gtir(this.instruction, print);
                break;
            }
            case "eqir": {
                p.eqir(this.instruction, print);
                break;
            }
            case "setr": {
                p.setr(this.instruction, print);
                break;
            }
            case "gtrr": {
                p.gtrr(this.instruction, print);
                break;
            }
        }
    }
}


public class Day_21 {

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day21.txt"), Charset.forName("utf-8"));

        String sFlowRegister = input.get(0);

        String[] f = sFlowRegister.split(" ");
        int jumpRegister = Integer.parseInt(f[1]);
        long instructionPointer = 0;


        input.remove(0);

        Processor21 processor21 = new Processor21();

        Program21 program21 = new Program21();

        instructionPointer = processor21.registers[jumpRegister];

        long count = 0;
        long mincount = Long.MAX_VALUE;
        processor21.setRegisters(11050031, 0, 0, 0, 0, 0);  // 1] [3] [19] [65536] [1024] [13606628]
//        processor21.setRegisters(1, 255, 19, 65536, 65280, 13606628);  // 1] [3] [19] [65536] [1024] [13606628]
//        processor21.setRegisters(1, 255, 19, 65536, 65280, 4869452);  // 1] [3] [19] [65536] [1024] [13606628]
//        processor21.setRegisters(1, 255, 19, 65536, 65280, 10984132);  // 1] [3] [19] [65536] [1024] [13606628]
//        processor21.setRegisters(1, 255, 19, 65536, 65280, 7107564);  // 1] [3] [19] [65536] [1024] [13606628]
//        processor21.setRegisters(1, 255, 19, 65536, 65280, 11820964);  // 1] [3] [19] [65536] [1024] [13606628]
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 6790540);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 7890308);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 3928620);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 2909284);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 5659084);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 4019268);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 3832940);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 5926180);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 6078988);
////        processor21.setRegisters(1, 255, 19, 65536, 65280, 9643780);


//        processor21.printRegisters(" -> ", processor21);
//        instructionPointer = processor21.registers[jumpRegister] + 1;
//        for (long x = 1; x < 40 ; x++) {
//
//            processor21.registers[jumpRegister] = instructionPointer;
//            if (instructionPointer >= input.size()) break;
//            program21.doInstruction(input.get((int) instructionPointer), processor21, true);
//            instructionPointer = (processor21.registers[jumpRegister] + 1);
//            processor21.printRegisters("\t\t\t\t\t\t\t -> ", processor21);
//            System.out.println();
//        }

//        for (long x = Integer.MAX_VALUE; x > 0; x=x-100000) {
//
//            processor21.setRegisters(x, 0, 0, 0, 0, 0);
//            instructionPointer = processor21.registers[jumpRegister];
//            count = 0;
//
//            do {
//                count++;
//                processor21.registers[jumpRegister] = instructionPointer;
//                if (instructionPointer >= input.size()) break;
//                program21.doInstruction(input.get((int) instructionPointer), processor21, false);
//                instructionPointer = (processor21.registers[jumpRegister] + 1);
////                processor21.printRegisters(" y:" + instructionPointer, processor21);
//                if (count > 100000000L) break;
//            } while (instructionPointer < input.size());
//
//            if (count < mincount) mincount = count;
//            if (mincount < 100000000L) {
//                processor21.printRegisters(" x:" + x, processor21);
//                break;
//            }
//            System.out.println("Next x " + x + " mincount = " + mincount);
//        }
//
//        System.out.println("mincount = " + mincount + " x= " + processor21.getRegisters()[0]);

//     //   proc.printRegisters(" y:" + instructionPointer, proc);
        System.out.println("The value in register 0 is " + processor21.getRegisters()[0]);

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        // part 2

        start = LocalTime.now();


        System.out.println("\nThe value in register 0 is " );

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
    }
}
