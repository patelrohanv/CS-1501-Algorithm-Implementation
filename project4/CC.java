/******************************************************************************
 *  Compilation:  javac CC.java
 *  Execution:    java CC filename.txt
 *  Dependencies: Graph.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                http://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                http://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *  % java CC tinyG.txt
 *  3 components
 *  0 1 2 3 4 5 6
 *  7 8
 *  9 10 11 12
 *
 *  % java CC mediumG.txt
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  % java -Xss50m CC largeG.txt
 *  1 components
 *  0 1 2 3 4 5 6 7 8 9 10 ...
 *
 *  Note: This implementation uses a recursive DFS. To avoid needing
 *        a potentially very large stack size, replace with a non-recurisve
 *        DFS ala NonrecursiveDFS.java.
 *
 ******************************************************************************/

/**
 *  The {@code CC} class represents a data type for
 *  determining the connected components in an undirected graph.
 *  The <em>id</em> operation determines in which connected component
 *  a given vertex lies; the <em>connected</em> operation
 *  determines whether two vertices are in the same connected component;
 *  the <em>count</em> operation determines the number of connected
 *  components; and the <em>size</em> operation determines the number
 *  of vertices in the connect component containing a given vertex.

 *  The <em>component identifier</em> of a connected component is one of the
 *  vertices in the connected component: two vertices have the same component
 *  identifier if and only if they are in the same connected component.

 *  <p>
 *  This implementation uses depth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>
 *  (in the worst case),
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <em>id</em>, <em>count</em>, <em>connected</em>,
 *  and <em>size</em> operations take constant time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/41graph">Section 4.1</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class CC {
    private boolean[] marked;   // marked[v] = has vertex v been marked?
    private int[] id;           // id[v] = id of connected component containing v
    private int[] size;         // size[id] = number of vertices in given component
    private int count;          // number of connected components

    /**
     * Computes the connected components of the undirected graph {@code G}.
     *
     * @param G the undirected graph
     */
    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    /**
     * Computes the connected components of the edge-weighted graph {@code G}.
     *
     * @param G the edge-weighted graph
     */
    public CC(EdgeWeightedGraph G, double currentPrice, double maxPrice) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            //if (!marked[v]) {
               System.out.println("\n---------------------------------------------");
                System.out.println("Starting at: " + Airline.cities[v]);
                dfs(G, v, currentPrice, maxPrice);
                count++;
            //}
        }
    }

    // depth-first search for a Graph
    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    // depth-first search for an EdgeWeightedGraph
    private void dfs(EdgeWeightedGraph G, int v, double currentPrice, double maxPrice) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        boolean printedOnce = false;
        boolean enteredLoop = false;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            double temp = currentPrice + e.price();
            if (!marked[w] && temp <= maxPrice) {
                enteredLoop = true;
                currentPrice += e.price();
                System.out.println(Airline.cities[e.either()] + " to "+ Airline.cities[e.other(e.either())]);
                System.out.println("Current Price: " + currentPrice);
                dfs(G, w, currentPrice, maxPrice);
            }
          //   else if(!printedOnce && enteredLoop){
          //     System.out.println("Total Price for this set of trips: " + currentPrice);
          //     printedOnce = true;
          //   }
        }

    }


    /**
     * Returns the component id of the connected component containing vertex {@code v}.
     *
     * @param  v the vertex
     * @return the component id of the connected component containing vertex {@code v}
     */
    public int id(int v) {
        return id[v];
    }

    /**
     * Returns the number of vertices in the connected component containing vertex {@code v}.
     *
     * @param  v the vertex
     * @return the number of vertices in the connected component containing vertex {@code v}
     */
    public int size(int v) {
        return size[id[v]];
    }

    /**
     * Returns the number of connected components in the graph {@code G}.
     *
     * @return the number of connected components in the graph {@code G}
     */
    public int count() {
        return count;
    }

    /**
     * Returns true if vertices {@code v} and {@code w} are in the same
     * connected component.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     *         connected component; {@code false} otherwise
     */
    public boolean connected(int v, int w) {
        return id(v) == id(w);
    }

    /**
     * Returns true if vertices {@code v} and {@code w} are in the same
     * connected component.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @return {@code true} if vertices {@code v} and {@code w} are in the same
     *         connected component; {@code false} otherwise
     * @deprecated Replaced by {@link #connected(int, int)}.
     */
    @Deprecated
    public boolean areConnected(int v, int w) {
        return id(v) == id(w);
    }
}
