package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Solution implements Serializable {

    private ArrayList solutionPath;

    /**
     * constructor for solution
     */
    public Solution() {
        solutionPath = new ArrayList();
    }

    /**
     * @return list of the solution path
     */
    public ArrayList getSolutionPath() {
        return solutionPath;
    }

    /**
     * goes back from the goal state through the path to start and reverse it
     * the solution path is from start to goal
     *
     * @param goal
     */
    public void backTrace(AState goal) {

        this.solutionPath.add((goal));
        while (goal.getPrev() != null) {
            goal.setPrev(checkIfIsATurn(goal));
            this.solutionPath.add(goal.getPrev());
            goal = goal.getPrev();
        }
        Collections.reverse(solutionPath);

    }

    private AState checkIfIsATurn(AState state) {
        AState prePrev = state.getPrev().getPrev();
        if (prePrev == null) return state.getPrev();
        String[] s = state.getState().split(",");
        String[] s2 = prePrev.getState().split(",");
        int x = Integer.parseInt(s[0].substring(1));
        int y = Integer.parseInt(s[1].substring(0, s[1].length() - 1));
        int w = Integer.parseInt(s2[0].substring(1));
        int z = Integer.parseInt(s2[1].substring(0, s2[1].length() - 1));
        if (Math.abs(x - w) == 1 && Math.abs(y - z) == 1)
             return prePrev;
        return state.getPrev();
    }

    public String toString() {
        return solutionPath.size() + " Steps";
    }
}
