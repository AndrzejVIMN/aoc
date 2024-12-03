package tech.paramount.andrzejvimn.aoc.y2023.day6;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Day6 {

    public void solve() {
        var lines = InputReader.readLines("2023/day6.txt");

        var races = parseRaces(lines);

        var waysToWin = races.stream().map(race ->
                LongStream.range(1, race.time)
                .map(held -> calculateDistance(held, race.time))
                .filter(distance -> distance > race.distance)
                .count()
        ).reduce((a, b) -> a * b).orElse(0L);

        System.out.println("Ways to win all races: " + waysToWin);

        var fixedInput = lines.stream().map(line -> line.replaceAll("\\s+", "")).toList();
        var theOneRace = parseRaces(fixedInput).get(0);

        var waysToWinTheOneRace = LongStream.range(1, theOneRace.time)
                .map(held -> calculateDistance(held, theOneRace.time))
                .filter(distance -> distance > theOneRace.distance)
                .count();

        System.out.println("Ways to win the one race: " + waysToWinTheOneRace);
    }

    private long calculateDistance(long held, long totalTime) {
        var speed = held;
        return (totalTime - held) * speed;
    }

    private List<Race> parseRaces(List<String> lines) {
        var times = getNumbers(lines.get(0));
        var distances = getNumbers(lines.get(1));
        var races = new ArrayList<Race>();
        for (int i=0 ; i<times.size() ; i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
        return races;
    }

    private List<Long> getNumbers(String line) {
        var matcher = Pattern.compile("\\d+").matcher(line);
        var numbers = new ArrayList<Long>();
        while(matcher.find()) {
            numbers.add(Long.parseLong(matcher.group()));
        }
        return numbers;
    }

    record Race(long time, long distance) {}
}
