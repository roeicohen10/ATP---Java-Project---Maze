package algorithms.mazeGenerators;

import java.io.Serializable;

public class Position implements Serializable {
    private int row;
    private int column;

    /**
     * Default constructor
     * initiated as (-1,-1)
     */
    public Position(){
        row =-1;
        column=-1;
    }

    /**
     * construcor
     * @param row
     * @param column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * copy constructor
     * @param position
     */
    public Position(Position position) {
        if (position==null) throw new NullPointerException("the position is null");
        row = position.row;
        column = position.column;
    }

    /**
     * get row index
     * @return row
     */
    public int getRowIndex() {
        return row;
    }

    /**
     * get column index
     * @return column
     */
    public int getColumnIndex() {
        return column;
    }

    /**
     * set row
     * @param row - the new row value
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * set column
     * @param column - the new column value
     */
    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString(){
        return "{" + row + "," + column + "}";
    }


    public boolean equals(Position other){
        return (other.row==row && other.column==column)? true: false;
    }


    public int hashcode(){
        return (((row+column)*(row+column+1))/2)+row;
    }
}
