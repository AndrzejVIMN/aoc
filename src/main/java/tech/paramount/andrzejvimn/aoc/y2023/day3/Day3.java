package tech.paramount.andrzejvimn.aoc.y2023.day3;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.List;
import java.util.regex.Pattern;

public class Day3 {

    public void solve() {
        var lines = InputReader.readLines("2023/day3.txt");

        var sum = 0;
        var pattern = Pattern.compile("\\d+");
        for (int i=0 ; i < lines.size() ; i++) {
            var matcher = pattern.matcher(lines.get(i));
            while (matcher.find()) {
                var start = matcher.start();
                var end = matcher.end();
                var isPartNumber = isPartNumber(lines, i, start, end-1);
                if (isPartNumber) {
                    sum += Integer.parseInt(matcher.group());
                }
            }
        }

        System.out.println("Sum of part numbers: " + sum);

        var sumOfGearRatios = 0;
        var gearPattern = Pattern.compile("\\*");
        for (int i=0 ; i < lines.size() ; i++) {
            var matcher = gearPattern.matcher(lines.get(i));
            while (matcher.find()) {
                var start = matcher.start();
                var end = matcher.end();
                if (start != end - 1) {
                    throw new RuntimeException("error parsing gears: " + lines.get(i));
                }
                sumOfGearRatios += getGearRatio(lines, i, start);
            }
        }

        System.out.println("Sum of gear ratios: " + sumOfGearRatios);
    }

    private boolean isPartNumber(List<String> lines, int line, int start, int end) {
        if (line > 0) {
            var previousLine = lines.get(line - 1);
            if (start > 0 && previousLine.charAt(start - 1) != '.') return true;
            for (int i = start ; i <= end ; i++) {
                if (previousLine.charAt(i) != '.') return true;
            }
            if (end < previousLine.length() - 1 && previousLine.charAt(end + 1) != '.') return true;
        }

        var currentLine = lines.get(line);
        if (start > 0 && currentLine.charAt(start - 1) != '.') return true;
        if (end < currentLine.length() - 1 && currentLine.charAt(end + 1) != '.') return true;

        if (line < lines.size() - 1) {
            var nextLine = lines.get(line + 1);
            if (start > 0 && nextLine.charAt(start - 1) != '.') return true;
            for (int i = start ; i <= end ; i++) {
                if (nextLine.charAt(i) != '.') return true;
            }
            if (end < nextLine.length() - 1 && nextLine.charAt(end + 1) != '.') return true;
        }

        return false;
    }

    private int getGearRatio(List<String> lines, int line, int gear) {
        var count = 0;
        var ratio = 1;
        if (line > 0) {
            var previousLine = lines.get(line - 1);
            var matcher = Pattern.compile("\\d+").matcher(previousLine);
            while (matcher.find()) {
                if (gear >= matcher.start() - 1 && gear <= matcher.end()) {
                    count++;
                    ratio *= Integer.parseInt(matcher.group());
                }
            }
        }

        var currentLine = lines.get(line);
        var matcher = Pattern.compile("\\d+").matcher(currentLine);
        while (matcher.find()) {
            if (gear >= matcher.start() - 1 && gear <= matcher.end()) {
                count++;
                ratio *= Integer.parseInt(matcher.group());
            }
        }

        if (line < lines.size() - 1) {
            var nextLine = lines.get(line + 1);
            var matcher1 = Pattern.compile("\\d+").matcher(nextLine);
            while (matcher1.find()) {
                if (gear >= matcher1.start() - 1 && gear <= matcher1.end()) {
                    count++;
                    ratio *= Integer.parseInt(matcher1.group());
                }
            }
        }

        return count == 2 ? ratio : 0;
    }
}
