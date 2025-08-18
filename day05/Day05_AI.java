// Advent of Code 2024 - Day 05 Solution (AI)
// Author: GitHub Copilot

package day05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.AdventOfCodeSolver;

public class Day05_AI extends AdventOfCodeSolver {

    public Day05_AI() {
    }

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
            if (!isCorrectOrder(update)) {
                List<Integer> sorted = topologicalSort(update);
                int mid = sorted.get(sorted.size() / 2);
                sumOfMiddles += mid;
            }
        }
        return sumOfMiddles;
    }

    // DFS-based topological sort for a single update using only relevant rules
    private List<Integer> topologicalSort(List<Integer> update) {
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (Integer page : update) {
            adj.put(page, new ArrayList<>());
        }
        for (int[] rule : orderingRules) {
            Integer a = rule[0], b = rule[1];
            if (adj.containsKey(a) && adj.containsKey(b)) {
                adj.get(a).add(b);
            }
        }
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> tempMark = new HashSet<>();
        List<Integer> nodes = new ArrayList<>(update);
        nodes.sort(null); // For deterministic order
        for (Integer node : nodes) {
            if (!visited.contains(node)) {
                if (!dfs(node, adj, visited, tempMark, result)) {
                    throw new RuntimeException("Cycle detected in rules");
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    private boolean dfs(Integer node, Map<Integer, List<Integer>> adj, Set<Integer> visited, Set<Integer> tempMark,
            List<Integer> result) {
        if (tempMark.contains(node))
            return false; // cycle
        if (visited.contains(node))
            return true;
        tempMark.add(node);
        for (Integer neighbor : adj.get(node)) {
            if (!dfs(neighbor, adj, visited, tempMark, result))
                return false;
        }
        tempMark.remove(node);
        visited.add(node);
        result.add(node);
        return true;
    }

    public static void main(String[] args) {
        new Day05_AI().setInputSections(2).run();
    }
}
