package algorithms.search;


import java.util.HashSet;
import java.util.Stack;

/**
 * DFS Algorithm
 */
public class DepthFirstSearch extends ASearchingAlgorithm{

    /**
     * DFS Data Structure
     */
    private Stack<AState> stack;

    /**
     * DFS Constructor
     */
    public DepthFirstSearch() {
        stack = new Stack<AState>();
        evaluatedNodes = new HashSet<AState>();
        solution = new Solution();
        evaluatedNodesCount=1;

    }

    @Override
    public Solution solve(ISearchable searchDomain) {
        stack.add(searchDomain.getStartingState());
        evaluatedNodes.add(searchDomain.getStartingState());
        while (!stack.isEmpty()) {
            AState tmpState = stack.pop();
            if (tmpState.toString().equals(searchDomain.getEndingState().toString())) {
                solution.backTrace(tmpState);
                return solution;
            }
            neighbors = searchDomain.getAllPossibleStates(tmpState);// add all neighbors of tmpState
            for (AState aState : neighbors) {           // for each neighbor
                aState.setPrev(tmpState);               // give tmpState as the previous state
                if (!evaluatedNodes.contains(aState)) { // if we never have been in this aState
                    stack.add(aState);                  // add it to the stack
                    evaluatedNodesCount++;              // count it as another evaluated node
                    evaluatedNodes.add(aState);         // and add it to the evaluatedNodes Set
                }
            }

        }
        return null;
    }

}