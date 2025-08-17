// Advent of Code 2024 - Day 04 Solution
// Author: Richard McMullin + GPT-4.1

package day04;

import aoc.AdventOfCodeSolver;

public class Day04_AI extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day04_AI().run();
    }

    private char[][] puzzleGrid;
    private int numRows, numCols;
    private final String SEARCH_WORD = "XMAS";
    private final int SEARCH_WORD_LENGTH = SEARCH_WORD.length();

    @Override
    protected void parseInput() {
        numRows = inputLines.size();
        numCols = inputLines.get(0).length();
        puzzleGrid = new char[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            puzzleGrid[row] = inputLines.get(row).toCharArray();
        }
    }

    @Override
    public Object solvePart1() {
        int totalMatches = 0;
        int[][] directionVectors = {
                { 0, 1 }, // right
                { 0, -1 }, // left
                { 1, 0 }, // down
                { -1, 0 }, // up
                { 1, 1 }, // down-right
                { -1, -1 }, // up-left
                { 1, -1 }, // down-left
                { -1, 1 } // up-right
        };
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                for (int[] direction : directionVectors) {
                    if (matchesWord(row, col, direction[0], direction[1]))
                        totalMatches++;
                }
            }
        }
        return totalMatches;
    }

    private boolean matchesWord(int startRow, int startCol, int rowDelta, int colDelta) {
        for (int i = 0; i < SEARCH_WORD_LENGTH; i++) {
            int currentRow = startRow + rowDelta * i;
            int currentCol = startCol + colDelta * i;
            if (currentRow < 0 || currentRow >= numRows || currentCol < 0 || currentCol >= numCols)
                return false;
            if (puzzleGrid[currentRow][currentCol] != SEARCH_WORD.charAt(i))
                return false;
        }
        return true;
    }

    @Override
    public Object solvePart2() {
        // X-MAS: two MAS in an X shape, each MAS can be forwards or backwards
        int xmasCount = 0;
        String MAS = "MAS";
        String SAM = new StringBuilder(MAS).reverse().toString();
        // For each cell, check if it is the center of an X
        for (int row = 1; row < numRows - 1; row++) {
            for (int col = 1; col < numCols - 1; col++) {
                // Check diagonal down-right and up-left
                String diag1 = "" + puzzleGrid[row - 1][col - 1] + puzzleGrid[row][col] + puzzleGrid[row + 1][col + 1];
                // Check diagonal down-left and up-right
                String diag2 = "" + puzzleGrid[row - 1][col + 1] + puzzleGrid[row][col] + puzzleGrid[row + 1][col - 1];
                boolean found = false;
                // Each diagonal must be MAS or SAM
                if ((diag1.equals(MAS) || diag1.equals(SAM)) && (diag2.equals(MAS) || diag2.equals(SAM))) {
                    found = true;
                }
                if (found)
                    xmasCount++;
            }
        }
        return xmasCount;
    }
}
