package graph;
/*
 * Author: Ashton Finch
 * Date: 7/29/25
 * Purpose: A program to test Dijktra's Algorithm.
 * Used to tests if ShortestPaths.java is correctly working.
 */
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.URL;
import java.io.FileNotFoundException;

import java.util.LinkedList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortestPathsTest {

    /* Performs the necessary gradle-related incantation to get the
       filename of a graph text file in the src/test/resources directory at
       test time.*/
    private String getGraphResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }

    /* Returns the Graph loaded from the file with filename fn located in
     * src/test/resources at test time. */
    private Graph loadBasicGraph(String fn) {
        Graph result = null;
        String filePath = getGraphResource(fn);
        try {
          result = ShortestPaths.parseGraph("basic", filePath);
        } catch (FileNotFoundException e) {
          fail("Could not find graph " + fn);
        }
        return result;
    }

    /** Dummy test case demonstrating syntax to create a graph from scratch.
     * Write your own tests below. */
    @Test
    public void test00Nothing() {
        Graph g = new Graph();
        Node a = g.getNode("A");
        Node b = g.getNode("B");
        g.addEdge(a, b, 1);

        // sample assertion statements:
        assertTrue(true);
        assertEquals(2+2, 4);
    }

    /** Minimal test case to check the path from A to B in Simple0.txt */
    @Test
    public void test01Simple0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
    }

    /* Pro tip: unless you include @Test on the line above your method header,
     * gradle test will not run it! This gets me every time. */
    @Test
    public void test02Simple01() {
        Graph g = loadBasicGraph("Simple1.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("S");
        sp.compute(a);

        // S to Node A
        Node b = g.getNode("A");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),3);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 8.0, 1e-6);

        // S to Node B
        b = g.getNode("B");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 4);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 9.0, 1e-6);

        // S to Node C
        b = g.getNode("C");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 5.0, 1e-6);

        // S to Node D
        b = g.getNode("D");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 3);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 7.0, 1e-6);
    }

    @Test
    public void test03Simple01() {
        Graph g = loadBasicGraph("Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);

        // A to Node B
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 4);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 5.0, 1e-6);

        // A to Node C
        b = g.getNode("C");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 4);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 7.0, 1e-6);

        // A to Node D
        b = g.getNode("D");
        assertNull(sp.shortestPath(b));

        // A to Node E
        b = g.getNode("E");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
        
        // A to Node F
        b = g.getNode("F");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),3);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 4.0, 1e-6);
        
        // A to Node G
        b = g.getNode("G");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),6);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 8.0, 1e-6);
        
        // A to Node H
        b = g.getNode("H");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 10.0, 1e-6);
        
        // A to Node I
        b = g.getNode("I");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),4);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 5.0, 1e-6);
        
        // A to Node J
        b = g.getNode("J");
        abPath = sp.shortestPath(b);
        assertEquals(abPath.size(),5);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(),  b);
        assertEquals(sp.shortestPathLength(b), 7.0, 1e-6);
    }

    @Test
    public void test03Simple02() {
        Graph g = loadBasicGraph("Simple2.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node b = g.getNode("B");
        sp.compute(b);
        Node d = g.getNode("D");
        // D to Node B
        
        assertNull(sp.shortestPath(d));
        assertEquals(sp.shortestPathLength(d), Double.POSITIVE_INFINITY, 1e-6);
    }
}
