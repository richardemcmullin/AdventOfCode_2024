package day12;

import aoc.AdventOfCodeSolver;

public class Day12_AI extends AdventOfCodeSolver {
    // Parsed garden map
    private char[][] garden;
    private int rows, cols;

    public static void main(String[] args) {
        new Day12_AI().run();
    }

    @Override
    protected void parseInput() {
        rows = inputLines.size();
        cols = inputLines.get(0).length();
        garden = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            garden[r] = inputLines.get(r).toCharArray();
        }
    }

    @Override
    public Object solvePart1() {
        boolean[][] visited = new boolean[rows][cols];
        int totalPrice = 0;
        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c]) {
                    char plant = garden[r][c];
                    // BFS to find region
                    java.util.Queue<int[]> queue = new java.util.LinkedList<>();
                    java.util.List<int[]> region = new java.util.ArrayList<>();
                    queue.add(new int[] { r, c });
                    visited[r][c] = true;
                    while (!queue.isEmpty()) {
                        int[] pos = queue.poll();
                        region.add(pos);
                        int pr = pos[0], pc = pos[1];
                        for (int d = 0; d < 4; d++) {
                            int nr = pr + dr[d], nc = pc + dc[d];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]
                                    && garden[nr][nc] == plant) {
                                queue.add(new int[] { nr, nc });
                                visited[nr][nc] = true;
                            }
                        }
                    }
                    // Calculate area and perimeter
                    int area = region.size();
                    int perimeter = 0;
                    for (int[] pos : region) {
                        int pr = pos[0], pc = pos[1];
                        for (int d = 0; d < 4; d++) {
                            int nr = pr + dr[d], nc = pc + dc[d];
                            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || garden[nr][nc] != plant) {
                                perimeter++;
                            }
                        }
                    }
                    totalPrice += area * perimeter;
                }
            }
        }
        return totalPrice;
    }

    @Override
    public Object solvePart2() {
        boolean[][] visited = new boolean[rows][cols];
        int totalPrice = 0;
        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!visited[r][c]) {
                    char plant = garden[r][c];
                    // BFS to find region
                    java.util.Queue<int[]> queue = new java.util.LinkedList<>();
                    java.util.List<int[]> region = new java.util.ArrayList<>();
                    queue.add(new int[] { r, c });
                    visited[r][c] = true;
                    while (!queue.isEmpty()) {
                        int[] pos = queue.poll();
                        region.add(pos);
                        int pr = pos[0], pc = pos[1];
                        for (int d = 0; d < 4; d++) {
                            int nr = pr + dr[d], nc = pc + dc[d];
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]
                                    && garden[nr][nc] == plant) {
                                queue.add(new int[] { nr, nc });
                                visited[nr][nc] = true;
                            }
                        }
                    }
                    // Calculate area and number of sides
                    int area = region.size();
                    int sides = countSides(region, plant);
                    totalPrice += area * sides;
                }
            }
        }
        return totalPrice;
    }

    // Helper to count maximal contiguous straight fence sides for a region
    private int countSides(java.util.List<int[]> region, char plant) {
        int rows = garden.length, cols = garden[0].length;
        boolean[][] inRegion = new boolean[rows][cols];
        for (int[] pos : region)
            inRegion[pos[0]][pos[1]] = true;
        int sides = 0;
        // Horizontal sides (top and bottom)
        for (int r = 0; r < rows; r++) {
            // Top
            int c = 0;
            while (c < cols) {
                // Find start of a run
                while (c < cols && !inRegion[r][c])
                    c++;
                int start = c;
                while (c < cols && inRegion[r][c]) {
                    // Is this a boundary?
                    if (r == 0 || !inRegion[r - 1][c])
                        break;
                    c++;
                }
                if (start < cols && c > start)
                    sides++;
                c++;
            }
            // Bottom
            c = 0;
            while (c < cols) {
                while (c < cols && !inRegion[r][c])
                    c++;
                int start = c;
                while (c < cols && inRegion[r][c]) {
                    if (r == rows - 1 || !inRegion[r + 1][c])
                        break;
                    c++;
                }
                if (start < cols && c > start)
                    sides++;
                c++;
            }
        }
        // Vertical sides (left and right)
        for (int c = 0; c < cols; c++) {
            // Left
            int r = 0;
            while (r < rows) {
                while (r < rows && !inRegion[r][c])
                    r++;
                int start = r;
                while (r < rows && inRegion[r][c]) {
                    if (c == 0 || !inRegion[r][c - 1])
                        break;
                    r++;
                }
                if (start < rows && r > start)
                    sides++;
                r++;
            }
            // Right
            r = 0;
            while (r < rows) {
                while (r < rows && !inRegion[r][c])
                    r++;
                int start = r;
                while (r < rows && inRegion[r][c]) {
                    if (c == cols - 1 || !inRegion[r][c + 1])
                        break;
                    r++;
                }
                if (start < rows && r > start)
                    sides++;
                r++;
            }
        }
        return sides;
    }
}
