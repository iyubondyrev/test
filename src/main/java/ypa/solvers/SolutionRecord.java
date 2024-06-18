package ypa.solvers;

import ypa.command.SCommand;
import java.util.ArrayList;

/**
 * A record of a solution.
 * Contains the solution and the list of commands that were executed to reach it.
 * 
 * @author Aleksandr Vardanian 1942263
 * @author Aleksandr Nikolaev 2001675
 */
public class SolutionRecord {
    public int[] solution;
    public ArrayList<SCommand> commands;

    /**
     * Constructs a solution record.
     * 
     * @param solution  the solution
     * @param commands  the list of commands
     */
    public SolutionRecord(int[] solution, ArrayList<SCommand> commands) {
        this.solution = solution;
        this.commands = commands;
    }
}
