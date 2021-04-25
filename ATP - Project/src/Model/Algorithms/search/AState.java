package algorithms.search;


import java.io.Serializable;


public abstract class AState implements Serializable {

    private String state;
    private double cost;
    private AState prev;


    /**
     * constructor of AState - initialize the state
     * @param state
     */
    public AState(String state){
        this.state = state;
    }


    /**
     * constructor
     * @param state - String
     * @param cost - double
     * @param prev - Astate
     */
    public AState(String state, double cost, AState prev) {
        this.state = state;
        this.cost = cost;
        this.prev = prev;
    }

    /**
     * copy constructor
     * @param other - to copy
     */
    public AState(AState other) {
        state = other.state;
        cost = other.cost;
        prev = other.prev;
    }

    /**
     * get the state
     * @return String state
     */
    public String getState() {
        return state;
    }

    /**
     * get cost
     * @return double cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * get previous state
     * @return Astate previous state
     */
    public AState getPrev() {
        return prev;
    }

    /**
     * set the state
     * @param state - String, the new state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * set the cost
     * @param cost - double, the new cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * set the previous state
     * @param prev - Astate, the other previous state
     */
    public void setPrev(AState prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AState)) return false;
        AState aState = (AState) o;
        return state!=null?state.equals(aState.state):aState.state==null;
    }

    @Override
    public int hashCode() {
        return state!=null? state.hashCode():0;
    }
}

