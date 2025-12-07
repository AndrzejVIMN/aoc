package tech.paramount.andrzejvimn.aoc.y2025.day6;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day6 {

    public void solve() {
        var lines = InputReader.readLines("2025/day6.txt");

        var tasks = parseTasks(lines);

        var result = tasks.stream()
                .map(Task::solve)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Grand Total: " + result);

        var celaphodTasks = parseCelaphodTasks(lines);

        var celaphodResult = celaphodTasks.stream()
                .map(Task::solve)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println("Celaphod Grand Total: " + celaphodResult);
    }

    private static class Task {
        List<Long> numbers;
        String operation;

        public Task(List<Long> numbers, String operation) {
            this.numbers = numbers;
            this.operation = operation;
        }

        Long solve() {
            return switch (operation) {
                case "+" -> numbers.stream().mapToLong(Long::longValue).sum();
                case "*" -> numbers.stream().reduce(1L, (a, b) -> a * b);
                default -> throw new IllegalArgumentException("Unknown operation: " + operation);
            };
        }
    }

    private List<Task> parseCelaphodTasks(List<String> lines) {
        if (lines.isEmpty()) {
            return List.of();
        }

        var tasks = new ArrayList<Task>();
        int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);

        int col = 0;
        while (col < maxLength) {
            // Skip separator columns
            if (isEmptyColumn(lines, col)) {
                col++;
                continue;
            }

            // Collect numbers for this problem
            var numbers = new ArrayList<Long>();
            String operation = null;

            // Read columns until we hit a separator or end
            while (col < maxLength && !isEmptyColumn(lines, col)) {
                var columnChars = getColumn(lines, col);

                // Last character is operation (only take from first column of group)
                var lastChar = columnChars.get(columnChars.size() - 1);
                if (!lastChar.isBlank() && operation == null) {
                    operation = lastChar;
                }

                // Build number from digits (excluding operation row)
                var numberStr = columnChars.subList(0, columnChars.size() - 1).stream()
                        .filter(c -> !c.isBlank())
                        .reduce("", String::concat);

                if (!numberStr.isEmpty()) {
                    numbers.add(Long.parseLong(numberStr));
                }

                col++;
            }

            if (!numbers.isEmpty() && operation != null) {
                tasks.add(new Task(numbers, operation));
            }
        }

        return tasks;
    }

    private boolean isEmptyColumn(List<String> lines, int col) {
        return lines.stream().allMatch(line -> col >= line.length() || line.charAt(col) == ' ');
    }

    private List<String> getColumn(List<String> lines, int col) {
        return lines.stream()
                .map(line -> col < line.length() ? String.valueOf(line.charAt(col)) : " ")
                .toList();
    }


    private List<Task> parseTasks(List<String> lines) {
        var tasks = new ArrayList<Task>();

        var rows = lines.stream()
                .map(line -> line.trim().split("\\s+"))
                .toList();

        var cols = rows.get(0).length;

        for (int i = 0; i < cols; i++) {
            var numbers = new ArrayList<Long>();
            for (int j = 0; j < rows.size() - 1; j++) {
                numbers.add(Long.parseLong(rows.get(j)[i]));
            }
            var operation = rows.get(rows.size() - 1)[i];
            tasks.add(new Task(numbers, operation));
        }

        return tasks;
    }
}
