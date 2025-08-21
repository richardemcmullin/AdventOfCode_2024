// Advent of Code 2024 - Day 10 Solution (RM)
// Author: RM

package day10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aoc.AdventOfCodeSolver;

public class Day10_RM extends AdventOfCodeSolver {

    class Location {
        final int row;
        final int col;

        Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            return 1000 * row + col;
        }
    }

    enum Direction {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        int dx;
        int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
    }

    int[][] map;
    int MAP_ROWS;
    int MAP_COLS;

    List<Location> trailHeadPositions = new ArrayList<>();

    @Override
    protected void parseInput() {

        // Initialize map dimensions
        MAP_ROWS = inputLines.size();
        MAP_COLS = inputLines.get(0).length();

        map = new int[MAP_ROWS][MAP_COLS];

        for (int row = 0; row < MAP_ROWS; row++) {
            String line = inputLines.get(row);
            for (int col = 0; col < MAP_COLS; col++) {
                map[row][col] = line.charAt(col) - '0';
                if (map[row][col] == 0) {
                    trailHeadPositions.add(new Location(row, col));
                }
            }
        }
    }

    @Override
    public Object solvePart1() {

        int totalSummits = 0;

        for (Location trailHead : trailHeadPositions) {
            totalSummits += findSummits(0, trailHead).size();
        }

        return totalSummits;
    }

    @Override
    public Object solvePart2() {

        int uniqueTrails = 0;

        for (Location trailHead : trailHeadPositions) {
            uniqueTrails += findUniqueTrails(0, trailHead);
        }

        return uniqueTrails;
    }

    private Set<Integer> findSummits(int curHeight, Location location) {

        if (curHeight == 9) {
            return new HashSet<>(Set.of(location.hashCode()));
        }

        int nextHeight = curHeight + 1;

        // Explore all directions where the height is the next height
        Set<Integer> summits = new HashSet<>();

        for (Direction dir : Direction.values()) {

            Location nextLocation = step(dir, location);

            if (nextLocation == null) {
                continue; // Skip if out of bounds
            }

            if (getHeight(nextLocation, map) == nextHeight) {
                summits.addAll(findSummits(nextHeight, nextLocation));
            }
        }
        return summits;
    }

    private int findUniqueTrails(int curHeight, Location location) {

        if (curHeight == 9) {
            return 1;
        }

        int nextHeight = curHeight + 1;

        int uniqueTrails = 0;

        for (Direction dir : Direction.values()) {

            Location nextLocation = step(dir, location);

            if (nextLocation == null) {
                continue; // Skip if out of bounds
            }

            if (getHeight(nextLocation, map) == nextHeight) {
                uniqueTrails += findUniqueTrails(nextHeight, nextLocation);
            }
        }
        return uniqueTrails;
    }

    private int getHeight(Location location, int[][] map) {

        if (isValidLocation(location.row, location.col)) {
            return map[location.row][location.col];
        }
        return -1; // Invalid location
    }

    private Location step(Direction dir, Location location) {

        int newRow = location.row + dir.dy;
        int newCol = location.col + dir.dx;

        if (isValidLocation(newRow, newCol)) {
            return new Location(newRow, newCol);
        }
        return null;
    }

    private boolean isValidLocation(int newRow, int newCol) {
        return newRow >= 0 && newRow < MAP_ROWS && newCol >= 0 && newCol < MAP_COLS;
    }

    public static void main(String[] args) {
        new Day10_RM().run();
    }
}
