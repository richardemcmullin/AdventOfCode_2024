// Advent of Code 2024 - Day 08 Solution (RM)
// Author: RM

package day08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import aoc.AdventOfCodeSolver;

public class Day08_RM extends AdventOfCodeSolver {

    class Location {

        final int row, col;

        public Location(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public long getLocationHash() {
            return (long) row * 1000 + col; // Assuming max 1000 columns
        }

        public boolean isValid() {
            return row >= 0 && row < MAP_ROWS && col >= 0 && col < MAP_COLS;
        }

        public Location getOffsetLocation(int rowOffset, int colOffset) {
            Location offsetLocation = new Location(this.row + rowOffset, this.col + colOffset);
            if (offsetLocation.isValid()) {
                return offsetLocation;
            }
            return null;
        }
    }

    class Antenna {
        Location location;
        char frequency;

        Antenna(int row, int col, char frequency) {
            this.location = new Location(row, col);
            this.frequency = frequency;
        }
    }

    Map<Character, List<Antenna>> antennaFrequencyMap = new HashMap<>();
    int MAP_ROWS, MAP_COLS;

    @Override
    protected void parseInput() {

        MAP_ROWS = inputLines.size();
        MAP_COLS = inputLines.get(0).length();

        for (int row = 0; row < MAP_ROWS; row++) {

            String inputLine = inputLines.get(row);

            for (int col = 0; col < inputLine.length(); col++) {

                char ch = inputLine.charAt(col);

                if (ch != '.') {

                    Antenna antenna = new Antenna(row, col, ch);

                    List<Antenna> antennaList = antennaFrequencyMap.get(antenna.frequency);
                    if (antennaList == null) {
                        antennaList = new ArrayList<>();
                        antennaFrequencyMap.put(antenna.frequency, antennaList);
                    }
                    antennaList.add(antenna);
                }
            }
        }
    }

    @Override
    public Object solvePart1() {

        Set<Long> antinodeLocations = new HashSet<>();

        for (Entry<Character, List<Antenna>> entry : antennaFrequencyMap.entrySet()) {

            List<Antenna> antennas = entry.getValue();

            List<Location> frequencyAntinodes = calcAntinodes(antennas, false);

            for (Location loc : frequencyAntinodes) {
                antinodeLocations.add(loc.getLocationHash());
            }
        }

        return antinodeLocations.size();
    }

    @Override
    public Object solvePart2() {

        Set<Long> antinodeLocations = new HashSet<>();

        for (Entry<Character, List<Antenna>> entry : antennaFrequencyMap.entrySet()) {

            List<Antenna> antennas = entry.getValue();

            List<Location> frequencyAntinodes = calcAntinodes(antennas, true);

            for (Location loc : frequencyAntinodes) {
                antinodeLocations.add(loc.getLocationHash());
            }
        }

        return antinodeLocations.size();
    }

    private List<Location> calcAntinodes(List<Antenna> antennas, boolean allAntinodes) {

        List<Location> antinodes = new ArrayList<>();
        List<Antenna> antennaList = new ArrayList<>(antennas);

        if (antennaList.size() < 2) {
            return antinodes; // Not enough antennas to form antinodes
        }

        Antenna a1 = antennaList.remove(0);

        for (Antenna a2 : antennaList) {

            int rowDiff = a2.location.row - a1.location.row;
            int colDiff = a2.location.col - a1.location.col;

            Location nextAntinodeLocation;

            if (!allAntinodes) {

                nextAntinodeLocation = a1.location.getOffsetLocation(-rowDiff, -colDiff);
                if (nextAntinodeLocation != null) {
                    antinodes.add(nextAntinodeLocation);
                }

                nextAntinodeLocation = a2.location.getOffsetLocation(rowDiff, colDiff);
                if (nextAntinodeLocation != null) {
                    antinodes.add(nextAntinodeLocation);
                }

            } else {

                // For all antinodes, we need to consider both directions until
                // the nextLocation is invalid.
                nextAntinodeLocation = a1.location;
                while (nextAntinodeLocation != null) {
                    antinodes.add(nextAntinodeLocation);
                    nextAntinodeLocation = nextAntinodeLocation.getOffsetLocation(-rowDiff, -colDiff);
                }

                nextAntinodeLocation = a2.location;
                while (nextAntinodeLocation != null) {
                    antinodes.add(nextAntinodeLocation);
                    nextAntinodeLocation = nextAntinodeLocation.getOffsetLocation(rowDiff, colDiff);
                }
            }
        }

        antinodes.addAll(calcAntinodes(antennaList, allAntinodes));

        return antinodes;
    }

    public static void main(String[] args) {
        new Day08_RM().run();
    }
}
