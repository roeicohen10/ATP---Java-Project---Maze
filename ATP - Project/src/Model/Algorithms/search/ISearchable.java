package algorithms.search;

import java.util.HashSet;

/**
 * Isearchable <<Interface>>
 */
public interface ISearchable {

    /**
     * get the initial state
     * @return the state that the search begins with
     */
    AState getStartingState();

    /**
     * get the goal state
     * @return the state that the search finishes with
     */
    AState getEndingState();

    /**
     * get all the possible states given by a current state
     * @param state - the current state
     * @return ArrayList of Astates
     */
    HashSet<AState> getAllPossibleStates(AState state);
}
