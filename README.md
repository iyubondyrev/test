# <Sujiko>
## Group Assignment 2IRR00 2024 group 60
* Ivan Bondyrev 1970275
* Dainius Gelžinis 1995006
* Akvilė Lukauskaitė 1953648
* Aleksandr Nikolaev 2001675
* Aleksandr Vardanian 1942263

### Description
Our puzzle project involves implementing the Sujiko puzzle Assistant, a logic-based, number-placement puzzle. The goal is to place digits in the grid so that each group of four surrounding cells adds up to a specified sum, and no number is repeated in the surrounding cells.

### Short use case
After opening the program, the user can:
Open an Existing Puzzle:

- Navigate to the "puzzles" folder and select an existing puzzle file to load.

Create a New Puzzle:

- Choose to create a new puzzle within the application.
Solve the Puzzle:

  Switch to the "SOLVE" mode. In "SOLVE" mode:
- The selected cell is highlighted yellow.
- The last modified cell is highlighted cyan.
- Incorrectly entered numbers are displayed in red.

Apply Reasoning and Manage Actions:

- Use reasoning tools to assist in solving the puzzle.
- Undo the last action.
- Redo an undone action.
- Undo all actions.
- Redo all undone actions.
- Clear Chat Messages:


Automatically Solve the Puzzle:

- Press the "solve" button to display the solutions of the puzzle. If "Stop at first solution" is on, the program will dispaly only one solution, if there is such.

In the "View"mode the user cannot change any values.

In the "Edit" mode the user is able to change the four targetted sums.

### Explanation
TODO: A short list and explanation of what design patterns you used/modified to implement what parts (classes, behavior) of YPA

For Model package we used a Facade Pattern:

SPuzzle acts as a Facade, providing a simplified interface for interacting with the puzzle. It offers high-level methods for managing the puzzle's state, checking its validity, and accessing cells, without exposing the complexity of the underlying classes (SGrid, SCell, SEntry).

The structure of the Command package was not changed. It is implemented using command and composite patterns. We also used stack to implement undo-redo features.

As for the GUI, we did not change the general structure a lot. We implemented solving with findind and displaying all solutions, working in the background using SwingWorker. The design employs MVC pattern, undo and redo. Puzzle can be saved to a file. It is possible to switch between solving, editing, and viewing modes. There is a mode to stop at first solution.

For reasoning and solvers, we adapted the existing classes and methods for new models, implemented new backtracking solver that is able to find all solutions. The solver is interruptible by the user, highlights the rule violations, has recursive design. Reasoners can be applied to fill cells. 

### Important!
d8fc95b67f8a215a8e808ebd0568bec113ca34ba commit contains a zip folder of the project as it was between 4 and 6 of June. My (Akvile) commits in that time frame were deleted by a force push. This solution is with the agreement of Jacob Krüger. Sorry for the additional work.

### Deadline
[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/_p0yNlNQ)
