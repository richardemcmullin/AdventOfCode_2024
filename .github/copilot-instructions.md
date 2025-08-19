
# Copilot Instructions for AdventOfCode_2024

## Project Overview
This repository contains Advent of Code 2024 solutions, organized by day in separate packages (e.g., `day01`, `day02`, ...). Each day's solution is a standalone Java class extending a shared base class.

## Architecture & Patterns
- **Base Class:** All solutions extend `aoc.AdventOfCodeSolver`, which provides:
	- Console input reading (user pastes puzzle input, ends with blank line)
	- Abstract methods for input parsing (`parseInput`), part 1 (`solvePart1`), and part 2 (`solvePart2`)
	- Automatic timing and output formatting for both parts
- **Entry Point:** Each day's solution class (e.g., `Day05_AI.java`, `Day09_RM.java`) has a `main` method that instantiates itself and calls `.run()`.
- **Input Handling:** Input is read from standard input, with a prompt to paste the puzzle input. Input sections can be split by blank lines if needed (see `setInputSections`).
- **No External Dependencies:** All code is pure Java, no third-party libraries.

## Developer Workflow
- **Add a Solution:** Create a new class in the appropriate `dayXX` package, extend `AdventOfCodeSolver`, and implement the required methods.
- **Run a Solution:** Run the class, paste the input when prompted, and view results for both parts with timing.
- **Input Parsing:** Use the `inputLines` field (a list of strings) in your solution class. Implement `parseInput()` to convert these lines into your data structures.
- **Testing/Debugging:** There are no automated tests or build scripts; run solutions directly and use commentary files for notes.

## Project-Specific Conventions
- **File/Space Modeling:** Some solutions (e.g., Day09) use custom classes to model files and spaces for simulation. See `Day09_RM.java` for an example of object-oriented modeling of disk compaction.
- **Commentary Files:** Each day may have a `.Commentary.txt` file for developer notes and solution commentary.
- **Multiple Solution Variants:** Some days have multiple solution files (e.g., `Day05_AI.java`, `Day05_RM.java`, `Day05_AI2.java`) for different approaches or authors.

## Key Files & Directories
- `aoc/AdventOfCodeSolver.java`: Base class for all solutions; read this file to understand the required structure and workflow.
- `dayXX/DayXX_*.java`: Daily solution files; see these for implementation patterns and conventions.
- `dayXX/DayXX.Commentary.txt`: Optional commentary and notes for each day's solution.

## Example Patterns
- **Disk Compaction (Day09):** See `Day09_RM.java` for modeling files and spaces as objects, and for simulating compaction and checksum calculation.
- **Input Section Splitting:** Use `.setInputSections(n)` before `.run()` if the puzzle input has multiple sections separated by blank lines.

## Integration Points
- No external services, APIs, or dependencies are used. All computation is local and self-contained.

## How to Be Immediately Productive
- Always extend `AdventOfCodeSolver` and implement the three abstract methods.
- Use `inputLines` for input data; parse in `parseInput()`.
- Use the provided timing/output logic; do not reimplement it.
- Follow the file organization and naming conventions for new solutions.

---
**Feedback:** If any section is unclear or incomplete, please specify which part needs more detail or examples.

