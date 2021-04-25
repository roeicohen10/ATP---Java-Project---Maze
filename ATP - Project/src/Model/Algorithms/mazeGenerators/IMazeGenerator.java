package algorithms.mazeGenerators;


/**
 * Maze Generator interface
 */
public interface IMazeGenerator
{
    /**
     * ganerate a new maze
     * @param rows - the number of rows that the maze is going to have
     * @param columns - the number of columns that the maze is going to have
     * @return Maze
     */
    public Maze generate(int rows, int columns) ;

    /**
     * measure the time by milliseconds for generating a new maze
     * @param rows - the number of rows that the maze is going to have
     * @param columns - the number of columns that the maze is going to have
     * @return long time - the time (ms) it took to generate the maze
     */
    public long measureAlgorithmTimeMillis(int rows, int columns) ;

}