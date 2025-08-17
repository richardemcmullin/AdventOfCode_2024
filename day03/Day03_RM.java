package day03;

import aoc.AdventOfCodeSolver;

public class Day03_RM extends AdventOfCodeSolver {

    private class MulInstruction {
        int x;
        int y;

        MulInstruction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getResult() {
            return x * y;
        }
    }

    public static void main(String[] args) {
        new Day03_RM().run();
    }

    @Override
    protected void parseInput() {
        // The puzzle input does not need to be parsed for this puzzle
    }

    @Override
    public Object solvePart1() {

        int sum = 0;

        for (String inputLine : inputLines) {

            int idx = 0;
            while (idx < inputLine.length()) {

                if (inputLine.startsWith("mul(", idx)) {

                    MulInstruction mulInstruction = parseMulInstruction(inputLine, idx);
                    if (mulInstruction != null) {
                        sum += mulInstruction.getResult();
                    }
                }

                idx++;
            }
        }

        return sum;
    }

    @Override
    public Object solvePart2() {

        int sum = 0;
        boolean enabled = true;

        for (String inputLine : inputLines) {

            int idx = 0;
            while (idx < inputLine.length()) {

                if (inputLine.startsWith("do()", idx)) {
                    enabled = true;
                    idx += 4;
                    continue;
                }

                if (inputLine.startsWith("don't()", idx)) {
                    enabled = false;
                    idx += 7;
                    continue;
                }

                // Dont process mul instructions if not enabled
                if (enabled && inputLine.startsWith("mul(", idx)) {

                    MulInstruction mulInstruction = parseMulInstruction(inputLine, idx);
                    if (mulInstruction != null) {
                        sum += mulInstruction.getResult();
                    }
                }

                idx++;
            }
        }
        return sum;
    }

    private MulInstruction parseMulInstruction(String line, int startIdx) {

        int closeIdx = line.indexOf(')', startIdx + 4);
        if (closeIdx == -1) {
            // No closing parenthesis found
            return null;
        }

        String inside = line.substring(startIdx + 4, closeIdx);
        int commaIdx = inside.indexOf(',');
        if (commaIdx == -1) {
            // No comma found
            return null;
        }

        String xStr = inside.substring(0, commaIdx).trim();
        if (xStr.length() > 3 || !isDigits(xStr)) {
            return null;
        }

        String yStr = inside.substring(commaIdx + 1).trim();
        if (yStr.length() > 3 || !isDigits(yStr)) {
            return null;
        }

        int x = Integer.parseInt(xStr);
        int y = Integer.parseInt(yStr);
        return new MulInstruction(x, y);
    }

    private boolean isDigits(String str) {

        if (str.isEmpty()) {
            return false;
        }

        // Cannot start with a zero unless it is "0"
        if (str.length() > 1 && str.charAt(0) == '0') {
            return false;
        }

        // All digits must be between '0' and '9'
        for (char c : str.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }
}
