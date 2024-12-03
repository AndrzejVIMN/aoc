package tech.paramount.andrzejvimn.aoc.y2023.day7;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.List;

public class Day7 {

    public void solve() {
        var lines = InputReader.readLines("2023/day7.txt");

        List<Hand> hands = lines.stream().map(this::parseHand).toList();

        var sortedHands = hands.stream().sorted(this::compareHands).toList();

        var wins = 0;
        for(int i=0 ; i<sortedHands.size() ; i++) {
            var hand = sortedHands.get(i);
            wins += (hand.bid * (i+1));
        }
        System.out.println("Total winnings: " + wins);
    }



    private int compareHands(Hand hand1, Hand hand2) {
        int result = getType(hand1.cards).score - getType(hand2.cards).score;
        if (result == 0) {
            result = getCardValue(hand1.cards.charAt(0)) - getCardValue(hand2.cards.charAt(0));
        }
        if (result == 0) {
            result = getCardValue(hand1.cards.charAt(1)) - getCardValue(hand2.cards.charAt(1));
        }
        if (result == 0) {
            result = getCardValue(hand1.cards.charAt(2)) - getCardValue(hand2.cards.charAt(2));
        }
        if (result == 0) {
            result = getCardValue(hand1.cards.charAt(3)) - getCardValue(hand2.cards.charAt(3));
        }
        if (result == 0) {
            result = getCardValue(hand1.cards.charAt(4)) - getCardValue(hand2.cards.charAt(4));
        }
        return result;
    }

    private HandType getType(String cards) {
        if (isFiveOfAKind(cards)) return HandType.FIVE_OF_A_KIND;
        else if (isFourOfAKind(cards)) return HandType.FOUR_OF_A_KIND;
        else if (isFullHouse(cards)) return HandType.FULL_HOUSE;
        else if (isThreeOfAKind(cards)) return HandType.THREE_OF_A_KIND;
        else if (isTwoPairs(cards)) return HandType.TWO_PAIRS;
        else if (isOnePair(cards)) return HandType.ONE_PAIR;
        else return HandType.HIGH_CARD;
    }

    private boolean isFiveOfAKind(String cards) {
        return cards.chars().distinct().count() == 1;
    }

    private boolean isFourOfAKind(String cards) {
        var distinct = cards.chars().distinct().toArray();
        if (distinct.length != 2) return false;
        var count = cards.chars().filter(card -> card == distinct[0]).count();
        return count == 4 || count == 1;
    }

    private boolean isFullHouse(String cards) {
        var distinct = cards.chars().distinct().toArray();
        if (distinct.length != 2) return false;
        var count1 = cards.chars().filter(card -> card == distinct[0]).count();
        var count2 = cards.chars().filter(card -> card == distinct[1]).count();
        return (count1 == 2 && count2 == 3) || (count1 == 3 && count2 == 2);
    }

    private boolean isThreeOfAKind(String cards) {
        var distinct = cards.chars().distinct().toArray();
        if (distinct.length != 3) return false;
        var count1 = cards.chars().filter(card -> card == distinct[0]).count();
        var count2 = cards.chars().filter(card -> card == distinct[1]).count();
        var count3 = cards.chars().filter(card -> card == distinct[2]).count();
        return (count1 == 3 && count2 == 1 && count3 == 1)
                || (count1 == 1 && count2 == 3 && count3 == 1)
                || (count1 == 1 && count2 == 1 && count3 == 3);
    }

    private boolean isTwoPairs(String cards) {
        var distinct = cards.chars().distinct().toArray();
        if (distinct.length != 3) return false;
        var count1 = cards.chars().filter(card -> card == distinct[0]).count();
        var count2 = cards.chars().filter(card -> card == distinct[1]).count();
        var count3 = cards.chars().filter(card -> card == distinct[2]).count();
        return (count1 == 2 && count2 == 2 && count3 == 1)
                || (count1 == 2 && count2 == 1 && count3 == 2)
                || (count1 == 1 && count2 == 2 && count3 == 2);
    }

    private boolean isOnePair(String cards) {
        var distinct = cards.chars().distinct().toArray();
        return distinct.length == 4;
    }

    enum HandType {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIRS(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int score;

        HandType(int score) {
            this.score = score;
        }
    }

    private Hand parseHand(String line) {
        var parts = line.split(" ");
        var cards = parts[0];
        if (cards.length() != 5) throw new RuntimeException("Failed to parse cards: " + line);
        var bid = Integer.parseInt(parts[1]);
        return new Hand(cards, bid);
    }

    record Hand (String cards, int bid) {}

    private int getCardValue(char card) {
        return switch (card) {
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            case 'A' -> 14;
            default -> 0;
        };
    }
}
