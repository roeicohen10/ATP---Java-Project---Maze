package View;

import javafx.fxml.Initializable;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

public class Help implements Initializable {

    public javafx.scene.control.Label helpL;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String help = "Welcome to Help window.\n"
                + "This game is a maze game and the goal is to get the Monkey to the Banana.\n"
                + "You can move the monkey by using the Numpad numbers :\n"
                + "2 : Going Down.\n"
                + "8 : Going Up\n"
                + "4 : Going Left\n"
                + "6 : Going Right\n"
                + "You can also use the Numbers 7,9,1,3 to move in a diagonal line.\n"
                + "All you need to do is to move the Monkey toward the Banana until he reach it and eat it with joy, means the maze has been solved!\n";
        helpL.setText(help);
        helpL.setMinHeight(Region.USE_PREF_SIZE);
        helpL.setMinWidth(Region.USE_PREF_SIZE);
    }
}
