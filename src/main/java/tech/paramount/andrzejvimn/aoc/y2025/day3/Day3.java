package tech.paramount.andrzejvimn.aoc.y2025.day3;

import tech.paramount.andrzejvimn.aoc.InputReader;

public class Day3 {

    public void solve() {
        var lines = InputReader.readLines("2025/day3.txt");

        var result = lines.stream().map(PowerBank::parse).mapToInt(PowerBank::findHighest2Digit).sum();

        System.out.println("Total output joltage: " + result);

        var finalResult = lines.stream().map(PowerBank::parse).mapToLong(PowerBank::findHighest12Digit).sum();

        System.out.println("Total output joltage for 12-digit: " + finalResult);
    }

    private static class PowerBank {
        int[] batteries;

        static PowerBank parse(String line) {
            var batteries = new int[line.length()];
            for (int i = 0; i < line.length(); i++) {
                batteries[i] = Integer.parseInt(line.charAt(i) + "");
            }
            var powerBank = new PowerBank();
            powerBank.batteries = batteries;
            return powerBank;
        }

        int findHighest2Digit() {
            int dec = 0;
            int decPosition = 0;
            int position = 0;
            while (position < batteries.length - 1) {
                if (batteries[position] > dec) {
                    dec = batteries[position];
                    decPosition = position;
                }
                if (dec == 9 || position == batteries.length - 2) break;
                position++;
            }
            position = decPosition + 1;
            int lastDigit = batteries[position];
            while (position < batteries.length) {
                if (batteries[position] > lastDigit) {
                    lastDigit = batteries[position];
                }
                if (lastDigit == 9) break;
                position++;
            }
            return dec * 10 + lastDigit;
        }

        long findHighest12Digit() {
            long result = 0;
            int startIndex = 0;
            for (int digitPosition = 0; digitPosition < 12; digitPosition++) {
                int maxDigit = -1;
                int maxIndex = -1;
                for (int i = startIndex; i <= batteries.length - (12 - digitPosition); i++) {
                    if (batteries[i] > maxDigit) {
                        maxDigit = batteries[i];
                        maxIndex = i;
                    }
                    if (maxDigit == 9) break;
                }
                result = result * 10 + maxDigit;
                startIndex = maxIndex + 1;
            }
            return result;
        }

    }
}
