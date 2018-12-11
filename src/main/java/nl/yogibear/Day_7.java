package nl.yogibear;

import com.google.common.io.Files;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

interface Callback {
    void onWorkDone();
}

@Data
class Instruction implements Comparable<Instruction> {

    private final char name;
    private Set<Instruction> requirements = new TreeSet<>();
    private boolean ready = false;
    private boolean working = false;

    public boolean isFree() {
        if (requirements.isEmpty()) {
            return true;
        }
        for (Instruction instruction : requirements) {
            if (!instruction.isReady()) {
                return false;
            }
        }
        return true;
    }

    public void addRequirements(Instruction instruction) {
        requirements.add(instruction);
    }

    public void setReady() {
        this.ready = true;
        this.working = false;
    }

    public void resetReady() {
        ready = false;
    }

    public void setWorking() {
        working = true;
    }

    public Instruction getNextInstruction() {
        for (Instruction instruction : requirements) {
            if (!instruction.isReady()) {
                return instruction;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Instruction)) {
            return false;
        }
        return ((Instruction) o).name == name;
    }

    @Override
    public int hashCode() {
        return name;
    }

    @Override
    public int compareTo(Instruction o) {
        return name - o.name;
    }
}

class Worker {
    private int workRemaining = 0;
    private Instruction instruction;
    private Callback callback;

    public void setWorking(Instruction instruction, Callback callback) {  // Set a worker to work on a instruction
        workRemaining = instruction.getName() - 'A' + 1 + 60;
        // workRemaining = instruction.getName() - 'A' + 1;   // this is for the testcase
        this.instruction = instruction;
        this.callback = callback;
        instruction.setWorking();
    }

    public void timeTick() {                     // next second
        if (workRemaining > 0) {
            workRemaining--;
        }

        if (workRemaining == 0) {
            if (instruction != null) {
                instruction.setReady();
            }
            if (callback != null) {
                callback.onWorkDone();
                callback = null;
            }
        }
    }

    public boolean isFree() {
        return workRemaining == 0;
    }
    public char getWork() {                       // gives the cuurent working instruction or a . when free
        return workRemaining > 0 ? instruction.getName() : '.';
    }
}

class Workforce {
    private Collection<Worker> workers = new ArrayList<>();
    private Set<Instruction> workToDo = new HashSet<>();

    public Workforce(final int numWorkers) {
        for (int i = 0; i < numWorkers; i++) {
            workers.add(new Worker());
        }
    }


    public void addWork(Instruction instruction) {   // adds work to the queue , doesn't actually start
        instruction.setWorking();
        workToDo.add(instruction);
    }

    public boolean isWorkerAvailable() {              // is true if at least 1 worker available
        return getAvailableWorker() != null;
    }

    public Worker getAvailableWorker() {           // returns an available working , null if none available
        for (Worker worker : workers) {
            if (worker.isFree()) {
                return worker;
            }
        }
        return null;
    }

    public void processQueuedWork() {                                   // starts queued work, giving it to a free worker
        if (!workToDo.isEmpty()) {
            Iterator<Instruction> iter = workToDo.iterator();
            Worker worker = getAvailableWorker();
            while (worker != null && iter.hasNext()) {
                Instruction instruction = iter.next();
                worker.setWorking(instruction, null);
                iter.remove();
                worker = getAvailableWorker();
            }
        }
    }

    public void timeTick() {                                       // time = time + 1 for all workers
        for (Worker worker : workers) {
            worker.timeTick();
        }
    }

    public boolean isAllWorkDone() {                       // are we ready?
        if (!workToDo.isEmpty()) {
            return false;
        }
        for (Worker worker : workers) {
            if (!worker.isFree()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        boolean first = true;
        for (Worker worker : workers) {
            if (first) first = false;
            s = s + "  " + worker.getWork();
        }
        return s;
    }
}




public class Day_7 {

        private static int NumberOfWorkers = 5;

        public static void main(String[] args) throws IOException {
            LocalTime start = LocalTime.now();

            String result = "";
            char lastChar = ' ';

            List<String> instructionLines = null;

            instructionLines = Files.readLines(new File("src/main/resources/day7.txt"), Charset.forName("utf-8"));

            Map<Character, Instruction> instructions = new TreeMap<>();

            for (String s : instructionLines ) {
                char req = s.charAt(5);
                char next =  s.charAt(36);
                instructions.putIfAbsent(req, new Instruction (req) );
                instructions.putIfAbsent(next, new Instruction (next) );
                instructions.get(next).addRequirements(instructions.get(req));
            }

            while (instructionLines.size() > 0) {
                ArrayList<Character> firstList = new ArrayList<>();
                ArrayList<Character> secondList = new ArrayList<>();
                for (String s : instructionLines) {
                    firstList.add(s.charAt(5));
                    secondList.add(s.charAt(36));
                }

                ArrayList<Character> delta = new ArrayList(firstList);
                delta.removeAll(secondList);
                Collections.sort(firstList);
                Collections.sort(secondList);

                Collections.sort(delta);

                result = result + delta.get(0);
                for (int y=0; y < delta.size(); y++){
                    for (int x = 0; x < instructionLines.size(); x++) {
                        if (instructionLines.get(x).charAt(5) == delta.get(0)){
                            lastChar =  instructionLines.get(x).charAt(36);
                            instructionLines.remove(x);
                        }
                    }
                }
            }

            result = result + lastChar;
            System.out.println("The order I should do the steps in the instructions is " + result);
            LocalTime finish = LocalTime.now();
            System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());

            // ********* Part 2

            start = LocalTime.now();
            for (Instruction instruction : instructions.values()) {
                instruction.resetReady();
            }

            Workforce workforce = new Workforce(NumberOfWorkers);
            int timeElapsed = 0;
            boolean allDone = false;

            while (!allDone || !workforce.isAllWorkDone()) {
                allDone = true;

                for (final Instruction instruction : instructions.values()) {
                    if (instruction.isReady()) {
                        continue;
                    }

                    if (instruction.isFree() && !instruction.isWorking()) {
                        allDone = false;
                        workforce.addWork(instruction);
                    }
                }

                if (!workforce.isAllWorkDone()) {
                    workforce.processQueuedWork();
                    System.out.println("time elapsed " + timeElapsed + " " + workforce.toString());
                    timeElapsed++;
                    workforce.timeTick();
                }

                if (allDone) {
                    for (Instruction instruction : instructions.values()) {
                        if (!instruction.isReady()) {
                            allDone = false;
                            break;
                        }
                    }
                }
            }

            System.out.println("All of the instructions will be completed in " + timeElapsed + " seconds");

            finish = LocalTime.now();
            System.out.println("Duration (ms): " + Duration.between(start, finish).toMillis());
        }
}

