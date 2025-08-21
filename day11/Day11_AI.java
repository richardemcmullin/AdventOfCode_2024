package day11;

import aoc.AdventOfCodeSolver;

public class Day11_AI extends AdventOfCodeSolver {
    // List to hold the current arrangement of stones
    private java.util.List<String> stones;

    public static void main(String[] args) {
        new Day11_AI().run();
    }

    @Override
    protected void parseInput() {
        // Parse input: each line is a sequence of numbers separated by spaces
        stones = new java.util.ArrayList<>();
        for (String line : inputLines) {
            for (String s : line.trim().split("\\s+")) {
                if (!s.isEmpty())
                    stones.add(s);
            }
        }
    }

    @Override
    public Object solvePart1() {
        // Simulate 25 blinks
        java.util.List<String> current = new java.util.ArrayList<>(stones);
        for (int blink = 0; blink < 25; blink++) {
            java.util.List<String> next = new java.util.ArrayList<>();
            for (String stone : current) {
                if (stone.equals("0")) {
                    next.add("1");
                } else if (stone.length() % 2 == 0) {
                    // Split into two stones
                    int mid = stone.length() / 2;
                    String left = stone.substring(0, mid).replaceFirst("^0+", "");
                    String right = stone.substring(mid).replaceFirst("^0+", "");
                    next.add(left.isEmpty() ? "0" : left);
                    next.add(right.isEmpty() ? "0" : right);
                } else {
                    // Multiply by 2024
                    long val = Long.parseLong(stone);
                    next.add(Long.toString(val * 2024));
                }
            }
            current = next;
        }
        return current.size();
    }

    @Override
    public Object solvePart2() {
        // Map-based DP: Map stone value (String) to count
        java.util.Map<String, Long> current = new java.util.HashMap<>();
        for (String stone : stones) {
            current.put(stone, current.getOrDefault(stone, 0L) + 1);
        }
        for (int blink = 0; blink < 75; blink++) {
            java.util.Map<String, Long> next = new java.util.HashMap<>();
            for (java.util.Map.Entry<String, Long> entry : current.entrySet()) {
                String stone = entry.getKey();
                long count = entry.getValue();
                if (stone.equals("0")) {
                    next.put("1", next.getOrDefault("1", 0L) + count);
                } else if (stone.length() % 2 == 0) {
                    int mid = stone.length() / 2;
                    String left = stone.substring(0, mid).replaceFirst("^0+", "");
                    String right = stone.substring(mid).replaceFirst("^0+", "");
                    left = left.isEmpty() ? "0" : left;
                    right = right.isEmpty() ? "0" : right;
                    next.put(left, next.getOrDefault(left, 0L) + count);
                    next.put(right, next.getOrDefault(right, 0L) + count);
                } else {
                    long val = Long.parseLong(stone);
                    String newStone = Long.toString(val * 2024);
                    next.put(newStone, next.getOrDefault(newStone, 0L) + count);
                }
            }
            current = next;
        }
        long total = 0L;
        for (long cnt : current.values())
            total += cnt;
        return total;
    }
}
