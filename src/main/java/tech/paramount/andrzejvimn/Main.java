package tech.paramount.andrzejvimn;

import tech.paramount.andrzejvimn.aoc.day1.Day1;
import tech.paramount.andrzejvimn.aoc.day2.Day2;
import tech.paramount.andrzejvimn.aoc.day3.Day3;
import tech.paramount.andrzejvimn.aoc.day4.Day4;
import tech.paramount.andrzejvimn.aoc.day5.Day5;
import tech.paramount.andrzejvimn.aoc.day6.Day6;
import tech.paramount.andrzejvimn.aoc.day7.Day7;
import tech.paramount.andrzejvimn.aoc.day7.Day7_2;
import tech.paramount.andrzejvimn.aoc.day8.Day8;
import tech.paramount.andrzejvimn.aoc.day9.Day9;

public class Main {

    public static void main(String[] args) {

        System.out.println("Day 1:");
        new Day1().solve();

        System.out.println("Day 2:");
        new Day2().solve();

        System.out.println("Day 3:");
        new Day3().solve();

        System.out.println("Day 4:");
        new Day4().solve();

        System.out.println("Day 5:");
        new Day5().solve();

        System.out.println("Day 6:");
        new Day6().solve();

        System.out.println("Day 7:");
        new Day7().solve();
        new Day7_2().solve();

        System.out.println("Day 8:");
        new Day8().solve();

        System.out.println("Day 9:");
        new Day9().solve();
    }
}