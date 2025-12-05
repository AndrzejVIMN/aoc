package tech.paramount.andrzejvimn.aoc.y2025.day5;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Day5 {

    public void solve() {
        var lines = InputReader.readLines("2025/day5.txt");

        var ranges = lines.stream().filter(line -> line.matches("\\d+-\\d+"))
                .map(FreshRange::parse)
                .toList();

        var ingredients = lines.stream().filter(line -> line.matches("\\d+")).mapToLong(Long::parseLong).toArray();

        var freshCount = Arrays.stream(ingredients).filter(i -> ranges.stream().anyMatch(r -> r.isFresh(i))).count();
        System.out.println("Number of fresh ingredients: " + freshCount);

        var sortedRanges = ranges.stream().sorted(Comparator.comparingLong(r -> r.start)).toList();

        var mergedRanges = new ArrayList<FreshRange>();
        var current = sortedRanges.get(0);

        for (int i = 1 ; i < sortedRanges.size(); i++) {
            var next = sortedRanges.get(i);

            if(next.start <= current.end + 1) {
                current = new FreshRange(current.start, Math.max(current.end, next.end));
            } else {
                mergedRanges.add(current);
                current = next;
            }
        }
        mergedRanges.add(current);

        var freshIdCount = mergedRanges.stream().mapToLong(r -> r.end - r.start + 1).sum();
        System.out.println("Number of fresh ingredient IDs: " + freshIdCount);
    }

    private static class FreshRange {
        long start;
        long end;

        public FreshRange(long start, long end) {
            this.start = start;
            this.end = end;
        }

        boolean isFresh(long i) {
            return i >= start && i <= end;
        }

        static FreshRange parse(String line) {
            var parts = line.split("-");
            return new FreshRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }
    }
}
