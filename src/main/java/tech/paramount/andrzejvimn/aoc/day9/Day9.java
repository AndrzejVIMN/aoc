package tech.paramount.andrzejvimn.aoc.day9;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 {

    public void solve() {
        var lines = InputReader.readLines("day9.txt");

        var values = lines.stream().map(this::parseValues).toList();

        var nextResult = values.stream().mapToInt(numbers -> predictNext(calculateDiffs(numbers))).sum();

        System.out.println("Sum of predicted next values: " + nextResult);

        var previousResult = values.stream().mapToInt(numbers -> predictPrevious(calculateDiffs(numbers))).sum();

        System.out.println("Sum of predicted previous values: " + previousResult);
    }

    private int[] parseValues(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private int[] getDifferences(int[] values) {
        var diffs = new int[values.length - 1];

        for (int i=0 ; i<diffs.length ; i++) {
            diffs[i] = values[i+1] - values[i];
        }

        return diffs;
    }

    private List<int[]> calculateDiffs(int[] values) {
        var current = values;
        var diffs = new ArrayList<int[]>();
        while (!Arrays.stream(current).allMatch(i -> i == 0)) {
            diffs.add(current);
            current = getDifferences(current);
        }
        diffs.add(current);
        return diffs;
    }

    private int predictNext(List<int[]> diffs) {
        int next = 0;
        for (int i = diffs.size() - 2 ; i >= 0 ; i--) {
            var line = diffs.get(i);
            next = next + line[line.length - 1];
        }
        return next;
    }

    private int predictPrevious(List<int[]> diffs) {
        int previous = 0;
        for (int i = diffs.size() - 2 ; i >= 0 ; i--) {
            var line = diffs.get(i);
            previous = line[0] - previous;
        }
        return previous;
    }
}
