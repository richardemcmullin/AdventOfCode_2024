package day03;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.AdventOfCodeSolver;

public class Day03_AI extends AdventOfCodeSolver {
    public static void main(String[] args) {
        new Day03_AI().run();
    }

    private List<int[]> mulInstructions = new ArrayList<>();

    @Override
    protected void parseInput() {
        // Extract valid mul(X,Y) instructions using regex
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        for (String line : inputLines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                mulInstructions.add(new int[] { x, y });
            }
        }
    }

    @Override
    public Object solvePart1() {
        int sum = 0;
        for (int[] pair : mulInstructions) {
            sum += pair[0] * pair[1];
        }
        return sum;
    }

    @Override
    public Object solvePart2() {
        int sum = 0;
        boolean enabled = true;
        Pattern mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Pattern dontPattern = Pattern.compile("don't\\(\\)");

        for (String line : inputLines) {
            int idx = 0;
            while (idx < line.length()) {
                Matcher doMatcher = doPattern.matcher(line.substring(idx));
                Matcher dontMatcher = dontPattern.matcher(line.substring(idx));
                Matcher mulMatcher = mulPattern.matcher(line.substring(idx));

                int nextDo = doMatcher.find() ? doMatcher.start() : Integer.MAX_VALUE;
                int nextDont = dontMatcher.find() ? dontMatcher.start() : Integer.MAX_VALUE;
                int nextMul = mulMatcher.find() ? mulMatcher.start() : Integer.MAX_VALUE;

                int next = Math.min(nextDo, Math.min(nextDont, nextMul));
                if (next == Integer.MAX_VALUE)
                    break;

                if (next == nextDo) {
                    enabled = true;
                    idx += doMatcher.end();
                } else if (next == nextDont) {
                    enabled = false;
                    idx += dontMatcher.end();
                } else if (next == nextMul) {
                    if (enabled) {
                        int x = Integer.parseInt(mulMatcher.group(1));
                        int y = Integer.parseInt(mulMatcher.group(2));
                        sum += x * y;
                    }
                    idx += mulMatcher.end();
                }
            }
        }
        return sum;
    }
}
