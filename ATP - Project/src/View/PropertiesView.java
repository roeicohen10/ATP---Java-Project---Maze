package View;

import javafx.fxml.Initializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import static Server.Server.*;
import Server.Server;
import javafx.scene.layout.Region;

public class PropertiesView implements Initializable {

    public javafx.scene.control.Label propertiesL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String [] prop =  propertiesValues();
        if (prop[0] != null && prop[0] != ""){
            String setting = "Number of threads used in this maze are: " + prop[0] + ".\n"
                    + "The maze type is: " + prop[1] +".\n"
                    + "The algorithm used for this searching in the maze is: " + prop[2] +".\n";
            propertiesL.setText(setting);
            propertiesL.setMinWidth(Region.USE_PREF_SIZE);
            propertiesL.setMinHeight(Region.USE_PREF_SIZE);
        }

    }
    private String[] propertiesValues(){
        String [] prop = new String[3];
        Properties properties = new Properties();
        InputStream forProperties = null;
        try {
            forProperties = new FileInputStream("Resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            properties.load(forProperties);
            prop[1] = properties.getProperty("Threads");
            prop[2] = properties.getProperty("mazeGenerator");
            prop[3] = properties.getProperty("Algorithm");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;

    }
}
