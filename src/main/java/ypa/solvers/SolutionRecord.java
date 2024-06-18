package ypa.solvers;

import ypa.command.SCommand;

import java.util.Stack;
import java.util.ArrayList;

public class SolutionRecord {
    public int[] solution;
    public ArrayList<SCommand> commands;

    public SolutionRecord(int[] solution, ArrayList<SCommand> commands) {
        this.solution = solution;
        this.commands = commands;
    }
}
