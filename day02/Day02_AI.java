package day02;

import java.util.ArrayList;
import java.util.List;

import aoc.AdventOfCodeSolver;

public class Day02_AI extends AdventOfCodeSolver {
    public static void main(String[] args) {
        new Day02_AI().run();
    }

    private List<List<Integer>> reports = new ArrayList<>();

    @Override
    protected void parseInput() {
        for (String line : inputLines) {
            if (!line.trim().isEmpty()) {
                List<Integer> levels = new ArrayList<>();
                for (String part : line.trim().split("\\s+")) {
                    levels.add(Integer.parseInt(part));
                }
                reports.add(levels);
            }
        }
    }

    @Override
    public Object solvePart1() {
        int safeCount = 0;
        for (List<Integer> levels : reports) {
            if (levels.size() < 2)
                continue;
            boolean increasing = true, decreasing = true;
            boolean validDiffs = true;
            for (int i = 1; i < levels.size(); i++) {
                int diff = levels.get(i) - levels.get(i - 1);
                if (diff < 1 || diff > 3)
                    increasing = false;
                if (diff > -1 || diff < -3)
                    decreasing = false;
                if (Math.abs(diff) < 1 || Math.abs(diff) > 3)
                    validDiffs = false;
            }
            if (validDiffs && (increasing || decreasing))
                safeCount++;
        }
        return safeCount;
    }

    @Override
    public Object solvePart2() {
        int safeCount = 0;
        for (List<Integer> levels : reports) {
            if (isSafe(levels)) {
                safeCount++;
            } else {
                // Try removing each level once
                boolean foundSafe = false;
                for (int i = 0; i < levels.size(); i++) {
                    List<Integer> modified = new ArrayList<>(levels);
                    modified.remove(i);
                    if (isSafe(modified)) {
                        safeCount++;
                        foundSafe = true;
                        break;
                    }
                }
            }
        }
        return safeCount;
    }

    private boolean isSafe(List<Integer> levels) {
        if (levels.size() < 2)
            return false;
        boolean increasing = true, decreasing = true;
        boolean validDiffs = true;
        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);
            if (diff < 1 || diff > 3)
                increasing = false;
            if (diff > -1 || diff < -3)
                decreasing = false;
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3)
                validDiffs = false;
        }
        return validDiffs && (increasing || decreasing);
    }
}
