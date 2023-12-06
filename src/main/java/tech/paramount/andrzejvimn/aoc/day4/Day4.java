package tech.paramount.andrzejvimn.aoc.day4;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {

    public void solve() {
        var lines = InputReader.readLines("day4.txt");

        var sumOfPoints = lines.stream().map(this::parseCard).mapToInt(this::getPoints).sum();

        System.out.println("Sum of points: " + sumOfPoints);

        var cards = lines.stream().map(this::parseCard).toList();
        var cardCounts = cards.stream().collect(Collectors.toMap(c -> c.id, c -> 1));
        var current = 0;
        while (current < cards.size()) {
            var matches = getMatches(cards.get(current));
            for (int i=0 ; i<matches ; i++) {
                if (current + i + 1 < cardCounts.size()) {
                    var copies = cardCounts.get(current + i + 2);
                    cardCounts.put(current + i + 2, copies + cardCounts.get(current + 1));
                }
            }
            current++;
        }

        var totalCards = cardCounts.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("Total cards: " + totalCards);
    }

    private int getMatches(Card card) {
        var matches = 0;
        for (var n : card.numbers) {
            if (card.winners.contains(n)) {
                matches++;
            }
        }
        return matches;
    }

    private int getPoints(Card card) {
        var matches = getMatches(card);
        return matches == 0 ? 0 : (int)Math.pow(2, matches - 1);
    }

    private Card parseCard(String line) {
        var parts = line.substring(line.indexOf(":") + 2).split("\\|");
        var id = Integer.parseInt(line.substring(5, line.indexOf(":")).trim());
        var winners = getNumbers(parts[0]);
        var numbers = getNumbers(parts[1]);
        return new Card(id, winners, numbers);
    }

    private List<Integer> getNumbers(String text) {
        var numbers = new ArrayList<Integer>();
        var pattern = Pattern.compile("\\d+");
        var matcher = pattern.matcher(text);
        while(matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }

    record Card (int id, List<Integer> winners, List<Integer> numbers) {}
}
