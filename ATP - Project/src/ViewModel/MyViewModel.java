package ViewModel;


import java.util.Observable;
import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private int userRowPosition;
    private int userColPosition;
    private StringProperty userRow = new SimpleStringProperty("");
    private StringProperty userCol = new SimpleStringProperty("");;
    private IModel iModel;

    public StringProperty getUserRow(){
        return userRow;
    }

    public StringProperty getUserCol(){
        return userCol;
    }

    @Override
    public void update(Observable o, Object arg){
        if(o==iModel){
            userRowPosition = iModel.getCharacterRow();
            userColPosition = iModel.getCharacterColumn();
            userRow.setValue(userRowPosition + "");
            userCol.setValue(userColPosition + "");
            setChanged();
            notifyObservers(arg);
        }
    }
    public void move(KeyCode move){
        iModel.move(move);
    }

    public void mazeGenerator(int rows,int columns){
        iModel.mazeGenerator(rows,columns);
    }

    public void solutionResolver(){
        iModel.solutionResolver();
    }

    public Maze getMaze(){
        return iModel.getMaze();
    }

    public Solution getSol(){
        return iModel.getSolution();
    }

    public int getUserRowPosition(){
        return userRowPosition;
    }

    public int getUserColPosition(){
        return userColPosition;
    }

    public void save(){
        iModel.save();
    }

    public void exit(){
        iModel.exit();
    }

    public void load(){
        iModel.load();
    }

    public void reset(){
        iModel.reset();
    }

    public MyViewModel(IModel m){
        iModel = m;
    }

}
