// Advent of Code 2024 - Day 05 Solution (AI3)
// Author: GitHub Copilot

package day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.AdventOfCodeSolver;

public class Day05_AI3 extends AdventOfCodeSolver {
    // Add fields for rules and updates
    // orderingRules: key -> list of pages that must come after key
    private Map<Integer, List<Integer>> orderingRules = new HashMap<>();
    private List<List<Integer>> updates = new ArrayList<>();

    @Override
    protected void parseInput() {
        orderingRules.clear();
        updates.clear();
        int i = 0;
        // Parse ordering rules into a HashMap: key -> list of pages that must come
        // after key
        while (i < inputLines.size() && inputLines.get(i).contains("|")) {
            String[] parts = inputLines.get(i).split("\\|");
            int a = Integer.parseInt(parts[0].trim());
            int b = Integer.parseInt(parts[1].trim());
            orderingRules.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            i++;
        }
        // Parse updates into a List<List<Integer>>
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

    // Checks if the update respects the ordering rules
    private boolean isCorrectOrder(List<Integer> update) {
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < update.size(); i++)
            pos.put(update.get(i), i);
        for (Map.Entry<Integer, List<Integer>> entry : orderingRules.entrySet()) {
            Integer a = entry.getKey();
            for (Integer b : entry.getValue()) {
                if (pos.containsKey(a) && pos.containsKey(b)) {
                    if (pos.get(a) >= pos.get(b))
                        return false;
                }
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
                if (a.equals(b))
                    return 0;
                // If a must come before b according to rules
                List<Integer> afterA = orderingRules.get(a);
                if (afterA != null && afterA.contains(b))
                    return -1;
                List<Integer> afterB = orderingRules.get(b);
                if (afterB != null && afterB.contains(a))
                    return 1;
                return Integer.compare(a, b); // fallback to natural order
            });
            int mid = sorted.get(sorted.size() / 2);
            sumOfMiddles += mid;
        }
        return sumOfMiddles;
    }

    public static void main(String[] args) {
        new Day05_AI3().setInputSections(2).run();
    }
}
