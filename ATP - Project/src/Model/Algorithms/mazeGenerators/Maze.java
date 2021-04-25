package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Maze class
 */
public class Maze implements Serializable {
    private int[][] grid;
    private Position startPos;
    private Position goalPos;

    /**
     * Constructor, gets an uncompressed byte array that represents the maze              FIX!!
     *
     * @param b - uncompressed byte array
     */
    public Maze(byte[] b) {
        if (b == null || b.length < 13)
            return;
        int rows = bytesToInt(0, 1, b);
        int columns = bytesToInt(2, 3, b);
        grid = new int[rows][columns];
        int tmpRow = bytesToInt(4, 5, b);
        int tmpCol = bytesToInt(6, 7, b);
        startPos = new Position(tmpRow, tmpCol);
        tmpRow = bytesToInt(8, 9, b);
        tmpCol = bytesToInt(10, 11, b);
        goalPos = new Position(tmpRow, tmpCol);
        int k = 12;
        for (int i = 0; i < rows && k < b.length; i++) {
            for (int j = 0; j < columns && k < b.length; j++) {
                grid[i][j] = (int) b[k];
                k++;
            }
        }
    }

    /**
     * @param i - index of the slot that contains number of full slots (255 in each one)
     * @param j - index of the slot that contains the modulo of the real number from 255
     * @param b - the byte array that contains the compressed data
     * @return int - the real integer value that was converted to 2-byte-number
     */
    private int bytesToInt(int i, int j, byte[] b) {
        int res = (b[i] & (0xff)) * 255;
        res += (b[j] & (0xff));
        return res;
    }

    /**
     * Maze constructor, initialize all slots as wall (1)
     * and start and goal positions as (-1,-1)
     *
     * @param rows    - number of rows the maze will have
     * @param columns - number of columns the maze will have
     */
    public Maze(int rows, int columns) {
        if (rows < 2) rows = 10;
        if (columns < 2) columns = 10;
        grid = new int[rows][columns];
        // CHECK IF ROWS OR COLUMNS >= 0 !!!!!
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = 1;
            }
        }
        startPos = new Position(-1, -1);
        goalPos = new Position(-1, -1);
    }

    /**
     * Constuctor for prepared grid and positions
     *
     * @param preparedGrid - the grid to make maze of
     * @param start        - start position
     * @param goal         - goal position
     */
    public Maze(int[][] preparedGrid, Position start, Position goal) {
        grid = preparedGrid;
        startPos = start;
        goalPos = goal;
    }

    /**
     * get the maze' grid
     *
     * @return int [][] grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * get the start position
     *
     * @return startPosition
     */
    public Position getStartPosition() {
        return startPos;
    }

    /**
     * get the goal position
     *
     * @return goalPosition
     */
    public Position getGoalPosition() {
        return goalPos;
    }

    /**
     * set the Start position
     *
     * @param row    - the new row value
     * @param column - the new column value
     */
    public void setStartPos(int row, int column) {
        if (getGoalPosition().getColumnIndex() == -1 && getGoalPosition().getRowIndex() == -1) {
            startPos.setRow(row);
            startPos.setColumn(column);
            grid[row][column] = 0;
        }
    }


    /**
     * set the Goal position
     *
     * @param row    - the new row value
     * @param column - the new column value
     */
    public void setGoalPos(int row, int column) {
        if (getGoalPosition().getColumnIndex() == -1 && getGoalPosition().getRowIndex() == -1) {
            goalPos.setRow(row);
            goalPos.setColumn(column);
            grid[row][column] = 0;
        }
    }

    /**
     * set the grid
     *
     * @param grid - the new grid to use
     */
    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

    /**
     * print the maze
     * S - start Position
     * E - exit Position
     */
    public void print() {
        int s_row = getStartPosition().getRowIndex();
        int s_column = getStartPosition().getColumnIndex();
        int e_row = getGoalPosition().getRowIndex();
        int e_column = getGoalPosition().getColumnIndex();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == s_row && j == s_column)
                    System.out.print("S");
                else if (i == e_row && j == e_column)
                    System.out.print("E");
                else
                    System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public byte[] toByteArray() {
        int size = grid.length * grid[0].length;
        byte[] data = new byte[size + 12];
        data[0] = (byte) (grid.length / 255);
        data[1] = (byte) (grid.length % 255);
        data[2] = (byte) (grid[0].length / 255);
        data[3] = (byte) (grid[0].length % 255);
        data[4] = (byte) (startPos.getRowIndex() / 255);
        data[5] = (byte) (startPos.getRowIndex() % 255);
        data[6] = (byte) (startPos.getColumnIndex() / 255);
        data[7] = (byte) (startPos.getColumnIndex() % 255);
        data[8] = (byte) (goalPos.getRowIndex() / 255);
        data[9] = (byte) (goalPos.getRowIndex() % 255);
        data[10] = (byte) (goalPos.getColumnIndex() / 255);
        data[11] = (byte) (goalPos.getColumnIndex() % 255);
        int k = 12;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; k++, j++) {
                data[k] = (byte) (grid[i][j]);
            }
        }
        return data;
    }
}
