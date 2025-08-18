// Advent of Code 2024 - Day 07 Solution (RM)
// Author: RM (with copilot autofill)

package day07;

import java.util.ArrayList;
import java.util.List;

import aoc.AdventOfCodeSolver;

public class Day07_RM extends AdventOfCodeSolver {
    // Add fields for input parsing and solution logic

    private class CalibrationEquation {
        long testValue = 0;
        List<Long> numbers = new ArrayList<>();
    }

    List<CalibrationEquation> calibrationEquations = new ArrayList<>();

    @Override
    protected void parseInput() {

        for (String line : inputLines) {
            // ...parse each line into CalibrationEquation objects...
            String[] s = line.split(":");

            long testValue = Long.valueOf(s[0].trim());

            String[] nums = s[1].trim().split(" ");

            CalibrationEquation eq = new CalibrationEquation();
            eq.testValue = testValue;
            for (String num : nums) {
                eq.numbers.add(Long.valueOf(num.trim()));
            }

            calibrationEquations.add(eq);
        }
    }

    @Override
    public Object solvePart1() {

        long totalCalibrationResult = 0;

        for (CalibrationEquation eq : calibrationEquations) {

            // Process each equation
            if (isEquationSolvable(eq, false)) {
                totalCalibrationResult += eq.testValue;
            }
        }
        return totalCalibrationResult;
    }

    @Override
    public Object solvePart2() {

        long totalCalibrationResult = 0;

        for (CalibrationEquation eq : calibrationEquations) {

            // Process each equation
            if (isEquationSolvable(eq, true)) {
                totalCalibrationResult += eq.testValue;
            }
        }
        return totalCalibrationResult;
    }

    private boolean isEquationSolvable(CalibrationEquation eq, boolean allowConcat) {

        if (eq.numbers.size() < 2) {
            return false;
        }

        // Get the first two numbers to use to try operations
        long a = eq.numbers.get(0);
        long b = eq.numbers.get(1);

        // If there are exactly two numbers remaining, try plus, multiply, and concat.
        if (eq.numbers.size() == 2) {
            if (allowConcat) {
                if (Long.valueOf(String.valueOf(a) + String.valueOf(b)) == eq.testValue) {
                    return true;
                }
            }
            return (a + b == eq.testValue) || (a * b == eq.testValue);
        }

        // Try sum branch
        CalibrationEquation nextEq = new CalibrationEquation();

        nextEq.testValue = eq.testValue;
        // Add all numbers except the first two
        nextEq.numbers.addAll(eq.numbers.subList(2, eq.numbers.size()));

        // Test sum branch
        nextEq.numbers.add(0, a + b);
        if (isEquationSolvable(nextEq, allowConcat)) {
            return true;
        }

        // Test product branch
        nextEq.numbers.remove(0); // Remove the sum result
        nextEq.numbers.add(0, a * b); // Replace with product
        if (isEquationSolvable(nextEq, allowConcat)) {
            return true;
        }

        // Try concat branch if allowed
        if (allowConcat) {
            nextEq.numbers.remove(0); // Remove the product result
            nextEq.numbers.add(0, Long.valueOf(String.valueOf(a) + String.valueOf(b)));
            if (isEquationSolvable(nextEq, allowConcat)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        new Day07_RM().run();
    }
}
