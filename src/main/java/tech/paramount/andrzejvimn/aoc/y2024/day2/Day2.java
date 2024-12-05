package tech.paramount.andrzejvimn.aoc.y2024.day2;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day2 {

    public void solve() {
        var lines = InputReader.readLines("2024/day2.txt");


        var safeReports = lines.stream().map(this::toLevels).filter(this::isSafe).count();
        System.out.println("Part 1: " + safeReports);

        var kindOfSafeReports = lines.stream().map(this::toLevels).filter(this::isKindOfSafe).count();
        System.out.println("Part 2: " + kindOfSafeReports);
    }

    private boolean isKindOfSafe(List<Integer> levels) {
        if (isSafe(levels)) {
            return true;
        }
        for(int i=0 ; i<levels.size() ; i++) {
            var temp = new ArrayList<>(levels);
            temp.remove(i);
            if (isSafe(temp)) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> toLevels(String line) {
        return Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();
    }

    private boolean isSafe(List<Integer> levels) {

        for (int i=0 ; i<levels.size()-1 ; i++) {
            var diff = Math.abs(levels.get(i) - levels.get(i+1));
            if (diff < 1 || diff > 3) return false;
        }

        var asc = levels.stream().sorted(Comparator.naturalOrder()).toList();
        var desc = levels.stream().sorted(Comparator.reverseOrder()).toList();

        return levels.equals(asc) || levels.equals(desc);
    }
}
