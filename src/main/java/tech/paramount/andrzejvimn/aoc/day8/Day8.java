package tech.paramount.andrzejvimn.aoc.day8;

import tech.paramount.andrzejvimn.aoc.InputReader;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day8 {

    private static final Pattern NODE_PATTERN = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

    public void solve() {
        var lines = InputReader.readLines("day8.txt");

        var nodes = lines.stream().skip(2).map(this::parseNode).collect(Collectors.toMap(Node::label, Function.identity()));
        var instructions = lines.get(0);

        var currentNode = nodes.get("AAA");
        var steps = 0;
        var instructionIndex = 0;

        while(!currentNode.label.equals("ZZZ")) {
            var instruction = instructions.charAt(instructionIndex);
            if (instruction == 'R') {
                currentNode = nodes.get(currentNode.right);
            } else if (instruction == 'L') {
                currentNode = nodes.get(currentNode.left);
            } else {
                throw new RuntimeException("Unknown instruction: " + instruction);
            }
            steps++;
            instructionIndex++;
            if (instructionIndex == instructions.length()) {
                instructionIndex = 0;
            }
        }

        System.out.println("Steps until ZZZ: " + steps);

        var aNodes = nodes.keySet().stream().filter(key -> key.endsWith("A")).toList();

        var allGhostSteps = aNodes.stream().map(node -> {
            var aNode = nodes.get(node);
            var ghostSteps = 0;
            var ghostInstructionIndex = 0;

            while(!aNode.label.endsWith("Z")) {
                var instruction = instructions.charAt(ghostInstructionIndex);
                if (instruction == 'R') {
                    aNode = nodes.get(aNode.right);
                } else if (instruction == 'L') {
                    aNode = nodes.get(aNode.left);
                } else {
                    throw new RuntimeException("Unknown instruction: " + instruction);
                }
                ghostSteps++;
                ghostInstructionIndex++;
                if (ghostInstructionIndex == instructions.length()) {
                    ghostInstructionIndex = 0;
                }
            }
            return ghostSteps;
        }).toList();

        var steps2 = LCMCalculator.findLCM(allGhostSteps);


        System.out.println("Steps until all match **Z: " + steps2);
    }

    private Node parseNode(String line) {
        var matcher = NODE_PATTERN.matcher(line);
        if(matcher.matches()) {
            var label = matcher.group(1);
            var left = matcher.group(2);
            var right = matcher.group(3);
            return new Node(label, left, right);
        }
        throw new RuntimeException("Failed to parse node: " + line);
    }

    record Node(String label, String left, String right) {}

    private class LCMCalculator {

        private static long gcd(long a, long b) {
            while (b != 0) {
                long temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }

        private static long lcm(long a, long b) {
            return (a * b) / gcd(a, b);
        }

        private static long findLCM(List<Integer> numbers) {
            long result = 1;
            for (long number : numbers) {
                result = lcm(result, number);
            }
            return result;
        }
    }
}
