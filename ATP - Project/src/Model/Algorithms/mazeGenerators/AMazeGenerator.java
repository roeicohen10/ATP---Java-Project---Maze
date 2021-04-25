package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * abstract class of MazeGenerator
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    protected ArrayList<Position> neighbors;

    /**
     * ganerate a new maze
     * @param rows - the number of rows that the maze is going to have
     * @param columns - the number of columns that the maze is going to have
     * @return Maze
     */
    @Override
    public abstract Maze generate(int rows, int columns);


    /**
     * measure the time by milliseconds for generating a new maze
     * @param rows - the number of rows that the maze is going to have
     * @param columns - the number of columns that the maze is going to have
     * @return long time - the time (ms) it took to generate the maze
     */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime = System.currentTimeMillis();
        generate(rows, columns);
        long finishTime = System.currentTimeMillis();
        return finishTime - startTime;
    }
}