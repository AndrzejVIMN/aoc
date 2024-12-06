package tech.paramount.andrzejvimn.aoc.y2024.day4;

import tech.paramount.andrzejvimn.aoc.InputReader;

public class Day4 {

    public void solve() {
        var lines = InputReader.readLines("2024/day4.txt");

        var x = lines.get(0).length();
        var y = lines.size();

        var board = new char[x][y];

        for (int i=0; i<x; i++) {
            for (int j=0; j<y; j++) {
                board[i][j] = lines.get(j).charAt(i);
            }
        }

        var xmasCount = 0;

        for (int i=0; i<x; i++) {
            for (int j=0; j<y; j++) {
                if (board[i][j] == 'X') {
                    xmasCount += countXmas(board, i, j);
                }
            }
        }

        System.out.println("Part 1: " + xmasCount);

        var x_masCount = 0;

        for (int i=0; i<x; i++) {
            for (int j=0; j<y; j++) {
                if (board[i][j] == 'A') {
                    x_masCount += countX_mas(board, i, j);
                }
            }
        }

        System.out.println("Part 2: " + x_masCount);
    }

    private int countXmas(char[][] board, int x, int y) {
        var count = 0;

        try {
            // N
            if (board[x][y + 1] == 'M' && board[x][y + 2] == 'A' && board[x][y + 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // NE
            if (board[x + 1][y + 1] == 'M' && board[x + 2][y + 2] == 'A' && board[x + 3][y + 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // E
            if (board[x + 1][y] == 'M' && board[x + 2][y] == 'A' && board[x + 3][y] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // SE
            if (board[x + 1][y - 1] == 'M' && board[x + 2][y - 2] == 'A' && board[x + 3][y - 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // S
            if (board[x][y - 1] == 'M' && board[x][y - 2] == 'A' && board[x][y - 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // SW
            if (board[x - 1][y - 1] == 'M' && board[x - 2][y - 2] == 'A' && board[x - 3][y - 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // W
            if (board[x - 1][y] == 'M' && board[x - 2][y] == 'A' && board[x - 3][y] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // NW
            if (board[x - 1][y + 1] == 'M' && board[x - 2][y + 2] == 'A' && board[x - 3][y + 3] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        return count;
    }

    private int countX_mas(char[][] board, int x, int y) {
        var count = 0;

        try {
            // M M
            //  A
            // S S
            if (board[x - 1][y + 1] == 'M' && board[x + 1][y + 1] == 'M' && board[x + 1][y - 1] == 'S' && board[x - 1][y - 1] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // S M
            //  A
            // S M
            if (board[x - 1][y + 1] == 'S' && board[x + 1][y + 1] == 'M' && board[x + 1][y - 1] == 'M' && board[x - 1][y - 1] == 'S') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // S S
            //  A
            // M M
            if (board[x - 1][y + 1] == 'S' && board[x + 1][y + 1] == 'S' && board[x + 1][y - 1] == 'M' && board[x - 1][y - 1] == 'M') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            // M S
            //  A
            // M S
            if (board[x - 1][y + 1] == 'M' && board[x + 1][y + 1] == 'S' && board[x + 1][y - 1] == 'S' && board[x - 1][y - 1] == 'M') count++;
        } catch (ArrayIndexOutOfBoundsException e) {}

        return count;
    }

}
