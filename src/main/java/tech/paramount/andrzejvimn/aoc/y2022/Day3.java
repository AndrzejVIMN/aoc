package tech.paramount.andrzejvimn.aoc.y2022;

import org.apache.commons.collections4.ListUtils;

import java.util.List;

public class Day3 {

    private static final String INPUT_FILE = "2022/day3-input.txt";

    public static void solve() {
        var lines = Reader.readLines(INPUT_FILE);
        var total = lines.stream().map(Day3::findMistake).map(Day3::getPriority).mapToInt(Integer::intValue).sum();
        System.out.println(total);
    }
    
    public static void solve2() {
        var lines = Reader.readLines(INPUT_FILE);
        var teams = ListUtils.partition(lines, 3);
        var total = teams.stream().map(Day3::findBadge).map(Day3::getPriority).mapToInt(Integer::intValue).sum();
        System.out.println(total);
    }

    private static char findMistake(String rucksack) {
        char[] firstCompartment = rucksack.substring(0, rucksack.length() / 2).toCharArray();
        char[] secondCompartment = rucksack.substring(rucksack.length() / 2).toCharArray();
        for (char c1 : firstCompartment) {
            for (char c2 : secondCompartment) {
                if (c1 == c2) {
                    return c1;
                }
            }
        }
        throw new RuntimeException("No mistake found in rucksack: " + rucksack);
    }

    private static int getPriority(char item) {
        return (int)item - (item >= 'a' ? 96 : 38);
    }

    private static char findBadge(List<String> rucksacks) {
        if (rucksacks.size() != 3) {
            throw new RuntimeException("Invalid group: " + rucksacks);
        }

        for(char c1 : rucksacks.get(0).toCharArray()) {
            for (char c2 : rucksacks.get(1).toCharArray()) {
                if (c1 == c2) {
                    for (char c3 : rucksacks.get(2).toCharArray()) {
                        if (c1 == c3) {
                            return c3;
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Failed to find badge in: " + rucksacks);
    }
}
