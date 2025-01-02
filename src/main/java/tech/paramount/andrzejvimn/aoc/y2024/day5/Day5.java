package tech.paramount.andrzejvimn.aoc.y2024.day5;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

    public void solve() {
        var lines = InputReader.readLines("2024/day5.txt");

        var rules = lines.stream().filter(line -> line.matches("\\d+\\|\\d+")).map(line -> {
            var numbers = line.split("\\|");
            return new Rule(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        }).toList();

        var updates = lines.stream().filter(line -> line.matches("(\\d+,?)+"))
                .map(this::updateToList)
                .toList();

        var result1 = updates.stream()
                .filter(update -> isCorrect(update, rules))
                .mapToInt(this::getMiddle)
                .sum();

        System.out.println("Part 1: " + result1);

        var result2 = updates.stream()
                .filter(update -> !isCorrect(update, rules))
                .map(update -> correct(update, rules))
                .mapToInt(this::getMiddle)
                .sum();

        System.out.println("Part 2: " + result2);
    }

    private record Rule(int first, int second){}

    private List<Integer> updateToList(String update) {
        return Arrays.stream(update.split(",")).map(Integer::parseInt).toList();
    }

    private int getMiddle(List<Integer> list) {
        return list.get(list.size() / 2);
    }

    private boolean isCorrect(List<Integer> update, List<Rule> rules) {
        for (Rule rule : rules) {
            if (update.contains(rule.first) && update.contains(rule.second)) {
                if (update.indexOf(rule.first) > update.indexOf(rule.second)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> correct(List<Integer> update, List<Rule> rules) {
        while (!isCorrect(update, rules)) {
            for (Rule rule : rules) {
                if (update.contains(rule.first) && update.contains(rule.second)) {
                    if (update.indexOf(rule.first) > update.indexOf(rule.second)) {
                        var fixed = new ArrayList<>(update);
                        fixed.set(update.indexOf(rule.second), rule.first);
                        fixed.set(update.indexOf(rule.first), rule.second);
                        update = fixed;
                    }
                }
            }
        }
        return update;
    }

}
