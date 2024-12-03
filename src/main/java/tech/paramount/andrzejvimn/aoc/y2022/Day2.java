package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.Map;

public class Day2 {

    private static final String INPUT_FILE = "2022/day2-input.txt";

    private static final Map<String, Shape> CODE = Map.of(
            "A", Shape.ROCK,
            "B", Shape.PAPER,
            "C", Shape.SCISSORS,
            "X", Shape.ROCK,
            "Y", Shape.PAPER,
            "Z", Shape.SCISSORS
    );

    public static void solve() {
        var lines = Reader.readLines(INPUT_FILE);
        var rounds = lines.stream().map(Day2::roundScore).toList();
        var total = rounds.stream().mapToInt(Integer::intValue).sum();
        System.out.println(total);
    }

    private static int roundScore(String round) {
        var letters = round.split(" ");
        var me = CODE.get(letters[1]);
        var opponent = CODE.get(letters[0]);
        return scoreShape(me) + scoreOutcome(me, opponent);
    }

    private static int scoreShape(Shape shape) {
        return switch (shape) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };
    }

    private static int scoreOutcome(Shape me, Shape opponent) {
        if (me.equals(opponent)) {
            return 3;
        }
        if (me.equals(Shape.ROCK)) {
            if (opponent.equals(Shape.PAPER)) {
                return 0;
            }
            if (opponent.equals(Shape.SCISSORS)) {
                return 6;
            }
        }
        if (me.equals(Shape.PAPER)) {
            if (opponent.equals(Shape.SCISSORS)) {
                return 0;
            }
            if (opponent.equals(Shape.ROCK)) {
                return 6;
            }
        }
        if (me.equals(Shape.SCISSORS)) {
            if (opponent.equals(Shape.ROCK)) {
                return 0;
            }
            if (opponent.equals(Shape.PAPER)) {
                return 6;
            }
        }
        throw new RuntimeException("Invalid input: %s, %s".formatted(me, opponent));
    }
    
    public static void solve2() {
        var lines = Reader.readLines(INPUT_FILE);
        var rounds = lines.stream().map(Day2::roundScore2).toList();
        var total = rounds.stream().mapToInt(Integer::intValue).sum();
        System.out.println(total);
    }

    private static int roundScore2(String round) {
        var letters = round.split(" ");
        var opponent = CODE.get(letters[0]);
        var me = pickShape(opponent, letters[1]);
        return scoreShape(me) + scoreOutcome(me, opponent);
    }

    private static Shape pickShape(Shape opponent, String code) {
        return switch (code) {
            case "X" -> lose(opponent);
            case "Y" -> draw(opponent);
            case "Z" -> win(opponent);
            default -> throw new IllegalArgumentException(code);
        };
    }
    
    private static Shape win(Shape opponent) {
        return switch (opponent) {
            case ROCK -> Shape.PAPER;
            case PAPER -> Shape.SCISSORS;
            case SCISSORS -> Shape.ROCK;
        };
    }
    
    private static Shape lose(Shape opponent) {
        return switch (opponent) {
            case ROCK -> Shape.SCISSORS;
            case PAPER -> Shape.ROCK;
            case SCISSORS -> Shape.PAPER;
        };
    }
    
    private static Shape draw(Shape opponent) {
        return opponent;
    }
    
    private enum Shape {
        ROCK, PAPER, SCISSORS
    }
}
