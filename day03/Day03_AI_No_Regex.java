package day03;

import aoc.AdventOfCodeSolver;

public class Day03_AI_No_Regex extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day03_AI().run();
    }

    private java.util.List<int[]> mulInstructions = new java.util.ArrayList<>();

    @Override
    protected void parseInput() {
        // Scan for valid mul(X,Y) instructions without regex
        for (String line : inputLines) {
            int idx = 0;
            while (idx < line.length()) {
                if (line.startsWith("mul(", idx)) {
                    int closeIdx = line.indexOf(')', idx + 4);
                    if (closeIdx != -1) {
                        String inside = line.substring(idx + 4, closeIdx);
                        int commaIdx = inside.indexOf(',');
                        if (commaIdx != -1) {
                            String xStr = inside.substring(0, commaIdx).trim();
                            String yStr = inside.substring(commaIdx + 1).trim();
                            if (isDigits(xStr) && isDigits(yStr)) {
                                int x = Integer.parseInt(xStr);
                                int y = Integer.parseInt(yStr);
                                mulInstructions.add(new int[] { x, y });
                            }
                        }
                        idx = closeIdx + 1;
                        continue;
                    }
                }
                idx++;
            }
        }
    }

    private boolean isDigits(String s) {
        if (s.length() == 0 || s.length() > 3)
            return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
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
        for (String line : inputLines) {
            int idx = 0;
            while (idx < line.length()) {
                // Check for do()
                if (line.startsWith("do()", idx)) {
                    enabled = true;
                    idx += 4;
                    continue;
                }
                // Check for don't()
                if (line.startsWith("don't()", idx)) {
                    enabled = false;
                    idx += 7;
                    continue;
                }
                // Check for mul(X,Y)
                if (line.startsWith("mul(", idx)) {
                    int closeIdx = line.indexOf(')', idx + 4);
                    if (closeIdx != -1) {
                        String inside = line.substring(idx + 4, closeIdx);
                        int commaIdx = inside.indexOf(',');
                        if (commaIdx != -1) {
                            String xStr = inside.substring(0, commaIdx).trim();
                            String yStr = inside.substring(commaIdx + 1).trim();
                            if (isDigits(xStr) && isDigits(yStr)) {
                                int x = Integer.parseInt(xStr);
                                int y = Integer.parseInt(yStr);
                                if (enabled) {
                                    sum += x * y;
                                }
                            }
                        }
                        idx = closeIdx + 1;
                        continue;
                    }
                }
                idx++;
            }
        }
        return sum;
    }
}
