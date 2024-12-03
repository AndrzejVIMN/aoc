package tech.paramount.andrzejvimn.aoc.y2022;

public class Day4 {

    private static final String INPUT_FILE = "2022/day4-input.txt";

    public static void solve() {
        var lines = Reader.readLines(INPUT_FILE);
        var count = lines.stream().filter(Day4::containsOneAnother).count();
        System.out.println(count);
    }
    
    public static void solve2() {
        var lines = Reader.readLines(INPUT_FILE);
        var count = lines.stream().filter(Day4::overlapsOneAnother).count();
        System.out.println(count);
    }

    private static boolean containsOneAnother(String line) {
        var ranges = line.split(",");
        var range1 = toRange(ranges[0]);
        var range2 = toRange(ranges[1]);
        return range1.contains(range2) || range2.contains(range1);
    }

    private static boolean overlapsOneAnother(String line) {
        var ranges = line.split(",");
        var range1 = toRange(ranges[0]);
        var range2 = toRange(ranges[1]);
        return range1.overlaps(range2);
    }

    private static Range toRange(String line) {
        var numbers = line.split("-");
        return new Range(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
    }

    private static class Range {
        private final int start;
        private final int end;

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(Range another) {
            return this.start <= another.start && this.end >= another.end;
        }

        boolean overlaps(Range another) {
            return this.start <= another.end && this.end >= another.start;
        }
    }
}
