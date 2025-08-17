package day02;

import java.util.ArrayList;
import java.util.List;

import aoc.AdventOfCodeSolver;

public class Day02_RM extends AdventOfCodeSolver {
    public static void main(String[] args) {
        new Day02_AI().run();
    }

    private List<List<Integer>> reports = new ArrayList<>();

    @Override
    protected void parseInput() {
        // Parse the input into reports
        for (String line : inputLines) {
            // Each non-blank input line contains one report with space-separated levels
            List<Integer> report = new ArrayList<>();
            for (String levelText : line.trim().split("\\s+")) {
                report.add(Integer.parseInt(levelText));
            }
            reports.add(report);
        }
    }

    @Override
    public Object solvePart1() {

        int safeCount = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                safeCount++;
            }
        }
        return safeCount;
    }

    @Override
    public Object solvePart2() {

        int safeCount = 0;

        for (List<Integer> report : reports) {

            if (isSafe(report)) {
                safeCount++;
            } else {
                // Try removing each level once
                // until a safe report is found
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> modifiedReport = new ArrayList<>(report);
                    modifiedReport.remove(i);
                    if (isSafe(modifiedReport)) {
                        safeCount++;
                        break;
                    }
                }
            }
        }
        return safeCount;
    }

    private boolean isSafe(List<Integer> report) {

        if (report.size() < 2)
            return false;

        boolean increasing = false, decreasing = false;

        for (int i = 1; i < report.size(); i++) {

            int diff = report.get(i) - report.get(i - 1);

            // Must differ by at least 1 and at most 3
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Must be consistently increasing or decreasing
            if (diff < 0 && increasing) {
                return false;
            }
            if (diff > 0 && decreasing) {
                return false;
            }

            if (diff > 0) {
                increasing = true;
            } else {
                decreasing = true;
            }
        }

        // If no errors have been found, the report is safe
        return true;
    }
}
