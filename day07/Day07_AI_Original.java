// Advent of Code 2024 - Day 07 Solution (AI)
// Author: GitHub Copilot

package day07;

import aoc.AdventOfCodeSolver;

public class Day07_AI_Original extends AdventOfCodeSolver {

    private static class CalibrationEquation {
        long testValue;
        java.util.List<Long> numbers = new java.util.ArrayList<>();
    }

    java.util.List<CalibrationEquation> calibrationEquations = new java.util.ArrayList<>();

    @Override
    protected void parseInput() {
        calibrationEquations.clear();
        for (String line : inputLines) {
            String[] s = line.split(":");
            long testValue = Long.parseLong(s[0].trim());
            String[] nums = s[1].trim().split(" ");
            CalibrationEquation eq = new CalibrationEquation();
            eq.testValue = testValue;
            for (String num : nums) {
                eq.numbers.add(Long.parseLong(num.trim()));
            }
            calibrationEquations.add(eq);
        }
    }

    @Override
    public Object solvePart1() {
        long totalCalibrationResult = 0;
        for (CalibrationEquation eq : calibrationEquations) {
            if (canMakeTestValue(eq.numbers, eq.testValue)) {
                totalCalibrationResult += eq.testValue;
            }
        }
        return totalCalibrationResult;
    }

    // Recursively check if numbers can produce target using + and * only,
    // left-to-right
    private boolean canMakeTestValue(java.util.List<Long> nums, long target) {
        if (nums.size() == 1)
            return nums.get(0) == target;
        for (int i = 1; i < nums.size(); i++) {
            // Try +
            java.util.List<Long> plus = new java.util.ArrayList<>();
            plus.add(nums.get(0) + nums.get(i));
            for (int j = 1; j < nums.size(); j++)
                if (j != i)
                    plus.add(nums.get(j));
            if (canMakeTestValue(plus, target))
                return true;
            // Try *
            java.util.List<Long> mult = new java.util.ArrayList<>();
            mult.add(nums.get(0) * nums.get(i));
            for (int j = 1; j < nums.size(); j++)
                if (j != i)
                    mult.add(nums.get(j));
            if (canMakeTestValue(mult, target))
                return true;
        }
        return false;
    }

    @Override
    public Object solvePart2() {
        long totalCalibrationResult = 0;
        for (CalibrationEquation eq : calibrationEquations) {
            if (canMakeTestValueWithConcat(eq.numbers, eq.testValue)) {
                totalCalibrationResult += eq.testValue;
            }
        }
        return totalCalibrationResult;
    }

    // Recursively check if numbers can produce target using +, *, and concat,
    // left-to-right
    private boolean canMakeTestValueWithConcat(java.util.List<Long> nums, long target) {
        if (nums.size() == 1)
            return nums.get(0) == target;
        for (int i = 1; i < nums.size(); i++) {
            // Try +
            java.util.List<Long> plus = new java.util.ArrayList<>();
            plus.add(nums.get(0) + nums.get(i));
            for (int j = 1; j < nums.size(); j++)
                if (j != i)
                    plus.add(nums.get(j));
            if (canMakeTestValueWithConcat(plus, target))
                return true;
            // Try *
            java.util.List<Long> mult = new java.util.ArrayList<>();
            mult.add(nums.get(0) * nums.get(i));
            for (int j = 1; j < nums.size(); j++)
                if (j != i)
                    mult.add(nums.get(j));
            if (canMakeTestValueWithConcat(mult, target))
                return true;
            // Try concat
            java.util.List<Long> concat = new java.util.ArrayList<>();
            concat.add(Long.parseLong(String.valueOf(nums.get(0)) + String.valueOf(nums.get(i))));
            for (int j = 1; j < nums.size(); j++)
                if (j != i)
                    concat.add(nums.get(j));
            if (canMakeTestValueWithConcat(concat, target))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new Day07_AI_Original().run();
    }
}
