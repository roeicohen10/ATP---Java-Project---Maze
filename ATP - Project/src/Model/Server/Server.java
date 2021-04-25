package Server;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    private static class Configurations {
        public static void RunProperties() {
            Properties prop = new Properties();
            FileOutputStream output = null;

            try {
                File file = new File("Resources/config.properties");
                if (!file.exists()) {
                    output = new FileOutputStream("Resources/config.properties");
                    prop.setProperty("Number of Threads", "2");
                    prop.setProperty("Generate Maze Algorithm", "MyMazeGenerator");
                    prop.setProperty("Search Algorithm", "BestFirstSearch");
                    prop.store(output, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    private static final Logger LOG = Logger.getLogger(Server.class.getName());


    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        LOG.setLevel(Level.SEVERE);
        Configurations p = new Configurations();
        Configurations.RunProperties();
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream("Resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String numOfThreads;
        int n;
        try {
            numOfThreads = prop.getProperty("Number of Threads");
            if (numOfThreads != null) {
                try {
                    n = Integer.parseInt(numOfThreads);
                    if (n<1) n=2;
                } catch (NumberFormatException e) {
                    n = 2;
                }
            }
            else
                n = 2;
        } catch (Exception e) {
            e.printStackTrace();
            n = 2;
        }

        threadPool = Executors.newFixedThreadPool(n);
    }


    public void start() {
        new Thread(() -> runServer()).start();
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            LOG.info(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    threadPool.execute(() -> handleClient(clientSocket));
                    LOG.info(String.format("Client excepted: %s", clientSocket.toString()));
                } catch (SocketTimeoutException e) {
                    LOG.warning("SocketTimeout - No clients pending!");
                }
            }
        } catch (IOException e) {
            LOG.severe(String.format("IOException: ", e));
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            LOG.info("Client axcepted!");
            LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getOutputStream().close();
//            clientSocket.getInputStream().close();
            clientSocket.close();

        } catch (IOException e) {
            LOG.severe(String.format("IOException: ", e));
        }
    }

    public void stop() {
        LOG.info("Stopping server..");
        stop = true;
        threadPool.shutdown();
    }
}
