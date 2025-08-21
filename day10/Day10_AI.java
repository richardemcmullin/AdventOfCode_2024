// Advent of Code 2024 - Day 10 Solution (AI)
// Author: GitHub Copilot

package day10;

import aoc.AdventOfCodeSolver;

public class Day10_AI extends AdventOfCodeSolver {
    private int[][] heights;
    private int rows, cols;
    private java.util.List<int[]> trailheads = new java.util.ArrayList<>();

    @Override
    protected void parseInput() {
        rows = inputLines.size();
        cols = inputLines.get(0).length();
        heights = new int[rows][cols];
        trailheads.clear();
        for (int r = 0; r < rows; r++) {
            String line = inputLines.get(r);
            for (int c = 0; c < cols; c++) {
                int h = line.charAt(c) - '0';
                heights[r][c] = h;
                if (h == 0)
                    trailheads.add(new int[] { r, c });
            }
        }
    }

    @Override
    public Object solvePart1() {
        int sum = 0;
        for (int[] start : trailheads) {
            sum += countReachableNines(start[0], start[1]);
        }
        return sum;
    }

    private int countReachableNines(int sr, int sc) {
        java.util.Set<String> visited = new java.util.HashSet<>();
        java.util.Set<String> foundNines = new java.util.HashSet<>();
        java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();
        queue.add(new int[] { sr, sc });
        visited.add(sr + "," + sc);
        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int r = pos[0], c = pos[1], h = heights[r][c];
            if (h == 9)
                foundNines.add(r + "," + c);
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                    continue;
                if (visited.contains(nr + "," + nc))
                    continue;
                if (heights[nr][nc] == h + 1) {
                    queue.add(new int[] { nr, nc });
                    visited.add(nr + "," + nc);
                }
            }
        }
        return foundNines.size();
    }

    @Override
    public Object solvePart2() {
        int sum = 0;
        for (int[] start : trailheads) {
            sum += countDistinctTrails(start[0], start[1]);
        }
        return sum;
    }

    private int countDistinctTrails(int sr, int sc) {
        // Memoization: memo[r][c][h] = number of trails from (r,c) at height h
        int[][][] memo = new int[rows][cols][10];
        return dfs(sr, sc, heights[sr][sc], memo);
    }

    private int dfs(int r, int c, int h, int[][][] memo) {
        if (h == 9)
            return 1;
        if (memo[r][c][h] != 0)
            return memo[r][c][h];
        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };
        int total = 0;
        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                continue;
            if (heights[nr][nc] == h + 1) {
                total += dfs(nr, nc, h + 1, memo);
            }
        }
        memo[r][c][h] = total;
        return total;
    }

    public static void main(String[] args) {
        new Day10_AI().run();
    }
}
