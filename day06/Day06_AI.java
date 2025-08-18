// Advent of Code 2024 - Day 06 Solution (AI)
// Author: GitHub Copilot

package day06;

import aoc.AdventOfCodeSolver;

public class Day06_AI extends AdventOfCodeSolver {
    private char[][] map;
    private int rows, cols;
    private int guardRow, guardCol, guardDir;
    // Directions: 0=up, 1=right, 2=down, 3=left
    private final int[] dRow = { -1, 0, 1, 0 };
    private final int[] dCol = { 0, 1, 0, -1 };

    @Override
    protected void parseInput() {
        rows = inputLines.size();
        cols = inputLines.get(0).length();
        map = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = inputLines.get(r);
            for (int c = 0; c < cols; c++) {
                char ch = line.charAt(c);
                map[r][c] = ch;
                if (ch == '^') {
                    guardRow = r;
                    guardCol = c;
                    guardDir = 0;
                }
                if (ch == '>') {
                    guardRow = r;
                    guardCol = c;
                    guardDir = 1;
                }
                if (ch == 'v') {
                    guardRow = r;
                    guardCol = c;
                    guardDir = 2;
                }
                if (ch == '<') {
                    guardRow = r;
                    guardCol = c;
                    guardDir = 3;
                }
            }
        }
    }

    @Override
    public Object solvePart1() {
        java.util.Set<String> visited = new java.util.HashSet<>();
        int r = guardRow, c = guardCol, dir = guardDir;
        visited.add(r + "," + c);
        while (true) {
            int nr = r + dRow[dir];
            int nc = c + dCol[dir];
            // Check if out of bounds
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                break;
            if (map[nr][nc] == '#') {
                dir = (dir + 1) % 4; // turn right
                continue;
            }
            r = nr;
            c = nc;
            visited.add(r + "," + c);
        }
        return visited.size();
    }

    @Override
    public Object solvePart2() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Skip walls and guard's starting position
                if (map[r][c] == '#' || (r == guardRow && c == guardCol))
                    continue;
                if (wouldLoop(r, c))
                    count++;
            }
        }
        return count;
    }

    // Simulate with a new obstruction at (blockR, blockC), return true if guard
    // gets stuck in a loop
    private boolean wouldLoop(int blockR, int blockC) {
        java.util.Set<String> seen = new java.util.HashSet<>();
        int r = guardRow, c = guardCol, dir = guardDir;
        while (true) {
            String state = r + "," + c + "," + dir;
            if (seen.contains(state))
                return true; // loop detected
            seen.add(state);
            int nr = r + dRow[dir];
            int nc = c + dCol[dir];
            // Check if out of bounds
            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                return false;
            if ((nr == blockR && nc == blockC) || map[nr][nc] == '#') {
                dir = (dir + 1) % 4; // turn right
                continue;
            }
            r = nr;
            c = nc;
        }
    }

    public static void main(String[] args) {
        new Day06_AI().run();
    }
}
