package algorithms.mazeGenerators;

import java.util.Random;

/**
 * Maze generator that creates a simple maze.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * private function that creates end/start position for the maze
     * @param rows
     * @param columns
     * @return array of two that represented the position
     */
    private int[] makePosition(int rows, int columns) {
        int position[] = new int[2];
        Random coinFlip = new Random();
        int point = coinFlip.nextInt(3);
        if (point == 0) {
            position[0] = 0;
            point = coinFlip.nextInt(columns);
            position[1] = point;
        } else if (point == 1) {
            position[0] = rows - 1;
            point = coinFlip.nextInt(columns);
            position[1] = point;
        } else {
            point = coinFlip.nextInt(rows);
            position[0] = point;
            point = coinFlip.nextInt(2);
            if (point == 0)
                position[1] = 0;
            else
                position[1] = columns - 1;
        }
        return position;
    }

    /**
     *
     * @param grid - the board
     * @param x - row position from the end
     * @param y - column position from the end
     * @param i - row position from the start
     * @param j - column position from the start
     * @return the grid
     */
    private int[][] makePassage(int[][] grid, int x, int y, int i, int j) {
        while (x != i || y != j) {
            if (x > i) {
                i++;
                grid[i][j] = 0;
            } else if (x != i) {
                x++;
                grid[x][y] = 0;
            } else if (y > j) {
                j++;
                grid[i][j] = 0;
            } else {
                y++;
                grid[x][y] = 0;
            }
        }
        return grid;
    }

    /**
     *  after the path from start to end created, randomize walls
     *  all over the grid except the path
     * @param grid - the board
     * @return - the board itself
     */
    private int[][] randomizeWalls(int[][] grid) {
        Random coinFlip = new Random();
        int res;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    res = coinFlip.nextInt(2);
                    if (res == 0)
                        grid[i][j] = 0;
                }
            }
        }
        return grid;
    }

    /**
     * generate a new maze
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
        Maze m = new Maze(rows, columns);
        int startPosition[] = makePosition(rows, columns);
        m.setStartPos(startPosition[0], startPosition[1]);
        boolean flag = false;
        int endingPosition[] = new int[2];
        if (rows >1 && columns>1) {
            while (!flag) {
                endingPosition = makePosition(rows, columns);
                if (m.getStartPosition().getRowIndex() != endingPosition[0] && m.getStartPosition().getColumnIndex() != endingPosition[1]) {
                    flag = true;
                }
            }
        }
        m.setGoalPos(endingPosition[0], endingPosition[1]);
        int[][] grid = m.getGrid();
        grid = makePassage(grid, m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex(), m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex());
        grid = randomizeWalls(grid);
        m.setGrid(grid);
        return m;
    }
}