// Advent of Code 2024 - Day 08 Solution (AI)
// Author: GitHub Copilot

package day08;

import aoc.AdventOfCodeSolver;

public class Day08_AI extends AdventOfCodeSolver {
    private int rows, cols;
    private java.util.Map<Character, java.util.List<int[]>> antennas = new java.util.HashMap<>();

    @Override
    protected void parseInput() {
        rows = inputLines.size();
        cols = inputLines.get(0).length();
        antennas.clear();
        for (int r = 0; r < rows; r++) {
            String line = inputLines.get(r);
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                if (Character.isLetterOrDigit(ch)) {
                    antennas.computeIfAbsent(ch, k -> new java.util.ArrayList<>()).add(new int[] { r, c });
                }
            }
        }
    }

    @Override
    public Object solvePart1() {
        java.util.Set<String> antinodes = new java.util.HashSet<>();
        for (char freq : antennas.keySet()) {
            java.util.List<int[]> list = antennas.get(freq);
            int n = list.size();
            for (int i = 0; i < n; i++) {
                int[] a = list.get(i);
                for (int j = 0; j < n; j++) {
                    if (i == j)
                        continue;
                    int[] b = list.get(j);
                    int dr = b[0] - a[0];
                    int dc = b[1] - a[1];
                    // Vertical
                    if (dc == 0 && dr != 0) {
                        if (dr % 3 == 0) {
                            int d = dr / 3;
                            int r1 = a[0] - d;
                            int r2 = b[0] + d;
                            if (inBounds(r1, a[1]))
                                antinodes.add(r1 + "," + a[1]);
                            if (inBounds(r2, b[1]))
                                antinodes.add(r2 + "," + b[1]);
                        }
                    }
                    // Horizontal
                    else if (dr == 0 && dc != 0) {
                        if (dc % 3 == 0) {
                            int d = dc / 3;
                            int c1 = a[1] - d;
                            int c2 = b[1] + d;
                            if (inBounds(a[0], c1))
                                antinodes.add(a[0] + "," + c1);
                            if (inBounds(b[0], c2))
                                antinodes.add(b[0] + "," + c2);
                        }
                    }
                    // Diagonal
                    else if (Math.abs(dr) == Math.abs(dc) && dr != 0) {
                        if (dr % 3 == 0 && dc % 3 == 0) {
                            int dR = dr / 3;
                            int dC = dc / 3;
                            int r1 = a[0] - dR;
                            int c1 = a[1] - dC;
                            int r2 = b[0] + dR;
                            int c2 = b[1] + dC;
                            if (inBounds(r1, c1))
                                antinodes.add(r1 + "," + c1);
                            if (inBounds(r2, c2))
                                antinodes.add(r2 + "," + c2);
                        }
                    }
                }
            }
        }
        return antinodes.size();
    }

    private int distance(int r1, int c1, int r2, int c2) {
        return Math.max(Math.abs(r1 - r2), Math.abs(c1 - c2));
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    @Override
    public Object solvePart2() {
        // ...solution for part 2...
        // RM NOTE: since the first part was not solvable by the AI, the second part was
        // not attempted.
        return null;
    }

    public static void main(String[] args) {
        new Day08_AI().run();
    }
}
