package tech.paramount.andrzejvimn.aoc.y2024.day1;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Day1 {

    public void solve() {
        var lines = InputReader.readLines("2024/day1.txt");

        var first = new ArrayList<Integer>();
        var second = new ArrayList<Integer>();

        lines.stream().forEach(line -> {
            var numbers = line.split("\\s+");
            first.add(Integer.parseInt(numbers[0]));
            second.add(Integer.parseInt(numbers[1]));
        });

        long total = 0;

        first.sort(Integer::compareTo);
        second.sort(Integer::compareTo);

        for (int i=0 ; i<first.size() ; i++) {
            total += Math.abs(first.get(i) - second.get(i));
        }

        System.out.println("Part 1: " + total);

        Map<Integer, Long> secondCounts = new HashMap<>();
        first.stream().distinct().forEach(i -> secondCounts.put(i, second.stream().filter(number -> Objects.equals(number, i)).count()));

        var similarityScore = first.stream().map(i -> i * secondCounts.get(i)).mapToLong(Long::longValue).sum();
        System.out.println("Part 2: " + similarityScore);
    }


}
