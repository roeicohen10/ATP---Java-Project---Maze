package algorithms.search;

import java.util.HashSet;
import java.util.Set;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected HashSet<AState> evaluatedNodes;
    protected int evaluatedNodesCount;
    protected Set<AState> neighbors;
    protected Solution solution;

    /**
     * get the number of nodes that the searching algorithm has been in their state
     * @return evaluatedNodesCount - the counter of the evaluated nodes
     */
    public int getNumberOfNodesEvaluated() {
        return evaluatedNodesCount;
    }

    /**
     * get the name of the searching algorithm
     * @return String - class.getSimpleName
     */
    public  String getName(){
        return this.getClass().getSimpleName();
    }
}


