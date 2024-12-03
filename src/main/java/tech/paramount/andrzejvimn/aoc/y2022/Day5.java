package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day5 {

    private static final String INPUT_FILE = "2022/day5-input.txt";

    public static void solve() {
        var lines = Reader.readLines(INPUT_FILE).stream().skip(10).toList(); // too lazy to parse initial state

        var crane = buildCrane();

        lines.forEach(crane::process);

        System.out.println(crane.summary());
    }
    
    public static void solve2() {
        var lines = Reader.readLines(INPUT_FILE).stream().skip(10).toList(); // too lazy to parse initial state

        var crane = buildCrane();

        lines.forEach(crane::process9001);

        System.out.println(crane.summary());

    }

    private static Crane buildCrane() {
        return new Crane(List.of(
                List.of('J', 'H', 'G', 'M', 'Z', 'N', 'T', 'F'),
                List.of('V', 'W', 'J'),
                List.of('G', 'V', 'L', 'J', 'B', 'T', 'H'),
                List.of('B', 'P', 'J', 'N', 'C', 'D', 'V', 'L'),
                List.of('F', 'W', 'S', 'M', 'P', 'R', 'G'),
                List.of('G', 'H', 'C', 'F', 'B', 'N', 'V', 'M'),
                List.of('D', 'H', 'G', 'M', 'R'),
                List.of('H', 'N', 'M', 'V', 'Z', 'D'),
                List.of('G', 'N', 'F', 'H')
        ));
    }

    private static class Crane {

        private final List<List<Character>> stacks;

        private Crane(List<List<Character>> stacks) {
            this.stacks = new ArrayList<>(stacks.size());
            for (List<Character> stack : stacks) {
                this.stacks.add(new ArrayList<>(stack));
            }
        }

        private void process(String command) {
            Pattern pattern = Pattern.compile("move (\\d+) from (\\d) to (\\d)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                int crates = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));
                process(crates, from, to);
            } else {
                throw new RuntimeException("Failed to process: " + command);
            }
        }

        private void process9001(String command) {
            Pattern pattern = Pattern.compile("move (\\d+) from (\\d) to (\\d)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.find()) {
                int crates = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));
                process9001(crates, from, to);
            } else {
                throw new RuntimeException("Failed to process: " + command);
            }
        }

        private void process(int crates, int from, int to) {
            for (int i=0 ; i<crates ; i++) {
                moveCrate(from, to);
            }
        }

        private void process9001(int crates, int from, int to) {
            moveCrates(crates, from, to);
        }

        private void moveCrate(int from, int to) {
            var fromStack = stacks.get(from - 1);
            var crate = fromStack.remove(fromStack.size() - 1);
            stacks.get(to - 1).add(crate);
        }

        private void moveCrates(int count, int from, int to) {
            var fromStack = stacks.get(from - 1);
            var toStack = stacks.get(to - 1);
            var moved = new ArrayList<>(fromStack.subList(fromStack.size() - count, fromStack.size()));
            for (int i=0 ; i<count ; i++) {
                fromStack.remove(fromStack.size() - 1);
            }
            toStack.addAll(moved);
        }

        private String summary() {
            return stacks.stream().map(stack -> stack.get(stack.size()-1)).map(String::valueOf).collect(Collectors.joining());
        }
    }
}
