package graph;

import heap.Heap;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

/** Provides an implementation of Dijkstra's single-source shortest paths
 * algorithm.
 * Sample usage:
 *   Graph g = // create your graph
 *   ShortestPaths sp = new ShortestPaths();
 *   Node a = g.getNode("A");
 *   sp.compute(a);
 *   Node b = g.getNode("B");
 *   LinkedList<Node> abPath = sp.getShortestPath(b);
 *   double abPathLength = sp.getShortestPathLength(b);
 *   */
public class ShortestPaths {
    // stores auxiliary data associated with each node for the shortest
    // paths computation:
    private HashMap<Node,PathData> paths;

    /** Compute the shortest path to all nodes from origin using Dijkstra's
     * algorithm. Fill in the paths field, which associates each Node with its
     * PathData record, storing total distance from the source, and the
     * backpointer to the previous node on the shortest path.
     * Precondition: origin is a node in the Graph.*/
    public void compute(Node origin) {
        // TODO 1: implement Dijkstra's algorithm to fill paths with
        // shortest-path data for each Node reachable from origin.
        paths = new HashMap<>();
        Heap<Node, Double> frontier = new Heap<>();
        frontier.add(origin, 0.0);
        PathData originPath = new PathData(0.0, null);
        paths.put(origin, originPath);

        while (frontier.size() > 0) {
            Node f = (Node) frontier.poll();

            for (Map.Entry<Node, Double> set : f.getNeighbors().entrySet()) {
                Node w = set.getKey();
                Double fd = paths.get(f).distance;
                Double wd = fd + set.getValue();
                if (!frontier.contains(w) && !paths.containsKey(w)) {
                    PathData pd = new PathData(wd, f);
                    paths.put(w, pd);
                    frontier.add(w, wd);
                } else if (wd < paths.get(w).distance) {
                    PathData pd = new PathData(wd, f);
                    paths.put(w, pd);
                    frontier.changePriority(w, wd);
                }
            }
        }
    }

    /** Returns the length of the shortest path from the origin to destination.
     * To do so fetches the shortest path length from the paths data computed 
     * by Dijkstra's algorithm.
     * If no path exists, return Double.POSITIVE_INFINITY.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public double shortestPathLength(Node destination) {
        if (paths.get(destination) == null) {
            return Double.POSITIVE_INFINITY;
        } else {
            return paths.get(destination).distance;
        }
    }

    /** Returns a LinkedList of the nodes along the shortest path from origin
     * to destination. This path includes the origin and destination. If origin
     * and destination are the same node, it is included only once.
     * To do this, reconstructs sequence of Nodes along the shortest path from
     * the origin to destination using the paths data computed by Dijkstra's algorithm.
     * If no path to it exists, return null.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public LinkedList<Node> shortestPath(Node destination) {
        if (paths.get(destination) == null) {
            return null;
        }

        LinkedList<Node> sList = new LinkedList<>();
        Node c = destination;
        while (paths.get(c) != null) {
            sList.add(c);
            c = paths.get(c).previous;
        }

        // Reverses a linked list using java.util.Collections
        Collections.reverse(sList);
        return sList;
    }


    /** Inner class representing data used by Dijkstra's algorithm in the
     * process of computing shortest paths from a given source node. */
    class PathData {
        double distance; // distance of the shortest path from source
        Node previous; // previous node in the path from the source

        /** constructor: initialize distance and previous node */
        public PathData(double dist, Node prev) {
            distance = dist;
            previous = prev;
        }
    }


    /** Static helper method to open and parse a file containing graph
     * information. Can parse either a basic file or a DB1B CSV file with
     * flight data. See GraphParser, BasicParser, and DB1BParser for more.*/
    protected static Graph parseGraph(String fileType, String fileName) throws
        FileNotFoundException {
        // create an appropriate parser for the given file type
        GraphParser parser;
        if (fileType.equals("basic")) {
            parser = new BasicParser();
        } else if (fileType.equals("db1b")) {
            parser = new DB1BParser();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported file type: " + fileType);
        }

        // open the given file
        parser.open(new File(fileName));

        // parse the file and return the graph
        return parser.parse();
    }

    public static void main(String[] args) {
        // read command line args
        String fileType = args[0];
        String fileName = args[1];
        String origCode = args[2];

        String destCode = null;
        if (args.length == 4) {
            destCode = args[3];
        }

        // parse a graph with the given type and filename
        Graph graph;
        try {
            graph = parseGraph(fileType, fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file " + fileName);
            return;
        }
        graph.report();


        // ShortestPaths object used it to compute shortest
        // paths data from the origin node given by origCode.
        ShortestPaths sp = new ShortestPaths();
        sp.compute(graph.getNode(origCode));

        // If destCode was not given, prints each reachable node followed by the
        // length of the shortest path to it from the origin.
        if (destCode == null) {
            for (Node n : sp.paths.keySet()) {
                System.out.println(n.getId() + ": " + sp.paths.get(n).distance);
            }

        // If destCode was given, prints the nodes in the path from
        // origCode to destCode, followed by the total path length
        // If no path exists, prints a message saying so.
        } else {
            LinkedList<Node> path = sp.shortestPath(graph.getNode(destCode));
            if (path == null) {
                System.out.println("No path exists.");
            } else {
                for (Node n : path) {
                    System.out.print(n.getId() + " ");
                }
                System.out.println(sp.shortestPathLength(graph.getNode(destCode)));
            }
        }
    }
}
