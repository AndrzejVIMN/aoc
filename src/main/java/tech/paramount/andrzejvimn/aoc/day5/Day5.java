package tech.paramount.andrzejvimn.aoc.day5;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Day5 {

    private static final Pattern RANGE_MAP_PATTERN = Pattern.compile("(\\d+) (\\d+) (\\d+)");

    public void solve() {
        var lines = InputReader.readLines("day5.txt");

        var seeds = parseSeeds(lines.get(0));

        var seedToSoil = lines.subList(lines.indexOf("seed-to-soil map:") + 1, lines.indexOf("soil-to-fertilizer map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var soilToFertilizer = lines.subList(lines.indexOf("soil-to-fertilizer map:") + 1, lines.indexOf("fertilizer-to-water map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var fertilizerToWater = lines.subList(lines.indexOf("fertilizer-to-water map:") + 1, lines.indexOf("water-to-light map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var waterToLight = lines.subList(lines.indexOf("water-to-light map:") + 1, lines.indexOf("light-to-temperature map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var lightToTemperature = lines.subList(lines.indexOf("light-to-temperature map:") + 1, lines.indexOf("temperature-to-humidity map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var temperatureToHumidity = lines.subList(lines.indexOf("temperature-to-humidity map:") + 1, lines.indexOf("humidity-to-location map:") - 1)
                .stream().map(this::parseRangeMap).toList();

        var humidityToLocation = lines.subList(lines.indexOf("humidity-to-location map:") + 1, lines.size() - 1)
                .stream().map(this::parseRangeMap).toList();

        var locations = seeds.stream()
                .map(seed -> map(seed, seedToSoil))
                .map(soil -> map(soil, soilToFertilizer))
                .map(fertilizer -> map(fertilizer, fertilizerToWater))
                .map(water -> map(water, waterToLight))
                .map(light -> map(light, lightToTemperature))
                .map(temp -> map(temp, temperatureToHumidity))
                .map(humidity -> map(humidity, humidityToLocation))
                .toList();

        var lowestLocation = locations.stream().min(Comparator.naturalOrder()).orElse(0L);
        System.out.println("Lowest location number: " + lowestLocation);

        var allSeeds = parseSeedRanges(lines.get(0));

        var lowestLocationsInRanges = List.of( // calculated using code below
                2293021398L,
                34039469L,
                676172178L,
                895745298L,
                264724955L,
                189363535L,
                98579365L,
                294997587L,
                1293632444L,
                34062606L
        );
        // new ArrayList<Long>();

//        for (var seedRange : allSeeds) {
//            var lowestLoc = LongStream.range(seedRange.start, seedRange.start + seedRange.length)
//                    .map(seed -> map(seed, seedToSoil))
//                    .map(soil -> map(soil, soilToFertilizer))
//                    .map(fertilizer -> map(fertilizer, fertilizerToWater))
//                    .map(water -> map(water, waterToLight))
//                    .map(light -> map(light, lightToTemperature))
//                    .map(temp -> map(temp, temperatureToHumidity))
//                    .map(humidity -> map(humidity, humidityToLocation))
//                    .min().orElse(0L);
//            System.out.println(lowestLoc);
//            lowestLocationsInRanges.add(lowestLoc);
//        }

        var lowestLocationOfAll = lowestLocationsInRanges.stream().min(Comparator.naturalOrder()).orElse(0L);
        System.out.println("Lowest location of all seeds: " + lowestLocationOfAll);
    }

    private List<SeedRange> parseSeedRanges(String line) {
        var matcher = Pattern.compile("(\\d+) (\\d+)").matcher(line);
        var seeds = new ArrayList<SeedRange>();
        while (matcher.find()) {
            var rangeStart = Long.parseLong(matcher.group(1));
            var rangeLength = Long.parseLong(matcher.group(2));
            seeds.add(new SeedRange(rangeStart, rangeLength));
        }
        return seeds;
    }

    record SeedRange(Long start, Long length) {}
    private Long map(Long value, List<RangeMap> maps) {
        return maps.stream()
                .filter(map -> value >= map.sourceStart && value <= map.sourceStart + map.length).findFirst()
                .map(map -> value + (map.destinationStart - map.sourceStart))
                .orElse(value);
    }

    private List<Long> parseSeeds(String line) {
        return Arrays.stream(line.substring(7).split(" ")).map(Long::parseLong).toList();
    }

    private RangeMap parseRangeMap(String line) {
        var matcher = RANGE_MAP_PATTERN.matcher(line.trim());
        if (matcher.matches()) {
            var destinationStart = Long.parseLong(matcher.group(1));
            var sourceStart = Long.parseLong(matcher.group(2));
            var length = Long.parseLong(matcher.group(3));
            return new RangeMap(destinationStart, sourceStart, length);
        }
        throw new RuntimeException("Failed to parse: " + line);
    }

    record RangeMap(long destinationStart, long sourceStart, long length) {};
}
