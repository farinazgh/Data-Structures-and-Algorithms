
/******************************************************************************
 *  Compilation:  javac DepthFirstOrder.java
 *  Execution:    java DepthFirstOrder digraph.txt
 *  Dependencies: Digraph.java Queue.java Stack.java StdOut.java
 *                EdgeWeightedDigraph.java DirectedEdge.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt
 *                https://algs4.cs.princeton.edu/42digraph/tinyDG.txt
 *
 *  Compute preorder and postorder for a digraph or edge-weighted digraph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstOrder tinyDAG.txt
 *     vertex  pre post
 *  --------------
 *     0    0    8
 *     1    3    2
 *     2    9   10
 *     3   10    9
 *     4    2    0
 *     5    1    1
 *     6    4    7
 *     7   11   11
 *     8   12   12
 *     9    5    6
 *    10    8    5
 *    11    6    4
 *    12    7    3
 *  Preorder:  0 5 4 1 6 9 11 12 10 2 3 7 8 
 *  Postorder: 4 5 1 12 11 10 9 6 0 3 2 7 8 
 *  Reverse postorder: 8 7 2 3 0 6 9 10 11 12 1 5 4 
 *
 ******************************************************************************/

import util.In;
import util.Queue;
import util.StdOut;

import java.util.Stack;

/**
 * The {@code DepthFirstOrder} class represents a data type for
 * determining depth-first search ordering of the vertices in a digraph
 * or edge-weighted digraph, including preorder, postorder, and reverse postorder.
 * <p>
 * This implementation uses depth-first search.
 * Each constructor takes &Theta;(<em>V</em> + <em>E</em>) time,
 * where <em>V</em> is the number of vertices and <em>E</em> is the
 * number of edges.
 * Each instance method takes &Theta;(1) time.
 * It uses &Theta;(<em>V</em>) extra space (not including the digraph).
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DepthFirstOrder {
    private boolean[] isVisited;          // isVisited[vertex] = has vertex been isVisited in dfs?
    private int[] pre;                 // pre[vertex]    = preorder  number of vertex
    private int[] post;                // post[vertex]   = postorder number of vertex
    private Queue<Integer> preorder;   // vertices in preorder
    private Queue<Integer> postorder;  // vertices in postorder
    private int preCounter;            // counter or preorder numbering
    private int postCounter;           // counter for postorder numbering

    /**
     * Determines a depth-first order for the digraph {@code G}.
     *
     * @param G the digraph
     */
    public DepthFirstOrder(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        isVisited = new boolean[G.V()];
        for (int vertex = 0; vertex < G.V(); vertex++)
            if (!isVisited[vertex]) dfs(G, vertex);

        assert check();
    }

    /**
     * Determines a depth-first order for the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     */
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new Queue<Integer>();
        preorder = new Queue<Integer>();
        isVisited = new boolean[G.V()];
        for (int vertex = 0; vertex < G.V(); vertex++)
            if (!isVisited[vertex]) dfs(G, vertex);
    }

    // run DFS in digraph G from vertex vertex and compute preorder/postorder
    private void dfs(Digraph G, int vertex) {
        isVisited[vertex] = true;
        pre[vertex] = preCounter++;
        preorder.enqueue(vertex);
        for (int current : G.adj(vertex)) {
            if (!isVisited[current]) {
                dfs(G, current);
            }
        }
        postorder.enqueue(vertex);
        post[vertex] = postCounter++;
    }

    // run DFS in edge-weighted digraph G from vertex vertex and compute preorder/postorder
    private void dfs(EdgeWeightedDigraph G, int vertex) {
        isVisited[vertex] = true;
        pre[vertex] = preCounter++;
        preorder.enqueue(vertex);
        for (DirectedEdge e : G.adj(vertex)) {
            int current = e.to();
            if (!isVisited[current]) {
                dfs(G, current);
            }
        }
        postorder.enqueue(vertex);
        post[vertex] = postCounter++;
    }

    /**
     * Returns the preorder number of vertex {@code vertex}.
     *
     * @param vertex the vertex
     * @return the preorder number of vertex {@code vertex}
     * @throws IllegalArgumentException unless {@code 0 <= vertex < V}
     */
    public int pre(int vertex) {
        validateVertex(vertex);
        return pre[vertex];
    }

    /**
     * Returns the postorder number of vertex {@code vertex}.
     *
     * @param vertex the vertex
     * @return the postorder number of vertex {@code vertex}
     * @throws IllegalArgumentException unless {@code 0 <= vertex < V}
     */
    public int post(int vertex) {
        validateVertex(vertex);
        return post[vertex];
    }

    /**
     * Returns the vertices in postorder.
     *
     * @return the vertices in postorder, as an iterable of vertices
     */
    public Iterable<Integer> post() {
        return postorder;
    }

    /**
     * Returns the vertices in preorder.
     *
     * @return the vertices in preorder, as an iterable of vertices
     */
    public Iterable<Integer> pre() {
        return preorder;
    }

    /**
     * Returns the vertices in reverse postorder.
     *
     * @return the vertices in reverse postorder, as an iterable of vertices
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int vertex : postorder)
            reverse.push(vertex);
        return reverse;
    }


    // check that pre() and post() are consistent with pre(vertex) and post(vertex)
    private boolean check() {

        // check that post(vertex) is consistent with post()
        int r = 0;
        for (int vertex : post()) {
            if (post(vertex) != r) {
                StdOut.println("post(vertex) and post() inconsistent");
                return false;
            }
            r++;
        }

        // check that pre(vertex) is consistent with pre()
        r = 0;
        for (int vertex : pre()) {
            if (pre(vertex) != r) {
                StdOut.println("pre(vertex) and pre() inconsistent");
                return false;
            }
            r++;
        }

        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= vertex < V}
    private void validateVertex(int vertex) {
        int V = isVisited.length;
        if (vertex < 0 || vertex >= V)
            throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (V - 1));
    }

    /**
     * Unit tests the {@code DepthFirstOrder} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   vertex  pre post");
        StdOut.println("--------------");
        for (int vertex = 0; vertex < G.V(); vertex++) {
            StdOut.printf("%4d %4d %4d\n", vertex, dfs.pre(vertex), dfs.post(vertex));
        }

        StdOut.print("Preorder:  ");
        for (int vertex : dfs.pre()) {
            StdOut.print(vertex + " ");
        }
        StdOut.println();

        StdOut.print("Postorder: ");
        for (int vertex : dfs.post()) {
            StdOut.print(vertex + " ");
        }
        StdOut.println();

        StdOut.print("Reverse postorder: ");
        for (int vertex : dfs.reversePost()) {
            StdOut.print(vertex + " ");
        }
        StdOut.println();
    }
}
