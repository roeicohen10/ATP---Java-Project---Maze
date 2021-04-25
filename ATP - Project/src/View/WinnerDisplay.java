package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WinnerDisplay extends Canvas {

    public void redraw(Stage mainStage)  {

        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0,0,getWidth(),getHeight());
        Image winner = new Image("/resources/winner/winner.gif");
        ImageView iv = new ImageView();
        Media win = new Media(new File("resources/winnerSound/monkey.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(win);
        mediaPlayer.play();
        iv.setImage(winner);
        iv.setFitHeight(getHeight());
        iv.setFitWidth(getWidth());
        Scene main_scene = mainStage.getScene();
        Button button = new Button();
        button.setText("New Game");
        button.setOnAction(e-> mainStage.setScene(main_scene));

        Pane p = new Pane();
        Scene s = new Scene(p,getWidth(),getHeight());
        p.getChildren().addAll(iv,button);
        mainStage.setScene(s);
        mainStage.show();

    }
}
