package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    private PriorityQueue<AState> pQueue;

    public BreadthFirstSearch() {
        evaluatedNodes = new HashSet<>();
        evaluatedNodesCount = 1;
//        neighbors = new ArrayList<>();
        solution = new Solution();
    }

    /**
     * BFS Algorithm to find shortest math from start to goal
     *
     * @param s
     * @return sulution
     */
    public Solution solve(ISearchable s) {
        pQueue = new PriorityQueue<>(new Comparator<AState>() {
            @Override
            public int compare(AState state, AState t1) {
                return 0;
            }
        });
        pQueue.add(s.getStartingState());
        while (!pQueue.isEmpty()) {
            AState currState = pQueue.poll();
            evaluatedNodes.add(currState);
            if (currState.getState().equals(s.getEndingState().toString())) { //check if the state is the GoalState
                solution.backTrace(currState);
                return solution;
            }
            neighbors = s.getAllPossibleStates(currState);
            for (AState state
                    : neighbors) {
                if (!pQueue.contains(state) && !evaluatedNodes.contains(state)){
                    state.setPrev(currState);
                    pQueue.add(state);
                    evaluatedNodesCount++;
                }
            }
        }
        return null; //No possible way to GoalState
    }
}
