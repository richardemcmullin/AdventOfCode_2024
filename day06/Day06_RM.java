// Advent of Code 2024 - Day 06 Solution (AI)
// Author: GitHub Copilot

package day06;

import java.util.HashSet;
import java.util.Set;

import aoc.AdventOfCodeSolver;

public class Day06_RM extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day06_RM().run();
    }

    enum Direction {
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0),
        LEFT(0, -1);

        final int rowMovement;
        final int colMovement;

        Direction(int rowMovement, int colMovement) {
            this.rowMovement = rowMovement;
            this.colMovement = colMovement;
        }
    }

    class Guard {

        int row, col;
        Direction dir = Direction.UP;

        Guard(int row, int col, char dir) {
            this.row = row;
            this.col = col;
            switch (dir) {
                case '^':
                    this.dir = Direction.UP;
                    break;
                case '>':
                    this.dir = Direction.RIGHT;
                    break;
                case 'v':
                    this.dir = Direction.DOWN;
                    break;
                case '<':
                    this.dir = Direction.LEFT;
                    break;
            }
        }

        public Guard(Guard guard) {
            this.row = guard.row;
            this.col = guard.col;
            this.dir = guard.dir;
        }

        void turnRight() {
            switch (dir) {
                case UP:
                    dir = Direction.RIGHT;
                    break;
                case RIGHT:
                    dir = Direction.DOWN;
                    break;
                case DOWN:
                    dir = Direction.LEFT;
                    break;
                case LEFT:
                    dir = Direction.UP;
                    break;
            }
        }

        boolean move(char[][] map) {

            int newRow = row + dir.rowMovement;
            int newCol = col + dir.colMovement;

            while (true) {
                // Check if out of bounds
                if (newRow < 0 || newRow >= map.length || newCol < 0 || newCol >= map[0].length) {
                    return false; // did not move
                }

                // Check for wall
                if (map[newRow][newCol] == '#') {

                    // Turn right if there is a wall
                    turnRight();

                    // try to move in the new direction
                    newRow = row + dir.rowMovement;
                    newCol = col + dir.colMovement;
                    continue;
                }

                // If we can move, update position
                row = newRow;
                col = newCol;
                return true; // moved successfully
            }
        }

        public long getPosition() {
            return (long) row * 10000 + col;
        }

        public long getPositionAndDirection() {
            return (long) row * 100000 + col * 10 + dir.ordinal();
        }

        public String toString() {
            return "Guard at (" + row + ", " + col + ") facing " + dir;
        }
    }

    private char[][] map;
    private int rows, cols;
    private Guard initialGuard;

    @Override
    protected void parseInput() {

        rows = inputLines.size();
        cols = inputLines.get(0).length();

        map = new char[rows][cols];

        for (int row = 0; row < rows; row++) {

            String inputLine = inputLines.get(row);

            for (int col = 0; col < cols; col++) {

                char ch = inputLine.charAt(col);
                map[row][col] = ch;

                if (ch == '^' || ch == '>' || ch == 'v' || ch == '<') {
                    initialGuard = new Guard(row, col, ch);
                }
            }
        }
    }

    @Override
    public Object solvePart1() {

        // Use a hash set to keep track of visited positions
        // In order to improve performance over a List.
        // Data lookup is O(log n) instead of O(n).
        Set<Long> visitedPositions = new HashSet<>();

        Guard guard = new Guard(initialGuard);

        // Add the initial position of the guard
        visitedPositions.add(guard.getPosition());

        while (guard.move(map)) {

            long guardPositionHash = guard.getPosition();

            // If the guard has not visited this position before, add it
            if (!visitedPositions.contains(guardPositionHash)) {
                visitedPositions.add(guardPositionHash);
            }
        }
        return visitedPositions.size();
    }

    @Override
    public Object solvePart2() {

        int loopPositions = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // Add a new obstacle at each position in the map and determine
                // if the guard now gets stuck in a loop

                // skip existing walls
                if (map[row][col] == '#') {
                    continue;
                }

                // Skip the initial guard position
                if (row == initialGuard.row && col == initialGuard.col) {
                    continue;
                }

                // place a temporary wall
                map[row][col] = '#';

                Set<Long> visitedPositions = new HashSet<>();

                Guard guard = new Guard(initialGuard);

                // Add the initial position of the guard
                visitedPositions.add(guard.getPositionAndDirection());

                while (guard.move(map)) {

                    long guardPositionDirectionHash = guard.getPositionAndDirection();

                    // If the guard has not visited this position before, add it
                    if (!visitedPositions.contains(guardPositionDirectionHash)) {
                        visitedPositions.add(guardPositionDirectionHash);
                    } else {
                        // If we revisit a position with the same direction, we are in a loop
                        loopPositions++;
                        break;
                    }
                }

                // remove the wall before the next iteration
                map[row][col] = '.';
            }
        }
        return loopPositions;
    }

}
