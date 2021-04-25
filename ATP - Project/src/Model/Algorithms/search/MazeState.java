package algorithms.search;


import algorithms.mazeGenerators.Position;


public class MazeState extends AState {

    private Position position;

    /**
     * initialize the maze state
     * @param parent
     * @param pos
     * @param cost
     */
    public MazeState(AState parent, Position pos, double cost){
        super(pos.toString());
        setPrev(parent);
        Position p = new Position(pos.getRowIndex(),pos.getColumnIndex());
        setPosition(p);
        setCost(cost);

    }

    /**
     * init the maze state
     * @param parent
     * @param pos
     */
    public MazeState(AState parent, Position pos){
        super(pos.toString());
        setPrev(parent);
        Position p = new Position(pos.getRowIndex(),pos.getColumnIndex());
        setPosition(p);
    }

    /**
     * get and set position
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return  position.toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeState mazeState = (MazeState) o;
        return position != null ? position.equals(mazeState.position) : mazeState.position == null;
    }

    @Override
    public int hashCode() {
        if (position==null)return 0;
        int x = position.getRowIndex();
        int y = position.getColumnIndex();
        return (((x+y)*(x+y+1))/2)+x;
    }
}
