package aoc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AdventOfCodeSolver {

    private int inputSections = 1;
    protected List<String> inputLines = new ArrayList<>();

    /**
     * Set the number of input sections (blank line delimited) in the input
     * <br>
     * NOTE: This method must be called before {@link #run()}.
     * 
     * @param inputSections the number of input sections
     * @return this AdventOfCodeSolver instance for method chaining
     */
    public AdventOfCodeSolver setInputSections(int inputSections) {
        this.inputSections = inputSections;
        return this;
    }

    private void readInput() {

        int inputSection = 1;

        try {
            System.out.println("Paste your Advent of Code input and press Enter on a blank line to finish:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String line;
            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    inputSection++;
                    if (inputSection > inputSections) {
                        break;
                    }
                }

                inputLines.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }

    /**
     * Parse the input lines into the required data structures.
     */
    protected abstract void parseInput();

    /**
     * Solve part 1 of the puzzle.
     */
    public abstract Object solvePart1();

    /**
     * Solve part 2 of the puzzle.
     */
    public abstract Object solvePart2();

    public void run() {

        readInput();
        parseInput();

        long start1 = System.nanoTime();
        Object part1 = solvePart1();
        long end1 = System.nanoTime();
        long time1 = Math.round((end1 - start1) / 1_000_000.0);
        System.out.println("Part 1: " + part1 + " (" + time1 + " ms)");

        long start2 = System.nanoTime();
        Object part2 = solvePart2();
        long end2 = System.nanoTime();
        long time2 = Math.round((end2 - start2) / 1_000_000.0);
        System.out.println("Part 2: " + part2 + " (" + time2 + " ms)");
    }
}
