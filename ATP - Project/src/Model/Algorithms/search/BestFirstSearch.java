package algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *best first search algorithm
 */

public class BestFirstSearch extends ASearchingAlgorithm {

    private PriorityQueue<AState> pariorityQ;

    public BestFirstSearch(){
        pariorityQ = new PriorityQueue<AState>(new Comparator<AState>() {
            @Override
            public int compare(AState o1, AState o2) {
                return (int)(o1.getCost()-o2.getCost());
            }
        });
        evaluatedNodes = new HashSet<AState>();
        solution = new Solution();
        evaluatedNodesCount=1;
    }

    /**
     * algorithm to find the solution path
     * @param searchDomain
     * @return solution
     */
    @Override
    public Solution solve(ISearchable searchDomain) {
        pariorityQ.add(searchDomain.getStartingState());
        while(pariorityQ.size()>0){
            AState n = pariorityQ.poll();
            evaluatedNodes.add(n);
            if(n.getState().equals(searchDomain.getEndingState().toString())){
                solution.backTrace(n);
                return solution;
            }
            //assign parents to each position
            neighbors = searchDomain.getAllPossibleStates(n);
            for(AState astate : neighbors){
                if(!evaluatedNodes.contains(astate) && !pariorityQ.contains(astate)) {
                    astate.setPrev(n);
                    pariorityQ.add(astate);
                    evaluatedNodesCount++;
                }
                //update cost field if algorithm finds a better path
                else if(n.getCost()+1 < astate.getCost()){
                    if(!pariorityQ.contains(astate)){
                        astate.setCost(n.getCost()+1);
                        astate.setPrev(n);
                        pariorityQ.add(astate);
                        evaluatedNodesCount++;
                    }
                    else{
                        astate.setPrev(n);
                        astate.setCost(n.getCost()+1);
                    }

                }
            }
        }
        return null;
    }

}


