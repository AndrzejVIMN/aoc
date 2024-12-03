package tech.paramount.andrzejvimn.aoc.y2022;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Day11 {

    private static final String INPUT_FILE = "2022/day11-input.txt";

    public static void solve() {
        var game = new MonkeyGame(prepareMonkeys(), true);
        game.playRounds(20);
        game.printMonkeyStats();

        var monkeyBusiness = game.calculateMonkeyBusiness();
        System.out.println(monkeyBusiness);
    }
    
    public static void solve2() {
        var game = new MonkeyGame(prepareMonkeys(), false);
        game.playRounds(10000);
        game.printMonkeyStats();

        var monkeyBusiness = game.calculateMonkeyBusiness();
        System.out.println(monkeyBusiness);
    }

    private static class MonkeyGame {

        private final boolean worryDecrease;

        private final List<Monkey> monkeys;

        private MonkeyGame(List<Monkey> monkeys, boolean worryDecrease) {
            this.monkeys = monkeys;
            this.worryDecrease = worryDecrease;
        }

        private void playRound() {
            for (int i=0 ; i<monkeys.size(); i++) {
//                System.out.printf("Monkey %d:%n", i);
                monkeys.get(i).processItems(worryDecrease);
            }
        }

        private void playRounds(int rounds) {
            for (int i=0 ; i<rounds ; i++) {
                System.out.printf("Round %d%n", i);
                playRound();
            }
        }

        private void printMonkeyStats() {
            for (int i=0 ; i< monkeys.size() ; i++) {
                System.out.printf("Monkey %d inspected items %d times.%n", i, monkeys.get(i).inspectCount);
            }
        }

        private int calculateMonkeyBusiness() {
            var top2Monkeys = monkeys.stream().map(monkey -> monkey.inspectCount)
                    .sorted(Comparator.reverseOrder())
                    .limit(2).mapToInt(Integer::intValue).toArray();
            return top2Monkeys[0] * top2Monkeys[1];
        }
    }

    private static class Monkey {

        private int inspectCount;

        private final List<BigInteger> items; // todo this will kill the processor with how large the numbers get (x^2), need to figure sth else out

        private final UnaryOperator<BigInteger> operation;

        private final Predicate<BigInteger> test;

        private Monkey target;

        private Monkey alternateTarget;

        private Monkey(List<Integer> items, UnaryOperator<BigInteger> operation, Predicate<BigInteger> test) {
            this.items = new ArrayList<>();
            items.forEach(item -> this.items.add(BigInteger.valueOf(item)));
            this.operation = operation;
            this.test = test;
            this.inspectCount = 0;
        }

        private void inspectItem(boolean worryDecrease) {
            BigInteger item = items.remove(0);
            if (item.signum() == -1) throw new RuntimeException();
//            System.out.printf("Monkey inspects an item with a worry level of %d.%n", item);
            inspectCount++;
            item = operation.apply(item);
//            System.out.printf("Worry level changes to %d.%n", item);
            if (worryDecrease) {
                item = item.divide(BigInteger.valueOf(3));
//                System.out.printf("Monkey gets bored with item. Worry level is divided by 3 to %d.%n", item);
            }
            if (test.test(item)) {
                target.items.add(item);
            } else {
                alternateTarget.items.add(item);
            }
        }

        private void processItems(boolean worryDecrease) {
            int itemsToProcess = items.size();
            for (int i=0 ; i<itemsToProcess ; i++) {
                inspectItem(worryDecrease);
            }
        }
    }

    private static List<Monkey> prepareMonkeys() {
        var monkey0 = new Monkey(
                List.of(59, 74, 65, 86),
                worry -> worry.multiply(BigInteger.valueOf(19)),
                item -> item.mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)
        );
        var monkey1 = new Monkey(
                List.of(62, 84, 72, 91, 68, 78, 51),
                worry -> worry.add(BigInteger.valueOf(1)),
                item -> item.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)
        );
        var monkey2 = new Monkey(
                List.of(78, 84, 96),
                worry -> worry.add(BigInteger.valueOf(8)),
                item -> item.mod(BigInteger.valueOf(19)).equals(BigInteger.ZERO)
        );
        var monkey3 = new Monkey(
                List.of(97, 86),
                worry -> worry.multiply(worry),
                item -> item.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)
        );
        var monkey4 = new Monkey(
                List.of(50),
                worry -> worry.add(BigInteger.valueOf(6)),
                item -> item.mod(BigInteger.valueOf(13)).equals(BigInteger.ZERO)
        );
        var monkey5 = new Monkey(
                List.of(73, 65, 69, 65, 51),
                worry -> worry.multiply(BigInteger.valueOf(17)),
                item -> item.mod(BigInteger.valueOf(11)).equals(BigInteger.ZERO)
        );
        var monkey6 = new Monkey(
                List.of(69, 82, 97, 93, 82, 84, 58, 63),
                worry -> worry.add(BigInteger.valueOf(5)),
                item -> item.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO)
        );
        var monkey7 = new Monkey(
                List.of(81, 78, 82, 76, 79, 80),
                worry -> worry.add(BigInteger.valueOf(3)),
                item -> item.mod(BigInteger.valueOf(17)).equals(BigInteger.ZERO)
        );

        monkey0.target = monkey6;
        monkey0.alternateTarget = monkey2;
        monkey1.target = monkey2;
        monkey1.alternateTarget = monkey0;
        monkey2.target = monkey6;
        monkey2.alternateTarget = monkey5;
        monkey3.target = monkey1;
        monkey3.alternateTarget = monkey0;
        monkey4.target = monkey3;
        monkey4.alternateTarget = monkey1;
        monkey5.target = monkey4;
        monkey5.alternateTarget = monkey7;
        monkey6.target = monkey5;
        monkey6.alternateTarget = monkey7;
        monkey7.target = monkey3;
        monkey7.alternateTarget = monkey4;

        return List.of(
                monkey0,
                monkey1,
                monkey2,
                monkey3,
                monkey4,
                monkey5,
                monkey6,
                monkey7
        );
    }

//    private static List<Monkey> testMonkeys() {
//        var monkey0 = new Monkey(
//                List.of(79, 98),
//                worry -> worry * 19,
//                item -> item % 23 == 0
//        );
//        var monkey1 = new Monkey(
//                List.of(54, 65, 75, 74),
//                worry -> worry + 6,
//                item -> item % 19 == 0
//        );
//        var monkey2 = new Monkey(
//                List.of(79, 60, 97),
//                worry -> worry * worry,
//                item -> item % 13 == 0
//        );
//        var monkey3 = new Monkey(
//                List.of(74),
//                worry -> worry + 3,
//                item -> item % 17 == 0
//        );
//
//        monkey0.target = monkey2;
//        monkey0.alternateTarget = monkey3;
//        monkey1.target = monkey2;
//        monkey1.alternateTarget = monkey0;
//        monkey2.target = monkey1;
//        monkey2.alternateTarget = monkey3;
//        monkey3.target = monkey0;
//        monkey3.alternateTarget = monkey1;
//
//        return List.of(
//                monkey0,
//                monkey1,
//                monkey2,
//                monkey3
//        );
//    }
}
