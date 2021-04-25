package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.HashSet;


public class SearchableMaze implements ISearchable {

    private Maze maze;

    public SearchableMaze(Maze maze) {
        if(maze!=null)
        this.maze = maze;
    }

    /**
     *
     * @return start state
     */
    @Override
    public AState getStartingState() {


        AState initialState = new MazeState(null, maze.getStartPosition());
        initialState.setCost(0);
        return initialState;
    }

    /**
     *
     * @return goal state
     */
    @Override
    public AState getEndingState() {
        AState goalState = new MazeState(null, maze.getGoalPosition());
        return goalState;
    }

    /**
     * get all the Succesors / neighbors for the current state
     * @param s - state
     * @return list of neighbors
     */
    @Override
    public HashSet<AState> getAllPossibleStates(AState s) {
        HashSet<AState> neighbors = new HashSet<>();

        //create new position near the current state and check it validity
        Position neighborUp = new Position(((MazeState)s).getPosition().getRowIndex()-1, ((MazeState)s).getPosition().getColumnIndex());
        checkNeighbor(((MazeState)s),neighborUp,neighbors);

        Position neighborDown = new Position(((MazeState)s).getPosition().getRowIndex()+1, ((MazeState)s).getPosition().getColumnIndex());
        checkNeighbor(((MazeState)s),neighborDown,neighbors);

        Position neighborRight = new Position(((MazeState)s).getPosition().getRowIndex(), ((MazeState)s).getPosition().getColumnIndex()+1);
        checkNeighbor(((MazeState)s),neighborRight,neighbors);

        Position neighborLeft = new Position(((MazeState)s).getPosition().getRowIndex(), ((MazeState)s).getPosition().getColumnIndex()-1);
        checkNeighbor(((MazeState)s),neighborLeft,neighbors);


        return neighbors;

    }

    /**
     * check if the neighbor valid - in the maze, not a wall, and didnt came from him(not a parent)
     * @param s
     * @param neighbor
     * @param neighbors
     */
    private void checkNeighbor(MazeState s, Position neighbor, HashSet<AState> neighbors) {
        if(neighbor.getRowIndex()>=0 && neighbor.getColumnIndex()>=0 && neighbor.getRowIndex()<maze.getGrid().length && neighbor.getColumnIndex()<maze.getGrid()[0].length){
            if(maze.getGrid()[neighbor.getRowIndex()][neighbor.getColumnIndex()]!=1){
                if((s.getPrev() != null && !(((MazeState)s.getPrev()).getPosition().toString().equals(neighbor.toString()))) || s.getPrev() == null){
                    AState newState = new MazeState(s,neighbor, s.getCost()+1);
                    neighbors.add(newState);
                }
            }

        }
    }
}

