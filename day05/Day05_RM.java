// Advent of Code 2024 - Day 05 Solution
// Author: Richard McMullin (with GPT-4.1 support)
package day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aoc.AdventOfCodeSolver;

public class Day05_RM extends AdventOfCodeSolver {

    public static void main(String[] args) {
        new Day05_RM().setInputSections(2).run();
    }

    private Map<Integer, List<Integer>> ruleMap = new HashMap<>();

    private List<List<Integer>> updates = new ArrayList<>();

    @Override
    protected void parseInput() {

        int section = 1;
        String[] pageStrings;

        for (String line : inputLines) {

            if (line.isEmpty()) {
                section++;
                continue;
            }

            switch (section) {

                case 1:
                    pageStrings = line.split("\\|");

                    int beforePageNumber = Integer.parseInt(pageStrings[0].trim());
                    int afterPageNumber = Integer.parseInt(pageStrings[1].trim());

                    // Collect a list of 'must follow' pages
                    List<Integer> mustFollowPages = ruleMap.get(afterPageNumber);
                    if (mustFollowPages == null) {
                        mustFollowPages = new java.util.ArrayList<>();
                        ruleMap.put(afterPageNumber, mustFollowPages);
                    }

                    mustFollowPages.add(beforePageNumber);

                    break;

                case 2:
                    // Parse updates section
                    // Each update is in the form "pageNumber1,pageNumber2,..."
                    pageStrings = line.split(",");
                    List<Integer> update = new ArrayList<>();
                    for (String pageString : pageStrings) {
                        update.add(Integer.parseInt(pageString.trim()));
                    }
                    updates.add(update);
                    break;
            }
        }
    }

    @Override
    public Object solvePart1() {

        int middlePageTotal = 0;

        for (List<Integer> update : updates) {

            // Skip updates that are not in order
            if (!pagesInOrder(update)) {
                continue;
            }

            middlePageTotal += getMiddlePage(update);
        }
        return middlePageTotal;
    }

    @Override
    public Object solvePart2() {

        int middlePageTotal = 0;

        for (List<Integer> update : updates) {

            // Skip updates that are not in order
            if (pagesInOrder(update)) {
                continue;
            }

            List<Integer> orderedUpdate = orderPages(update);

            middlePageTotal += getMiddlePage(orderedUpdate);
        }
        return middlePageTotal;
    }

    private int getMiddlePage(List<Integer> update) {

        int middleIndex = update.size() / 2;
        return update.get(middleIndex);
    }

    /**
     * This method checks if the pages in the update are in the correct order
     * according to the rules defined in ruleMap, using recursion.
     * 
     * @param update the list of page numbers in the update
     * @return {@code true} if the pages are in order, {@code false} otherwise
     */
    private boolean pagesInOrder(List<Integer> update) {

        if (update.size() < 2) {
            return true;
        }

        int currentPage = update.get(0);

        List<Integer> mustFollowPages = ruleMap.get(currentPage);

        if (mustFollowPages != null) {

            for (int mustFollowPage : mustFollowPages) {
                // If one of the must-follow pages is in the remaining update list
                // then the order is not correct.
                if (update.contains(mustFollowPage)) {
                    return false; // Not in order
                }
            }
        }

        return pagesInOrder(update.subList(1, update.size()));
    }

    private List<Integer> orderPages(List<Integer> update) {

        List<Integer> orderedUpdate = new ArrayList<>(update);

        // Sort the update list based on the rules
        orderedUpdate.sort((page1, page2) -> {

            List<Integer> mustFollowPages1 = ruleMap.get(page1);
            List<Integer> mustFollowPages2 = ruleMap.get(page2);

            // Assume the rules make sense and there are no cirular dependencies
            if (mustFollowPages1 != null && mustFollowPages1.contains(page2)) {
                return 1; // page2 must come before page1
            } else if (mustFollowPages2 != null && mustFollowPages2.contains(page1)) {
                return -1; // page1 must come before page2
            }
            return 0; // They are essentially equal because no rules apply
        });

        return orderedUpdate;
    }

}
