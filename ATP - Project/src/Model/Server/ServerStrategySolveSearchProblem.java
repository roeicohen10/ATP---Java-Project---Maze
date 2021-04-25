package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private HashMap<byte[], String> mazesHistory = new HashMap<>();


    private Solution solveWithAlgorithm(String s, SearchableMaze sMaze) {
       try {
           if (s != null) {
               if (s.equals("BestFirstSearch")) {
                   ASearchingAlgorithm BestFS = new BestFirstSearch();
                   return BestFS.solve(sMaze);
               } else if (s.equals("BreadthFirstSearch")) {
                   ASearchingAlgorithm BreadthFS = new BreadthFirstSearch();
                   return BreadthFS.solve(sMaze);
               } else if (s.equals("DepthFirstSearch")) {
                   ASearchingAlgorithm DepthFS = new DepthFirstSearch();
                   return DepthFS.solve(sMaze);
               } else {
                   ASearchingAlgorithm BestFS = new BreadthFirstSearch();
                   return BestFS.solve(sMaze);
               }
           } else {
               ASearchingAlgorithm BestFS = new BreadthFirstSearch();
               return BestFS.solve(sMaze);
           }
       } catch (Exception e){
           ASearchingAlgorithm BestFS = new BreadthFirstSearch();
           return BestFS.solve(sMaze);
       }

    }


    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        Properties properties = new Properties();
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            toClient.flush();
            Maze maze = (Maze) fromClient.readObject();
            byte[] data = maze.toByteArray();
//            /* FOR SUBMISSION */
//            String path = System.getProperty("java.io.tmpdir");
//            /* FOR LINUX USE */
            String path = System.getProperty("user.dir");
            path += "/tmp";
            System.out.println(path);
            String solvedMazesPath = path + "\\solvedMazes";
            File file = new File(solvedMazesPath);
            if (file.exists()) {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(solvedMazesPath));
                if (mazesHistory.isEmpty()) {
                    mazesHistory = (HashMap) input.readObject();
                }
                input.close();
            }
            Solution sol;
            if (!mazesHistory.containsKey(data)) {
                SearchableMaze sMaze = new SearchableMaze(maze);
                InputStream input = new FileInputStream("Resources/config.properties");
                properties.load(input);
                String search = properties.getProperty("Search Algorithm");
                sol = solveWithAlgorithm(search, sMaze);
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String mazeSolution = path + "\\mazeSolution";
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(mazeSolution + mazesHistory.size()));
                output.writeObject(sol);
                ObjectOutputStream solvedMazesFile = new ObjectOutputStream(new FileOutputStream(new File(solvedMazesPath)));
                mazesHistory.put(data, "Solution" + mazesHistory.size());
                solvedMazesFile.writeObject(mazesHistory);
                solvedMazesFile.flush();
                solvedMazesFile.close();
            } else {
                String s = mazesHistory.get(data);
                ObjectInputStream inputData = new ObjectInputStream(new FileInputStream(path + "\\" + s));
                sol = (Solution) inputData.readObject();
                inputData.close();
            }
            toClient.writeObject(sol);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
