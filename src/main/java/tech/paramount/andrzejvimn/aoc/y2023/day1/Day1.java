package tech.paramount.andrzejvimn.aoc.y2023.day1;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Day1 {

    public void solve() {
        var lines = InputReader.readLines("2023/day1.txt");

        var totalCalibrationValues = lines.stream().map(this::getCalibrationValue).mapToInt(Integer::intValue).sum();

        System.out.println("Sum of calibration values: " + totalCalibrationValues);

        var totalCorrectCalibrationValues = lines.stream().map(this::getCorrectCalibrationValue).mapToInt(Integer::intValue).sum();

        System.out.println("Sum of correct calibration values: " + totalCorrectCalibrationValues);
    }

    private int getCorrectCalibrationValue(String line) {
        var digits = new ArrayList<String>();
        String pattern = "(?=(\\d|one|two|three|four|five|six|seven|eight|nine))";
        var matcher = Pattern.compile(pattern).matcher(line);
        while (matcher.find()) {
            digits.add(matcher.group(1));
        }
        var first = getDigit(digits.get(0));
        var second = getDigit(digits.get(digits.size() - 1));

        return (10 * first) + second;
    }

    private int getCalibrationValue(String line) {
        var digits = new ArrayList<String>();
        String pattern = "\\d";
        var matcher = Pattern.compile(pattern).matcher(line);
        while (matcher.find()) {
            digits.add(matcher.group());
        }
        var first = getDigit(digits.get(0));
        var second = getDigit(digits.get(digits.size() - 1));

        return (10 * first) + second;
    }

    private int getDigit(String digitName) {
        return switch (digitName) {
            case "one", "1" -> 1;
            case "two", "2" -> 2;
            case "three", "3" -> 3;
            case "four", "4" -> 4;
            case "five", "5" -> 5;
            case "six", "6" -> 6;
            case "seven", "7" -> 7;
            case "eight", "8" -> 8;
            case "nine", "9" -> 9;
            default -> 0;
        };
    }
}
