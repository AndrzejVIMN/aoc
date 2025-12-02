package tech.paramount.andrzejvimn.aoc.y2025.day2;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.Arrays;

public class Day2 {

    public void solve() {
        var lines = InputReader.readLines("2025/day2.txt");

        var ranges = Arrays.stream(lines.get(0).split(",")).map(Range::parse).toList();

        var result = ranges.stream().map(this::sumInvalidIdsInRange).mapToLong(Long::longValue).sum();

        System.out.println("Sum of invalid IDs: " + result);

        var result2 = ranges.stream().map(this::sumInvalidIdsInRange2).mapToLong(Long::longValue).sum();

        System.out.println("Sum of invalid IDs 2: " + result2);
    }

    private static class Range {
        long min;
        long max;

        private Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        static Range parse(String part) {
            String[] tokens = part.split("-");
            long min = Long.parseLong(tokens[0]);
            long max = Long.parseLong(tokens[1]);
            return new Range(min, max);
        }
    }

    private boolean isInvalidId(String id) {
        int len = id.length();
        if (len % 2 != 0) {
            return false;
        }
        String firstHalf = id.substring(0, len / 2);
        String secondHalf = id.substring(len / 2);
        return firstHalf.equals(secondHalf);
    }

    private long sumInvalidIdsInRange(Range range) {
        long sum = 0;
        for (long i = range.min; i <= range.max; i++) {
            String id = Long.toString(i);
            if (isInvalidId(id)) {
                sum += i;
            }
        }
        return sum;
    }

    private boolean isInvalidId2(String id) {
        int len = id.length();
        for (int seqLength = 1; seqLength <= len / 2; seqLength++) {
            if (len % seqLength == 0) {
                String sequence = id.substring(0, seqLength);
                boolean matches = true;
                for (int i = seqLength; i < len; i += seqLength) {
                    if (!id.substring(i, i + seqLength).equals(sequence)) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return true;
                }
            }
        }
        return false;
    }

    private long sumInvalidIdsInRange2(Range range) {
        long sum = 0;
        for (long i = range.min; i <= range.max; i++) {
            String id = Long.toString(i);
            if (isInvalidId2(id)) {
                sum += i;
            }
        }
        return sum;
    }
}
