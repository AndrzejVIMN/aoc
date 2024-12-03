package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {

    private static final String INPUT_FILE = "2022/day1-input.txt";

    public static void solve() {
        var lines = Reader.readLines(INPUT_FILE);
        int max = 0;
        int current = 0;
        for (var line : lines) {
            if (line.matches("\\d+")) {
                current += Integer.parseInt(line);
            } else {
                if (current > max) {
                    max = current;
                }
                current = 0;
            }
        }
        System.out.println(max);
    }

    public static void solve2() {
        var lines = Reader.readLines(INPUT_FILE);
        var elves = toElves(lines);
        var max3 = elves.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue).sum();
        System.out.println(max3);
    }

    private static List<Integer> toElves(List<String> lines) {
        var elves = new ArrayList<Integer>();
        int current = 0;
        for (var line : lines) {
            if (line.matches("\\d+")) {
                current += Integer.parseInt(line);
            } else {
                if (current > 0) {
                    elves.add(current);
                }
                current = 0;
            }
        }
        return elves;
    }
}
