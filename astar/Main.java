package astar;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            inputList.add(scanner.nextLine());
        }
        initAStar(inputList);
    }

    private static void initAStar(ArrayList<String> inputList) {
        String[] nodesEdges = inputList.get(0).split(" ");
        // n nodes
        int n = Integer.parseInt(nodesEdges[0]);
        // m edges
        int m = Integer.parseInt(nodesEdges[1]);

        Graph graph = new Graph(n);

        for (int i = 1; i <= n; i++) {
            // Create nodes with heuristic
            int heuristic = Integer.parseInt(inputList.get(i));
            // 1 = node 0, 2 = node 1, ...
            graph.addNode(i - 1, heuristic);
        }

        for (int j = n + 1; j <= n + m; j++) {
            String[] edges = inputList.get(j).split(" ");
            int srcNode = Integer.parseInt(edges[0]);
            int destNode = Integer.parseInt(edges[1]);
            int costs = Integer.parseInt(edges[2]);
            graph.addEdge(srcNode, destNode, costs);
        }

        Astar astar = new Astar(graph, graph.getNode(0), graph.getNode(n - 1));
        astar.calculatePath();
    }

}
