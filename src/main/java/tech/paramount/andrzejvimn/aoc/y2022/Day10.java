package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day10 {

    private static final String INPUT_FILE = "2022/day10-input.txt";

    public static void solve() {
        var input = Reader.readLines(INPUT_FILE);
        var processor = new Processor(false);
        input.forEach(line -> process(processor, line));
        var signalStrength = processor.getSignalStrength();
        System.out.println(signalStrength);
    }
    
    public static void solve2() {
        var input = Reader.readLines(INPUT_FILE);
        var processor = new Processor(true);
        input.forEach(line -> process(processor, line));
    }

    private static void process(Processor processor, String line) {
        if (line.startsWith("addx")) {
            processor.addx(Integer.parseInt(line.split(" ")[1]));
        } else if (line.equals("noop")) {
            processor.noop();
        }
    }

    private static class Processor {

        private List<Log> logs;

        private int register;

        private int cycle;

        private boolean screenOn;

        private Processor(boolean screenOn) {
            this.screenOn = screenOn;
            register = 1;
            cycle = 0;
            logs = new ArrayList<>();
        }

        private void addx(int x) {
            int before = register;
            advanceCycle(register);
            register += x;
            advanceCycle(before);
        }

        private void noop() {
            advanceCycle(register);
        }

        private void advanceCycle(int currentValue) {
            cycle++;
            logCycle(currentValue);
            printCRT();
        }

        private void printCRT() {
            if (screenOn) {
                int crt = cycle % 40;
                if (crt % 40 == 0) {
                    System.out.println();
                }
                if (crt == register - 1 || crt == register || crt == register + 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
        }

        private void logCycle(int valueBefore) {
            logs.add(new Log(cycle, valueBefore, register));
        }

        private Log getCycleLog(int cycle) {
            return logs.stream().filter(log -> log.cycle == cycle).findFirst().orElseThrow();
        }

        private int getSignalStrength() {
            return Stream.of(20, 60, 100, 140, 180, 220).mapToInt(cycle -> cycle * getCycleLog(cycle).valueBefore).sum();
        }
    }

    private record Log (int cycle, int valueBefore, int valueAfter) {}
}
