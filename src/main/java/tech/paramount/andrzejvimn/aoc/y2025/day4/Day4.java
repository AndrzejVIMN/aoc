package tech.paramount.andrzejvimn.aoc.y2025.day4;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.List;

public class Day4 {

    public void solve() {
        var lines = InputReader.readLines("2025/day4.txt");

        var grid = parse(lines);

        var result = 0;

        for (int i = 0 ; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '@') {
                    int count = countAdjacentAtSigns(grid, i, j);
                    if (count < 4) result++;
                }
            }
        }

        System.out.println("Rolls accessible by forklift: " + result);

        var removedRolls = 0;
        var justRemoved = 0;

        do {
            justRemoved = 0;
            for (int i = 0 ; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == '@') {
                        int count = countAdjacentAtSigns(grid, i, j);
                        if (count < 4) {
                            grid[i][j] = '.';
                            justRemoved++;
                        }
                    }
                }
            }
            removedRolls += justRemoved;
        } while (justRemoved > 0);

        System.out.println("Removed rolls: " + removedRolls);
    }

    private char[][] parse(List<String> lines) {
        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    private int countAdjacentAtSigns(char[][] grid, int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length) {
                    if (grid[newRow][newCol] == '@') {
                        count++;
                    }
                }
            }
        }
        return count;
    }

}
