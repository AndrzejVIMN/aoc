package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.Arrays;
import java.util.List;

public class Day8 {

    private static final String INPUT_FILE = "2022/day8-input.txt";

    public static void solve() {
        var input = Reader.readLines(INPUT_FILE);
        var forest = new Forest(input);
        int visibleTrees = 0;
        for (int i=0 ; i<forest.trees.length ; i++) {
            for (int j=0 ; j<forest.trees[0].length ; j++) {
                if (forest.isTreeVisible(i, j)) {
                    visibleTrees++;
                }
            }
        }
        System.out.println(visibleTrees);
    }
    
    public static void solve2() {
        var input = Reader.readLines(INPUT_FILE);
        var forest = new Forest(input);
        int maxScore = 0;
        for (int i=0 ; i<forest.trees.length ; i++) {
            for (int j=0 ; j<forest.trees[0].length ; j++) {
                int score = forest.scenicScore(i, j);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }
        System.out.println(maxScore);
    }

    private static class Forest {

        private final int[][] trees;

        private Forest(List<String> lines) {
            trees = new int[lines.size()][];
            for (int i=0 ; i<lines.size() ; i++) {
                trees[i] = Arrays.stream(lines.get(i).split("")).mapToInt(Integer::parseInt).toArray();
            }
        }

        private boolean isTreeVisible(int x, int y) {
            if (x == 0 ||  y == 0 || x == trees.length - 1 || y == trees[0].length - 1) {
                return true;
            }
            return isVisibleFromTop(x, y) || isVisibleFromBottom(x, y) || isVisibleFromLeft(x, y) || isVisibleFromRight(x, y);
        }

        private boolean isVisibleFromTop(int x, int y) {
            int height = trees[x][y];
            for (int i=0 ; i<x ; i++) { // not y because it was easier to read the table translated
                if (trees[i][y] >= height) {
                    return false;
                }
            }
            return true;
        }

        private boolean isVisibleFromBottom(int x, int y) {
            int height = trees[x][y];
            for (int i=x+1 ; i<trees.length ; i++) {
                if (trees[i][y] >= height) {
                    return false;
                }
            }
            return true;
        }

        private boolean isVisibleFromLeft(int x, int y) {
            int height = trees[x][y];
            for (int i=0 ; i<y ; i++) {
                if (trees[x][i] >= height) {
                    return false;
                }
            }
            return true;
        }

        private boolean isVisibleFromRight(int x, int y) {
            int height = trees[x][y];
            for (int i=y+1 ; i<trees[0].length ; i++) {
                if (trees[x][i] >= height) {
                    return false;
                }
            }
            return true;
        }

        private int viewDistanceTop(int x, int y) {
            int height = trees[x][y];
            if (x == 0) {
                return 0;
            }
            int view = 1;
            for (int i=x-1 ; i>0 ; i--) {
                if (trees[i][y] >= height) {
                    break;
                }
                view++;
            }
            return view;
        }

        private int viewDistanceBottom(int x, int y) {
            int height = trees[x][y];
            if (x == trees.length - 1) {
                return 0;
            }
            int view = 1;
            for (int i=x+1 ; i<trees.length - 1 ; i++) {
                if (trees[i][y] >= height) {
                    break;
                }
                view++;
            }
            return view;
        }

        private int viewDistanceLeft(int x, int y) {
            int height = trees[x][y];
            if (y == 0) {
                return 0;
            }
            int view = 1;
            for (int i=y-1 ; i>0 ; i--) {
                if (trees[x][i] >= height) {
                    break;
                }
                view++;
            }
            return view;
        }

        private int viewDistanceRight(int x, int y) {
            int height = trees[x][y];
            if (y == trees[0].length - 1) {
                return 0;
            }
            int view = 1;
            for (int i=y+1 ; i<trees[0].length - 1 ; i++) {
                if (trees[x][i] >= height) {
                    break;
                }
                view++;
            }
            return view;
        }

        private int scenicScore(int x, int y) {
            return viewDistanceTop(x, y) * viewDistanceBottom(x, y) * viewDistanceLeft(x, y) * viewDistanceRight(x, y);
        }
    }
}
