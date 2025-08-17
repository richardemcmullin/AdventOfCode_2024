package day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aoc.AdventOfCodeSolver;

public class Day01 extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day01().run();
    }

    private List<Integer> leftList = new ArrayList<>();
    private List<Integer> rightList = new ArrayList<>();

    @Override
    protected void parseInput() {
        for (String line : inputLines) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 2) {
                leftList.add(Integer.parseInt(parts[0]));
                rightList.add(Integer.parseInt(parts[1]));
            }
        }
    }

    @Override
    public Object solvePart1() {

        List<Integer> leftSorted = new ArrayList<>(leftList);
        List<Integer> rightSorted = new ArrayList<>(rightList);

        Collections.sort(leftSorted);
        Collections.sort(rightSorted);

        int totalDistance = 0;
        for (int i = 0; i < leftSorted.size(); i++) {
            totalDistance += Math.abs(leftSorted.get(i) - rightSorted.get(i));
        }

        return totalDistance;
    }

    @Override
    public Object solvePart2() {
        // Count occurrences of each number in rightList
        java.util.Map<Integer, Integer> rightCount = new java.util.HashMap<>();
        for (int num : rightList) {
            rightCount.put(num, rightCount.getOrDefault(num, 0) + 1);
        }
        int similarityScore = 0;
        for (int num : leftList) {
            similarityScore += num * rightCount.getOrDefault(num, 0);
        }
        return similarityScore;
    }
}
