package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.Arrays;

public class Day6 {

    private static final String INPUT_FILE = "2022/day6-input.txt";

    public static void solve() {
        var input = Reader.readLines(INPUT_FILE).get(0);
        var markerEnd = markerEnd(input, 4);
        System.out.println(markerEnd);
    }
    
    public static void solve2() {
        var input = Reader.readLines(INPUT_FILE).get(0);
        var markerEnd = markerEnd(input, 14);
        System.out.println(markerEnd);
    }

    private static int markerEnd(String input, int markerLength) {
        for (int i=0 ; i<input.length() ; i++) {
            String marker = input.substring(i, i + markerLength);
            if (Arrays.stream(marker.split("")).distinct().count() == markerLength) {
                return i + markerLength;
            }
        }
        throw new RuntimeException("Failed to find marker.");
    }
}
