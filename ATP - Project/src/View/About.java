package View;

import javafx.fxml.Initializable;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {

    public javafx.scene.control.Label aboutL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String about = "This is the latest version of La-Maze game series.\n" + "The algorithm used for creating this maze is Prim and the algorithm used for solving this maze is Best-First Search.\n" +
                "Developers involved in creating this maze are : Roi Nissan and Roei Cohen also known as Rocket Team!\n" + "Hope you will enjoy playing the game.\n\n" + "Emails for question or reporting a bug : roeie@post.bgu.ac.il , roinis@post.bgu.ac.il";

        aboutL.setText(about);
        aboutL.setMinWidth(Region.USE_PREF_SIZE);
        aboutL.setMinHeight(Region.USE_PREF_SIZE);
    }
}
