package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MazeDisplay extends Canvas{

    private Maze maze;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameWay = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();

    public void redraw(Maze maze,int charRow,int charCol) {
        if (maze != null) {
            this.maze = maze;
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.getMaze().length;
            double cellWidth = canvasWidth / maze.getMaze()[0].length;
            characterPositionRow = charRow;
            characterPositionColumn = charCol;

                Image wallImage = new Image("resrouces/wall/bush.png");
                Image characterImage = new Image("resources/player/monkey.png");
                Image wayImage = new Image("resources/way/grass.jpg");
                Image goalImage = new Image("resources/goal/banana.jpg");

                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                for (int i = 0; i < maze.getMaze().length; i++) {
                    for (int j = 0; j < maze.getMaze()[i].length; j++) {
                        if (maze.getMaze()[i][j] == 1) {
                            gc.drawImage(wallImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                        }
                        else
                            gc.drawImage(wayImage,i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                    }
                }

                gc.drawImage(goalImage,maze.getGoalPosition().getColumnIndex() * cellWidth, maze.getGoalPosition().getRowIndex() * cellHeight ,cellWidth , cellHeight);
                gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);

        }
    }


    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }
    public String getImageFileNameWay() {
        return ImageFileNameWay.get();
    }

    public void setImageFileNameWay(String imageFileNameWay) {
        this.ImageFileNameWay.set(imageFileNameWay);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }
}
