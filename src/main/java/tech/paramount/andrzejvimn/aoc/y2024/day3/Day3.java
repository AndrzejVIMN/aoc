package tech.paramount.andrzejvimn.aoc.y2024.day3;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.regex.Pattern;

public class Day3 {

    public void solve() {
        var lines = InputReader.readLines("2024/day3.txt");
        var combinedLine = String.join("", lines);

        var mulPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");

        var mulMatcher = mulPattern.matcher(combinedLine);
        var result = 0;
        while (mulMatcher.find()) {
            var match = mulMatcher.group();
            result += calculate(match);;
        }

        System.out.println("Part 1: " + result);

        var conditionalPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))|(do\\(\\))|(don't\\(\\))");
        var conditionalMatcher = conditionalPattern.matcher(combinedLine);
        boolean enabled = true;
        var conditionalResult = 0;
        while (conditionalMatcher.find()) {
            var match = conditionalMatcher.group();
            if (enabled && match.startsWith("mul")) {
                conditionalResult += calculate(match);
            } else if (match.equals("don't()")) {
                enabled = false;
            } else if (match.equals("do()")) {
                enabled = true;
            }
        }

        System.out.println("Part 2: " + conditionalResult);
    }

    private int calculate(String mul) {
        var mulPattern = Pattern.compile("(\\d{1,3}),(\\d{1,3})");
        var matcher = mulPattern.matcher(mul);
        if (!matcher.find()) {
            throw new IllegalArgumentException(mul);
        }
        return Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
    }


}
