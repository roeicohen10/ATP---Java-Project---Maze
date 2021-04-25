package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

public class MyModel extends Observable implements IModel {

    private Server serverGenerator;
    private Server mazeSolved;
    private Maze new_maze;
    private Solution sol;
    private int userRowPosition;
    private int userColPosition;
    private boolean solved;

    public void initM(){
        serverGenerator = new Server(5400,1000,new ServerStrategyGenerateMaze());
        mazeSolved = new Server(5400,100,new ServerStrategySolveSearchProblem());
        serverGenerator.start();
        mazeSolved.start();
    }

    @Override
    public void mazeGenerator(int rows, int columns) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, columns};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[100000 ];
                        is.read(decompressedMaze);
                        new_maze = new Maze(decompressedMaze);
                        userRowPosition = new_maze.getStartPosition().getRowIndex();
                        userColPosition = new_maze.getStartPosition().getColumnIndex();
                        solved = false;
                        setChanged();
                        notifyObservers("mazeDisplay");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void setSolved(boolean toSet){
        solved = toSet;
    }

    public void solutionResolver(){
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(new_maze); //send maze to server
                        toServer.flush();
                        sol = (Solution) fromServer.readObject();
                        setChanged();
                        notifyObservers("solDisplay");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void move(KeyCode move){
        if(solved==false){
            switch(move){
                case NUMPAD4:
                    if(checkMazeSizes(userRowPosition,userColPosition-1)){
                        userColPosition--;
                    }
                    break;

                case NUMPAD6:
                    if(checkMazeSizes(userRowPosition,userColPosition+1)){
                        userColPosition++;
                    }
                    break;

                case NUMPAD8:
                    if(checkMazeSizes(userRowPosition-1,userColPosition)){
                        userRowPosition--;
                    }
                    break;

                case NUMPAD2:
                    if(checkMazeSizes(userRowPosition+1,userColPosition)){
                        userRowPosition++;
                    }
                    break;

                case NUMPAD7:
                    if(checkMazeSizes(userRowPosition-1,userColPosition-1)){
                        userRowPosition--;
                        userColPosition--;
                    }
                    break;

                case NUMPAD9:
                    if(checkMazeSizes(userRowPosition-1,userColPosition+1)){
                        userRowPosition--;
                        userColPosition++;
                    }
                    break;

                case NUMPAD1:
                    if(checkMazeSizes(userRowPosition+1,userColPosition-1)){
                        userRowPosition++;
                        userColPosition--;
                    }
                    break;

                case NUMPAD3:
                    if(checkMazeSizes(userRowPosition+1,userColPosition+1)){
                        userRowPosition++;
                        userColPosition++;
                    }
                    break;
            }
            if(userRowPosition==new_maze.getGoalPosition().getRowIndex()
                    && userColPosition==new_maze.getGoalPosition().getColumnIndex()){
                setSolved(true);
                setChanged();
                notifyObservers("");
            }
        }
    }

    private boolean checkMazeSizes(int row, int col){
        if(row<0 || row>=new_maze.getRows() || col<0 || col>=new_maze.getColumns())
            return false;
        if(new_maze.getMaze()[row][col]==1)
            return false;
        return true;
    }

    public void exit(){
        this.serverGenerator.stop();
        this.mazeSolved.stop();
    }

    public void reset(){
        if(new_maze!=null){
            this.userRowPosition = this.new_maze.getStartPosition().getRowIndex();
            this.userColPosition = this.new_maze.getStartPosition().getColumnIndex();
            sol = null;
            setSolved(false);
            setChanged();
            notifyObservers("mazeDisplay");
        }
    }

    public void save() {
        boolean saved_maze=false;
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (new_maze!= null && selectedFile != null){
            try{
                ObjectOutputStream save_maze = new ObjectOutputStream(new FileOutputStream(selectedFile));
                save_maze.writeObject(new_maze);
                save_maze.flush();
                save_maze.close();
                saved_maze = true;
            } catch (IOException e) {
                saved_maze = false;
                e.printStackTrace();
            } finally {
                Alert is_saved = new Alert(saved_maze ? Alert.AlertType.CONFIRMATION : Alert.AlertType.ERROR);
                is_saved.setContentText(saved_maze ? "Game saving succeeded": "Game saving failed");
                is_saved.showAndWait();
            }
        }
    }

    public void load(){
        boolean loaded_maze=false;
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile!=null) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selectedFile));
                new_maze = (Maze)(ois.readObject());
                userRowPosition = new_maze.getStartPosition().getRowIndex();
                userColPosition = new_maze.getStartPosition().getColumnIndex();
                setSolved(false);
                sol = null;
                setChanged();
                notifyObservers("mazeDisplay");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Game loading failed");
                alert.showAndWait();
            } catch (ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Game loading failed");
                alert.showAndWait();
            }
        }
    }

    @Override
    public int getCharacterRow() {
        return userColPosition;
    }

    @Override
    public int getCharacterColumn() {
        return userRowPosition;
    }

    @Override
    public Maze getMaze() {
        return new_maze;
    }

    @Override
    public Solution getSolution() {
        return sol;
    }
}
