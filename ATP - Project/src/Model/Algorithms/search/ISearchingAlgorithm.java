package algorithms.search;

/**
 * Searching Algorithms Interface
 */
public interface ISearchingAlgorithm {

    /**
     * Execute the search algorithm
     * @param s - Isearchable
     * @return Solution
     */
    Solution solve(ISearchable s);

    /**
     * get the number of visited nodes while solving
     * @return int - number of those visited nodes
     */
    int getNumberOfNodesEvaluated();

    /**
     * get the searching algorithm name
     * @return String - the algorithm's name
     */
    String getName();

}
