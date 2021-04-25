package Server;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.SimpleMazeGenerator;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy {


    private AMazeGenerator checkMazeGeneratorAlgorithm(String s) {
        try {
            if (s != null) {
                if (s.equals("SimpleMazeGenerator")) {
                    return new SimpleMazeGenerator();
                } else {
                    return new MyMazeGenerator();
                }
            } else {
                return new MyMazeGenerator();
            }
        } catch (Exception e) {
            return new MyMazeGenerator();
        }
    }


    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        Properties properties = new Properties();

        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            toClient.flush();
            int[] size = (int[]) (fromClient.readObject());
            if (size.length != 2) {
                size = new int [] {10,10};
                /*WHAT TO DO IF ARRAY SIZE IS NOT 2?!?!*/
//                System.out.println("array size: " + size.length + " is not 2"); //CHECK CHECK CHECK!
                /* WHAT TO DO IF ARRAY SIZE IS NOT 2?!?! */
            }
            InputStream input = new FileInputStream("Resources/config.properties");
            properties.load(input);
            AMazeGenerator mazeGenerator;
            try {
                String mazeGenerateAlgorithm = properties.getProperty("Generate Maze Algorithm");
                mazeGenerator = checkMazeGeneratorAlgorithm(mazeGenerateAlgorithm);
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                mazeGenerator = checkMazeGeneratorAlgorithm(" ");
            }

            Maze maze = mazeGenerator.generate(size[0], size[1]);
            String mazeFileName = "compressedMaze.maze";
            MyCompressorOutputStream output = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            output.write(maze.toByteArray());
            int len = output.getLen();
            output.flush();
            output.close();
            InputStream inputMazeFileNam = new FileInputStream(mazeFileName);
            byte[] savedMazeBytes = new byte[len];
            inputMazeFileNam.read(savedMazeBytes);
            inputMazeFileNam.close();
            toClient.writeObject(savedMazeBytes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}