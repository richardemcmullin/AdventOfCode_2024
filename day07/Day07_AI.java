// Advent of Code 2024 - Day 07 Solution (AI)
// Author: GitHub Copilot

package day07;

import aoc.AdventOfCodeSolver;

public class Day07_AI extends AdventOfCodeSolver {

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
        long a = nums.get(0);
        long b = nums.get(1);
        java.util.List<Long> rest = new java.util.ArrayList<>();
        rest.add(a + b);
        rest.addAll(nums.subList(2, nums.size()));
        if (canMakeTestValue(rest, target))
            return true;
        rest.clear();
        rest.add(a * b);
        rest.addAll(nums.subList(2, nums.size()));
        if (canMakeTestValue(rest, target))
            return true;
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
        long a = nums.get(0);
        long b = nums.get(1);
        java.util.List<Long> rest = new java.util.ArrayList<>();
        rest.add(a + b);
        rest.addAll(nums.subList(2, nums.size()));
        if (canMakeTestValueWithConcat(rest, target))
            return true;
        rest.clear();
        rest.add(a * b);
        rest.addAll(nums.subList(2, nums.size()));
        if (canMakeTestValueWithConcat(rest, target))
            return true;
        rest.clear();
        rest.add(Long.parseLong(String.valueOf(a) + String.valueOf(b)));
        rest.addAll(nums.subList(2, nums.size()));
        if (canMakeTestValueWithConcat(rest, target))
            return true;
        return false;
    }

    public static void main(String[] args) {
        new Day07_AI().run();
    }
}
