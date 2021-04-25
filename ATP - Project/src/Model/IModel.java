package Model;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.Observable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Observer;

public interface IModel {

    void initM();

    void mazeGenerator(int rows,int columns);

    void solutionResolver();

    void exit();

    void save();

    void load();

    void move(KeyCode move);

    void reset();

    int getCharacterRow();

    int getCharacterColumn();

    Maze getMaze();

    Solution getSolution();


}
