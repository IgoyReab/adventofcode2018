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
class Flow {
    long[] registers;

    public Flow() {
        this.registers = new long[6];
        for (long i : this.registers) i = 0;
    }

    public void setRegisters(int a, int b, int c, long d, long e, long f){
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

    public void printRegisters(String s, Flow p) {
        System.out.print(s);
        System.out.println("  Processor   : [" + p.getRegisters()[0] + "] [" + p.getRegisters()[1] + "] [" + p.getRegisters()[2] + "] [" + p.getRegisters()[3] + "] ["  + p.getRegisters()[4] + "] [" +  + p.getRegisters()[5] + "]");
//        System.out.println();
    }

    public void addr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] + this.registers[b];
    }

    public void addi(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] + b;
    }

    public void mulr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] * this.registers[b];
    }

    public void muli(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] * b;
    }

    public void banr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] & this.registers[b];
    }

    public void bani(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] & b;
    }

    public void borr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] | this.registers[b];
    }

    public void bori(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a] | b;
    }

    public void setr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = this.registers[a];
    }

    public void seti(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int c = Integer.parseInt(instr[3]);
        this.registers[c] = a;
    }

    public void gtir(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (a > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void gtri(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (this.registers[a] > b) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void gtrr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (this.registers[a] > this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void eqir(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (a == this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void eqri(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (this.registers[a] == b) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }

    public void eqrr(String[] instr) {
        int a = Integer.parseInt(instr[1]);
        int b = Integer.parseInt(instr[2]);
        int c = Integer.parseInt(instr[3]);
        if (this.registers[a] == this.registers[b]) {
            this.registers[c] = 1;
        } else {
            this.registers[c] = 0;
        }
    }
}

@Data
class Flowprogram {
    String[] instruction;

    public Flowprogram() {
        this.instruction = new String[4];
    }

    public void doInstruction(String sInstruction, Flow p) {

        String[] f = sInstruction.split(" ");

        this.instruction[0] = f[0];
        this.instruction[1] = f[1];
        this.instruction[2] = f[2];
        this.instruction[3] = f[3];

        switch (this.instruction[0]) {
            case "borr": {
                p.borr(this.instruction);
                break;
            }
            case "seti": {
                p.seti(this.instruction);
                break;
            }
            case "mulr": {
                p.mulr(this.instruction);
                break;
            }
            case "eqri": {
                p.eqri(this.instruction);
                break;
            }
            case "banr": {
                p.banr(this.instruction);
                break;
            }
            case "bori": {
                p.bori(this.instruction);
                break;
            }
            case "bani": {
                p.bani(this.instruction);
                break;
            }
            case "gtri": {
                p.gtri(this.instruction);
                break;
            }
            case "addr": {
                p.addr(this.instruction);
                break;
            }
            case "muli": {
                p.muli(this.instruction);
                break;
            }
            case "addi": {
                p.addi(this.instruction);
                break;
            }
            case "eqrr": {
                p.eqrr(this.instruction);
                break;
            }
            case "gtir": {
                p.gtir(this.instruction);
                break;
            }
            case "eqir": {
                p.eqir(this.instruction);
                break;
            }
            case "setr": {
                p.setr(this.instruction);
                break;
            }
            case "gtrr": {
                p.gtrr(this.instruction);
                break;
            }
        }
    }
}


public class Day_19 {

    public static void main(String[] args) throws IOException {
        LocalTime start = LocalTime.now();

        List<String> input = null;

        input = Files.readLines(new File("src/main/resources/day19.txt"), Charset.forName("utf-8"));

        String sFlowRegister = input.get(0);

        String[] f = sFlowRegister.split(" ");
        int jumpRegister = Integer.parseInt(f[1]);


        input.remove(0);

//        Flow proc = new Flow();
//
//        Flowprogram flow = new Flowprogram();
//
//  //      proc.printRegisters(" y:" + proc.registers[jumpRegister], proc);
//
//        do {
//            flow.doInstruction(input.get(proc.registers[jumpRegister]), proc);
//            proc.registers[jumpRegister] = proc.registers[jumpRegister] + 1;
////            proc.printRegisters(" y:" + proc.registers[jumpRegister], proc);
//            if (proc.registers[jumpRegister] >= input.size()) break;
//
//        } while (proc.registers[jumpRegister] < input.size());

 //       System.out.println("The value in register 0 is " + proc.getRegisters()[0]);

        LocalTime finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

        // part 2

        start = LocalTime.now();
        Flow proc2 = new Flow();

        Flowprogram flow2 = new Flowprogram();

//        proc2.setRegisters(1,0,0,0,0,0);
//
//        for (long x= 0; x < 500; x++) {
////        do {
//            proc2.printRegisters(" y:" + proc2.registers[jumpRegister], proc2);
//            flow2.doInstruction(input.get(proc2.registers[jumpRegister]), proc2);
//            proc2.registers[jumpRegister] = proc2.registers[jumpRegister] + 1;
//        }
////        } while (proc2.registers[jumpRegister] < input.size());

        proc2.setRegisters(10551362,10551361,10551361,4,10551361,111331218952321L);  //  [10551362] [10551361] [10551361] [4] [10551361] [111331218952321]

//        for (long x= 0; x < 20; x++) {
        do {

            flow2.doInstruction(input.get((int)proc2.registers[jumpRegister]), proc2);
            proc2.registers[jumpRegister] = proc2.registers[jumpRegister] + 1;
            proc2.printRegisters(" y:" + proc2.registers[jumpRegister], proc2);
            if (proc2.registers[jumpRegister] >= input.size()) break;
        } while (proc2.registers[jumpRegister] < input.size());
        proc2.printRegisters(" y:" + proc2.registers[jumpRegister], proc2);
        System.out.println("The value in register 0 is " + proc2.getRegisters()[0]);

        finish = LocalTime.now();
        System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());


    }
}
