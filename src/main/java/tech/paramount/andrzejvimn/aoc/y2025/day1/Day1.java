package tech.paramount.andrzejvimn.aoc.y2025.day1;

import tech.paramount.andrzejvimn.aoc.InputReader;

public class Day1 {

    public void solve() {
        var lines = InputReader.readLines("2025/day1.txt");

        int position = 50;
        int password = 0;

        for (String line : lines) {
            Rotation rotation = Rotation.parse(line);
            position = rotation.turn(position);
            if (position == 0) password++;
        }

        System.out.println("Password: " + password);

        int position2 = 50;
        int password2 = 0;

        for (String line : lines) {
            Rotation rotation = Rotation.parse(line);
            int rounds = (int) (rotation.steps / 100);
            int actualSteps = (int) (rotation.steps % 100);
            password2 += rounds;
            if (position2 > 0 && rotation.direction == RotationDirection.LEFT && position2 - actualSteps < 0) {
                password2++;
            }
            if (position2 > 0 && rotation.direction == RotationDirection.RIGHT && position2 + actualSteps > 100) {
                password2++;
            }
            position2 = rotation.turn(position2);
            if (position2 == 0) password2++;
        }

        System.out.println("Password 2: " + password2);
    }

    private static class Rotation {
        private RotationDirection direction;
        private long steps;

        private Rotation(RotationDirection direction, long steps) {
            this.direction = direction;
            this.steps = steps;
        }

        static Rotation parse(String line) {
            char dirChar = line.charAt(0);
            long steps = Long.parseLong(line.substring(1));
            RotationDirection direction = (dirChar == 'L') ? RotationDirection.LEFT : RotationDirection.RIGHT;
            return new Rotation(direction, steps);
        }

        int turn(int startPosition) {
            int position = startPosition;
            int actualSteps = (int) (steps % 100);
            if (direction == RotationDirection.LEFT) {
                position -= actualSteps;
                if (position < 0) {
                    position += 100;
                }
            } else {
                position += actualSteps;
                if (position >= 100) {
                    position -= 100;
                }
            }
            return position;
        }
    }

    private enum RotationDirection {
        LEFT,
        RIGHT
    }

}
