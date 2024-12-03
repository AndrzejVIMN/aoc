package tech.paramount.andrzejvimn.aoc.y2023.day2;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    public void solve() {
        var lines = InputReader.readLines("2023/day2.txt");

        var allowedRed = 12;
        var allowedGreen = 13;
        var allowedBlue = 14;

        var sumOfPossibleGames = lines.stream().map(this::parseGame)
                .filter(game -> isPossible(game, allowedRed, allowedGreen, allowedBlue))
                .mapToInt(Game::id)
                .sum();

        System.out.println("Sum of possible game IDs: " + sumOfPossibleGames);

        var sumOfPowers = lines.stream().map(this::parseGame)
                .mapToInt(this::getPower)
                .sum();

        System.out.println("Sum of game set powers: " + sumOfPowers);
    }

    private int getPower(Game game) {
        return maxRed(game) * maxGreen(game) * maxBlue(game);
    }

    private int maxRed(Game game) {
        return game.rounds.stream().mapToInt(r -> r.red).max().orElse(0);
    }

    private int maxBlue(Game game) {
        return game.rounds.stream().mapToInt(r -> r.blue).max().orElse(0);
    }

    private int maxGreen(Game game) {
        return game.rounds.stream().mapToInt(r -> r.green).max().orElse(0);
    }

    private boolean isPossible(Game game, int allowedRed, int allowedGreen, int allowedBlue) {
        return game.rounds.stream().allMatch(round ->
                round.red <= allowedRed &&
                round.blue <= allowedBlue &&
                round.green <= allowedGreen);
    }

    private Game parseGame(String line) {
        var gameId = Integer.parseInt(line.substring(5, line.indexOf(":")));
        var rounds = new ArrayList<Round>();
        Arrays.stream(line.substring(line.indexOf(":") + 2)
                .split("; "))
                .forEach(r -> rounds.add(parseRound(r)));
        return new Game(gameId, rounds);
    }

    private Round parseRound(String counts) {
        var parts = counts.split(", ");
        if (parts.length > 3) {
            throw new RuntimeException("buggy parsing of: " + counts);
        }
        var round = new Round();
        for (var part : parts) {
            var details = part.split(" ");
            if (details[1].equals("red")) {
                round.red = Integer.parseInt(details[0]);
            } else if (details[1].equals("green")) {
                round.green = Integer.parseInt(details[0]);
            } else if (details[1].equals("blue")) {
                round.blue = Integer.parseInt(details[0]);
            }
        }
        return round;
    }

    record Game(int id, List<Round> rounds) {}

    static class Round {

        int red = 0;
        int green = 0;
        int blue = 0;

        @Override
        public String toString() {
            return "Round[red=%d, green=%d, blue=%d]".formatted(red, green, blue);
        }
    }
}
