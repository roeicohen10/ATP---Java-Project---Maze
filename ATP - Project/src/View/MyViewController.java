package View;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MyViewController implements IView, Observer {


    private MyViewModel viewM;
    private MazeDisplay mDisplay;
    private SolDisplay sDisplay;
    private WinnerDisplay wDisplay;
    private About about;
    private Help help;
    private PropertiesView properties;
    public javafx.scene.control.Button generatingButton;
    public javafx.scene.control.Button solvingButton;
    public javafx.scene.control.Button reset;
    public javafx.scene.control.TextField rowsText;
    public javafx.scene.control.TextField colsText;
    public javafx.scene.control.Label userRow;
    public javafx.scene.control.Label userColumn;
    public Stage mainStage;




    public void aboutStage(ActionEvent event){
        try{
            Stage newStage = new Stage();
            newStage.setTitle("About");
            FXMLLoader fxml = new FXMLLoader();
            Parent root = fxml.load(getClass().getResource("A.fxml").openStream());
            Scene newScene = new Scene(root,800,600);
            newStage.setScene(newScene);
            help = fxml.getController();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void propertiesStage(ActionEvent event){
        try{
            Stage newStage = new Stage();
            newStage.setTitle("Properties");
            FXMLLoader fxml = new FXMLLoader();
            Parent root = fxml.load(getClass().getResource("P.fxml").openStream());
            Scene newScene = new Scene(root,800,600);
            newStage.setScene(newScene);
            properties = fxml.getController();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void helpStage(ActionEvent event){
        try{
            Stage newStage = new Stage();
            newStage.setTitle("Help");
            FXMLLoader fxml = new FXMLLoader();
            Parent root = fxml.load(getClass().getResource("H.fxml").openStream());
            Scene newScene = new Scene(root,800,600);
            newStage.setScene(newScene);
            about = fxml.getController();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void resizeScreen(Scene s){
        s.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mDisplay.redraw(viewM.getMaze(),viewM.getUserRowPosition(),viewM.getUserColPosition());
                sDisplay.redraw(viewM.getMaze(),viewM.getSol());
            }
        });
        s.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mDisplay.redraw(viewM.getMaze(),viewM.getUserRowPosition(),viewM.getUserColPosition());
                sDisplay.redraw(viewM.getMaze(),viewM.getSol());
            }
        });
    }

    public void exitGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Exit Game?");
        Optional<ButtonType> ok_or_cancel = alert.showAndWait();
        if(ok_or_cancel.get()==ButtonType.OK){
            viewM.exit();
            System.exit(0);
        }
        else{
            event.consume();
        }
    }

    public void scrolling(ScrollEvent event){
        if(event.isControlDown()){
            double zoomIn = 1.1;
            double zoomOut = event.getDeltaY();
            if(zoomOut<0){
                zoomIn = 0.9;
            }
            mDisplay.setScaleY(mDisplay.getScaleY()*zoomIn);
            mDisplay.setScaleX(mDisplay.getScaleX()*zoomIn);
            sDisplay.setScaleY(sDisplay.getScaleY()*zoomIn);
            sDisplay.setScaleX(sDisplay.getScaleX()*zoomIn);
        }
    }

    public void resetButton(ActionEvent event){
        viewM.reset();
        event.consume();
    }

    public void loadButton(ActionEvent event){
        viewM.load();
        event.consume();
    }

    public void solveButton(ActionEvent event){
        viewM.solutionResolver();
        event.consume();
    }

    public void generateButton(ActionEvent event){
        try {
            int rows_num = Integer.valueOf(rowsText.getText());
            int cols_num = Integer.valueOf(colsText.getText());
            generatingButton.setDisable(true);
            viewM.mazeGenerator(rows_num, cols_num);
            generatingButton.setDisable(false);
            reset.setDisable(false);
            solvingButton.setDisable(false);
        }
        catch (NumberFormatException nfe){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You may enter only numbers");
            alert.showAndWait();
        }
        event.consume();
    }

    public void onPlayerMovement(KeyEvent event){
        viewM.move(event.getCode());
        event.consume();
    }

    public void save(ActionEvent event){
        if(viewM.getMaze()!=null){
            viewM.save();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must generate a maze before trying to save");
            alert.showAndWait();
        }
    }

    public void setVModel(MyViewModel mvm){
        viewM = mvm;
        userRow.textProperty().bind(viewM.getUserRow());
        userColumn.textProperty().bind(viewM.getUserCol());
    }



    @Override
    public void update(Observable o, Object arg) {
        String argument = (String)arg;
        if(o==viewM){
            if(argument.equals("MazeDisplay")){
                mDisplay.redraw(viewM.getMaze(),viewM.getUserRowPosition(),viewM.getUserColPosition());
            }
            if(argument.equals("SolDisplay")){
                sDisplay.redraw(viewM.getMaze(),viewM.getSol());
            }
            if(argument.equals("WinnerDisplay")){
                wDisplay.redraw(mainStage);
            }
        }
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
