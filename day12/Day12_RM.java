package day12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.AdventOfCodeSolver;
import aoc.Direction;

public class Day12_RM extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day12_RM().run();
    }

    class Garden {

        final char type;
        Set<Plot> plots = new HashSet<>();
        Map<Integer, List<Plot>> rows = new HashMap<>();
        Map<Integer, List<Plot>> cols = new HashMap<>();

        public Garden(char type) {
            this.type = type;
        }

        public void addPlot(Plot plot) {

            plots.add(plot);

            List<Plot> rowPlotList = rows.get(plot.row);
            if (rowPlotList == null) {
                rowPlotList = new ArrayList<>();
                rows.put(plot.row, rowPlotList);
            }
            rowPlotList.add(plot);

            List<Plot> colPlotList = cols.get(plot.col);
            if (colPlotList == null) {
                colPlotList = new ArrayList<>();
                cols.put(plot.col, colPlotList);
            }
            colPlotList.add(plot);
        }

        public void sortRowsAndCols() {
            // Sort the rows by column
            for (List<Plot> rowPlots : rows.values()) {
                rowPlots.sort(new Comparator<Plot>() {
                    @Override
                    public int compare(Plot p1, Plot p2) {
                        return Integer.compare(p1.col, p2.col);
                    }
                });
            }
            // Sort the columns by row
            for (List<Plot> colPlots : cols.values()) {
                colPlots.sort(new Comparator<Plot>() {
                    @Override
                    public int compare(Plot p1, Plot p2) {
                        return Integer.compare(p1.row, p2.row);
                    }
                });
            }
        }
    }

    class Plot {
        final int row, col;
        final char type;

        public Plot(int row, int col, char type) {
            this.row = row;
            this.col = col;
            this.type = type;
        }
    }

    List<Garden> gardens = new ArrayList<>();
    Plot[][] farm;
    int ROWS;
    int COLS;

    @Override
    protected void parseInput() {

        ROWS = inputLines.size();
        COLS = inputLines.get(0).length();

        farm = new Plot[ROWS][COLS];
        Set<Plot> unvisitedPlots = new HashSet<>();

        for (int row = 0; row < inputLines.size(); row++) {
            String line = inputLines.get(row);

            for (int col = 0; col < line.length(); col++) {

                char type = line.charAt(col);
                Plot plot = new Plot(row, col, type);
                unvisitedPlots.add(plot);
                farm[row][col] = plot;
            }
        }

        // Parse the input into gardens
        while (!unvisitedPlots.isEmpty()) {
            Plot current = unvisitedPlots.iterator().next();
            unvisitedPlots.remove(current);

            // Create a new garden for the current plot
            Garden garden = new Garden(current.type);
            gardens.add(garden);
            garden.addPlot(current);

            // Explore the garden and add all connected plots
            exploreGarden(current, garden, unvisitedPlots);

            garden.sortRowsAndCols();
        }

    }

    @Override
    public Object solvePart1() {

        int totalCost = 0;

        for (Garden garden : gardens) {
            totalCost += garden.plots.size() * calcPerimiter(garden);
        }

        return totalCost;
    }

    @Override
    public Object solvePart2() {

        int totalCost = 0;

        for (Garden garden : gardens) {
            totalCost += garden.plots.size() * calcSides(garden);
        }

        return totalCost;
    }

    private int calcPerimiter(Garden garden) {

        int perimeter = 0;

        for (List<Plot> rowPlots : garden.rows.values()) {

            perimeter += 2; // Left and right edge

            for (int i = 1; i < rowPlots.size(); i++) {
                Plot prev = rowPlots.get(i - 1);
                Plot curr = rowPlots.get(i);
                // Gaps between plots are two fences
                perimeter += curr.col - prev.col > 1 ? 2 : 0;
            }
        }

        for (List<Plot> colPlots : garden.cols.values()) {

            perimeter += 2; // Left and right edge

            for (int i = 1; i < colPlots.size(); i++) {
                Plot prev = colPlots.get(i - 1);
                Plot curr = colPlots.get(i);
                // Gaps between plots are two fences
                perimeter += curr.row - prev.row > 1 ? 2 : 0;
            }
        }

        return perimeter;
    }

    private int calcSides(Garden garden) {

        // For each row, calculate the number of fences on the top
        // and bottom of the row.
        int sides = 0;

        for (List<Plot> rowPlots : garden.rows.values()) {

            Plot lastTopFence = null;
            Plot lastBottomFence = null;

            for (Plot plot : rowPlots) {

                if (requiresFence(plot, Direction.UP, garden)) {

                    // All first or non-adjacent sides start a new fence
                    if (lastTopFence == null || lastTopFence.col != plot.col - 1) {
                        sides += 1;
                    }
                    lastTopFence = plot;
                }

                if (requiresFence(plot, Direction.DOWN, garden)) {

                    // All first or non-adjacent sides start a new fence
                    if (lastBottomFence == null || lastBottomFence.col != plot.col - 1) {
                        sides += 1;
                    }
                    lastBottomFence = plot;
                }
            }
        }

        for (List<Plot> colPlots : garden.cols.values()) {

            Plot lastLeftFence = null;
            Plot lastRightFence = null;

            for (Plot plot : colPlots) {

                if (requiresFence(plot, Direction.LEFT, garden)) {

                    // All first or non-adjacent sides start a new fence
                    if (lastLeftFence == null || lastLeftFence.row != plot.row - 1) {
                        sides += 1;
                    }
                    lastLeftFence = plot;
                }

                if (requiresFence(plot, Direction.RIGHT, garden)) {

                    // All first or non-adjacent sides start a new fence
                    if (lastRightFence == null || lastRightFence.row != plot.row - 1) {
                        sides += 1;
                    }
                    lastRightFence = plot;
                }
            }
        }

        return sides;
    }

    private boolean requiresFence(Plot plot, Direction direction, Garden garden) {

        // Get the plot from the farm in the required direction
        Plot nextPlot = nextPlot(plot, direction);

        if (nextPlot == null) {
            return true; // Out of bounds, so requires a fence
        }

        // If the garden has no plot in the required direction, a fence is needed
        return !garden.plots.contains(nextPlot);
    }

    private void exploreGarden(Plot current, Garden garden, Set<Plot> unvisitedPlots) {

        for (Direction direction : Direction.values()) {

            Plot plot = nextPlot(current, direction);

            // Check the plot is valid and is the same type
            if (plot == null || plot.type != garden.type) {
                continue;
            }

            // Check that the plot is not yet visited
            if (!unvisitedPlots.contains(plot)) {
                continue;
            }

            garden.addPlot(plot);
            unvisitedPlots.remove(plot);
            exploreGarden(plot, garden, unvisitedPlots);
        }
    }

    private Plot nextPlot(Plot fmPlot, Direction direction) {

        int newRow = fmPlot.row + direction.dRow;
        int newCol = fmPlot.col + direction.dCol;

        // Check bounds
        if (newRow < 0 || newRow >= ROWS || newCol < 0 || newCol >= COLS) {
            return null;
        }
        return farm[newRow][newCol];
    }

}
