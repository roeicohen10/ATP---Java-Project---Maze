package test;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.util.ArrayList;

/**
 * Created by Aviadjo on 3/22/2017.
 */
public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(1000, 1000);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        BreadthFirstSearch bFS = new BreadthFirstSearch();
        DepthFirstSearch dFS = new DepthFirstSearch();
        BestFirstSearch bestFS = new BestFirstSearch();
        long startTime = System.currentTimeMillis();
        solveProblem(searchableMaze, bFS);
        long finishTime = System.currentTimeMillis();
        long bfs = finishTime - startTime;
        startTime = System.currentTimeMillis();
        solveProblem(searchableMaze, dFS);
        finishTime = System.currentTimeMillis();
        long dfs = finishTime - startTime;
        startTime = System.currentTimeMillis();
        solveProblem(searchableMaze, bestFS);
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", bFS.getName(), bFS.getNumberOfNodesEvaluated()));
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", dFS.getName(), dFS.getNumberOfNodesEvaluated()));
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", bestFS.getName(), bestFS.getNumberOfNodesEvaluated()));
        System.out.println("BFS :  "+ bfs);
        System.out.println("DFS :  "+ dfs);
        System.out.println("BestFS :  "+ (finishTime - startTime));

    }

    private static void solveProblem(ISearchable domain, ISearchingAlgorithm searcher) {
        //Solve a searching problem with a searcher
        Solution solution = searcher.solve(domain);
        System.out.println(String.format("'%s' algorithm - nodes evaluated: %s", searcher.getName(), searcher.getNumberOfNodesEvaluated()));
        //Printing Solution Path
        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s",i,solutionPath.get(i)));
        }
    }
}