package algorithms.mazeGenerators;

import java.util.ArrayList;

/**
 * our maze generator, uses prim's algorithm
 */
public class MyMazeGenerator extends AMazeGenerator {

    public MyMazeGenerator() {
        neighbors = new ArrayList<Position>();
    }

    /**
     * ganerate a new maze
     * @param rows - the number of rows that the maze is going to have
     * @param columns - the number of columns that the maze is going to have
     * @return Maze
     */
    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 1 || columns <= 1){
            rows = 100;
            columns = 100;
        }
        int[][] grid = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = 1;
            }
        }

        //get the start position (randomly)
        Position start = findRandomPositionOnFrame(rows, columns);
        grid[start.getRowIndex()][start.getColumnIndex()] = 0;
        neighbors.add(start);


        //add cells with value 0 to maze
        int count = 0;
        boolean flag = false;
        while (!neighbors.isEmpty()) {

            int r = (int) (Math.random() * neighbors.size()); // find random neighbor from list
            Position pos = new Position((Position) neighbors.get(r)); // save random position
            neighbors.remove(r); // remove random position from list

            //break wall if allowed and check that only one or less neighbors with value 0
            count = 0;
            int posRow = pos.getRowIndex();
            int posCol = pos.getColumnIndex();
            count = countWallBreaks(grid,0, posRow, posRow-1, posCol, count);
            count = countWallBreaks(grid, 0, posCol, posRow, posCol-1,count);
            count = countWallBreaks(grid, posRow+1, grid.length, posRow+1, posCol, count);
            count = countWallBreaks(grid, posCol+1, grid[0].length, posRow, posCol+1, count);
            if (count <= 1) {
                grid[posRow][posCol] = 0;
                flag = true;
            }

            //add valid neighbors to list, only if pos was a valid neighbor and value was 1 before
            if (flag) {
                addValidNeighbors(grid, 0, posRow, posRow-1, posCol);
                addValidNeighbors(grid, 0, posCol, posRow, posCol-1);
                addValidNeighbors(grid, posRow+1, grid.length, posRow+1, posCol);
                addValidNeighbors(grid, posCol+1, grid[0].length, posRow, posCol+1);
                flag = false;
            }
        }

        Position goal = findRandomPositionOnFrame(rows, columns);

        // while the start and goal are on the same side of the frame or the goal is on a wall, find another goal position
        while (goal.getColumnIndex()==start.getColumnIndex() ||
                goal.getRowIndex()==start.getRowIndex() ||
                grid[goal.getRowIndex()][goal.getColumnIndex()]!=0)
            goal = findRandomPositionOnFrame(rows, columns);

        //initialize myMaze with final grid
        Maze myMaze = new Maze(grid, start, goal);
        return myMaze;
    }

    /**
     * add the valid neighbors to the list if it had the value 1
     * @param grid - the grid of the map
     * @param pos - the index that could be out of range
     * @param limit - the range that pos cant pass
     * @param i - row index
     * @param j - column index
     */
    private void addValidNeighbors(int[][] grid, int pos, int limit, int i, int j) {
        if (pos< limit && grid[i][j]==1){
            Position tmp = new Position(i,j);
            neighbors.add(tmp);
        }
    }

    /**
     * check if its OK to break a wall (if only 1 neighbor has the value 0)
     * @param grid - the grid of the map
     * @param pos - the index that could be out of range
     * @param limit - the range that pos cant pass
     * @param i - row index
     * @param j - column index
     * @param count - the counter
     * @return count or count++ depends if passed the check
     */
    private int countWallBreaks(int [][] grid, int pos, int limit, int i, int j, int count) {
        return pos < limit && grid[i][j]==0? ++count: count;
    }

    /**
     * find a random position on the frame of a 2D array (based on math)
     * @param rows - the number of rows the 2D array has
     * @param columns - the number of columns the 2D array has
     * @returns the random position on the frame
     */
    private Position findRandomPositionOnFrame(int rows, int columns) {
        if (rows < 0 || columns < 0) return null;
        Position pos;
        int frameSide = (int) (Math.random() * 4); // choose a side of the frame
        if (frameSide % 2 == 0) {                  // if its an even number
            int onframe = (int) (Math.random() * columns);
            if (frameSide == 0)               // if its top frame side
                pos = new Position(0, onframe);
            else pos = new Position(rows - 1, onframe);
        } else {
            int onframe = (int) (Math.random() * rows);
            if (frameSide == 1)
                pos = new Position(onframe, 0);
            else pos = new Position(onframe, columns - 1);
        }
        return pos;
    }


}
