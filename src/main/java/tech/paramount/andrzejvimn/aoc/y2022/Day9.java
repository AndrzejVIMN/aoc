package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day9 {

    private static final String INPUT_FILE = "2022/day9-input.txt";

    public static void solve() {
        var input = Reader.readLines(INPUT_FILE);
        var rope = new Rope(2);
        input.forEach(line -> process(rope, line));
        System.out.println(rope.tailTrail.stream().distinct().count());
    }
    
    public static void solve2() {
        var input = Reader.readLines(INPUT_FILE);
        var rope = new Rope(10);
        input.forEach(line -> process(rope, line));
        System.out.println(rope.tailTrail.stream().distinct().count());
    }

    private static void process(Rope rope, String line) {
        String[] parts = line.split(" ");
        String command = parts[0];
        int count = Integer.parseInt(parts[1]);
        switch (command) {
            case "U" -> repeat(count, rope::up);
            case "D" -> repeat(count, rope::down);
            case "L" -> repeat(count, rope::left);
            case "R" -> repeat(count, rope::right);
            default -> throw new RuntimeException("Unknown command: " + command);
        }
    }

    private static void repeat(int count, Runnable action) {
        for (int i=0 ; i<count ; i++) {
            action.run();
        }
    }

    private static class Rope {

        private final List<Knot> knots;

        private final Knot head;

        private final Knot tail;

        private final List<Point> tailTrail = new ArrayList<>();

        private Rope(int knotsCount) {
            var start = new Point(0, 0);

            knots = new ArrayList<>();
            head = new Knot(start, null);
            knots.add(head);
            for(int i=1 ; i<knotsCount ; i++) {
                knots.add(new Knot(start, knots.get(i-1)));
            }
            tail = knots.get(knots.size() - 1);
            tailTrail.add(start);
        }

        void up() {
            head.move(new Point(head.position.x, head.position.y + 1));
            follow();
        }

        void down() {
            head.move(new Point(head.position.x, head.position.y - 1));
            follow();
        }

        void left() {
            head.move(new Point(head.position.x - 1, head.position.y));
            follow();
        }

        void right() {
            head.move(new Point(head.position.x + 1, head.position.y));
            follow();
        }

        void follow() {
            for (int i=1 ; i<knots.size() ; i++) {
                knots.get(i).follow();
            }
            tailTrail.add(tail.position);
        }
    }

    private static class Knot {
        private Point position;
        private final Knot previousKnot;

        private Knot(Point position, Knot previousKnot) {
            this.position = position;
            this.previousKnot = previousKnot;
        }

        private void move(Point position) {
            this.position = position;
        }

        private void follow() {
            if (Math.abs(previousKnot.position.x - this.position.x) <= 1 && Math.abs(previousKnot.position.y - this.position.y) <= 1) {
                return;
            }
            if (previousKnot.position.y == this.position.y) {
                if (previousKnot.position.x < this.position.x) {
                    move(new Point(this.position.x - 1, this.position.y));
                } else {
                    move(new Point(this.position.x + 1, this.position.y));
                }
            } else if (previousKnot.position.x == this.position.x) {
                if (previousKnot.position.y < this.position.y) {
                    move(new Point(this.position.x, this.position.y - 1));
                } else {
                    move(new Point(this.position.x, this.position.y + 1));
                }
            } else {
                move(new Point(
                        previousKnot.position.x < this.position.x ? this.position.x - 1 : this.position.x + 1,
                        previousKnot.position.y < this.position.y ? this.position.y - 1 : this.position.y + 1
                ));
            }
        }
    }

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y =y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(%d, %d)".formatted(x, y);
        }
    }
}
