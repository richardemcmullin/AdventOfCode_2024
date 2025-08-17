// Advent of Code 2024 - Day 04 Solution
// Author: Richard McMullin (with GPT-4.1 support)
package day04;

import aoc.AdventOfCodeSolver;

public class Day04_RM extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day04_RM().run();
    }

    private char[][] wordSearch;
    private int numRows, numCols;

    enum Direction {
        RIGHT(0, 1),
        LEFT(0, -1),
        DOWN(1, 0),
        UP(-1, 0),
        DOWN_RIGHT(1, 1),
        UP_LEFT(-1, -1),
        DOWN_LEFT(1, -1),
        UP_RIGHT(-1, 1);

        final int rowDelta, colDelta;

        Direction(int rowDelta, int colDelta) {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }
    }

    @Override
    protected void parseInput() {

        numRows = inputLines.size();
        numCols = inputLines.get(0).length();

        wordSearch = new char[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            wordSearch[row] = inputLines.get(row).toCharArray();
        }
    }

    @Override
    public Object solvePart1() {

        int totalMatches = 0;

        String word = "XMAS";

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                // Check for directions only if this character matches first letter
                if (wordSearch[row][col] != word.charAt(0)) {
                    continue;
                }

                // Check for a match in each direction
                for (Direction direction : Direction.values()) {
                    if (matchesWord(word, row, col, direction)) {
                        totalMatches++;
                    }
                }
            }
        }

        return totalMatches;
    }

    @Override
    public Object solvePart2() {

        // Count crosses of MAS centered on the letter A.
        int totalMatches = 0;

        for (int row = 1; row < numRows - 1; row++) {
            for (int col = 1; col < numCols - 1; col++) {

                // Check for diagonal directions only if this character matches center letter
                if (wordSearch[row][col] != 'A') {
                    continue;
                }

                // Check the diagonal \ and /
                String diag1 = "" + wordSearch[row - 1][col - 1] + wordSearch[row][col] + wordSearch[row + 1][col + 1];

                if (!diag1.equals("MAS") && !diag1.equals("SAM")) {
                    continue;
                }

                String diag2 = "" + wordSearch[row - 1][col + 1] + wordSearch[row][col] + wordSearch[row + 1][col - 1];

                if (!diag2.equals("MAS") && !diag2.equals("SAM")) {
                    continue;
                }

                totalMatches++;
            } 
        }
        
        return totalMatches;
    }

    private boolean matchesWord(String word, int startRow, int startCol, Direction direction) {

        for (int i = 0; i < word.length(); i++) {

            int currentRow = startRow + direction.rowDelta * i;
            int currentCol = startCol + direction.colDelta * i;

            // Check bounds
            if (currentRow < 0 || currentRow >= numRows || currentCol < 0 || currentCol >= numCols) {
                return false;
            }

            // Check character match
            if (wordSearch[currentRow][currentCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }

}
