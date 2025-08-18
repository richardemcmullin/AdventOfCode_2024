// Advent of Code 2024 - Day 05 Solution (AI)
// Author: GitHub Copilot

package day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.AdventOfCodeSolver;

public class Day05_AI2 extends AdventOfCodeSolver {

    private List<int[]> orderingRules = new ArrayList<>();
    private List<List<Integer>> updates = new ArrayList<>();

    @Override
    protected void parseInput() {
        orderingRules.clear();
        updates.clear();
        int i = 0;
        // Parse ordering rules
        while (i < inputLines.size() && inputLines.get(i).contains("|")) {
            String[] parts = inputLines.get(i).split("\\|");
            orderingRules.add(new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) });
            i++;
        }
        // Parse updates
        for (; i < inputLines.size(); i++) {
            String line = inputLines.get(i).trim();
            if (line.isEmpty())
                continue;
            List<Integer> update = new ArrayList<>();
            for (String num : line.split(",")) {
                update.add(Integer.parseInt(num.trim()));
            }
            updates.add(update);
        }
    }

    @Override
    public Object solvePart1() {
        int sumOfMiddles = 0;
        for (List<Integer> update : updates) {
            if (isCorrectOrder(update)) {
                int mid = update.get(update.size() / 2);
                sumOfMiddles += mid;
            }
        }
        return sumOfMiddles;
    }

    private boolean isCorrectOrder(List<Integer> update) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < update.size(); i++)
            pos.put(update.get(i), i);
        for (int[] rule : orderingRules) {
            Integer a = rule[0], b = rule[1];
            if (pos.containsKey(a) && pos.containsKey(b)) {
                if (pos.get(a) >= pos.get(b))
                    return false;
            }
        }
        return true;
    }

    @Override
    public Object solvePart2() {
        int sumOfMiddles = 0;
        for (List<Integer> update : updates) {

            List<Integer> sorted = new ArrayList<>(update);

            java.util.Collections.sort(sorted, (a, b) -> {
                for (int[] rule : orderingRules) {
                    if (rule[0] == a && rule[1] == b)
                        return -1;
                    if (rule[0] == b && rule[1] == a)
                        return 1;
                }
                return 0; // keep the input order
            });

            int mid = sorted.get(sorted.size() / 2);
            sumOfMiddles += mid;
        }
        return sumOfMiddles;
    }

    public static void main(String[] args) {
        new Day05_AI().setInputSections(2).run();
    }
}
