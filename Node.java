package JoinedUpWriting;

import java.lang.Comparable;

/**
 * Defines a node to be used for JoinedUp.
 *
 * @author Oliver O'Connor, Patric Steenkamp, Matthew Jennings
 */
public class Node implements Comparable<Node> {

    String val;
    boolean singleVisited;
    boolean doubleVisited;
    Node previous;
    int dist;

    /**
     * Create and initialize a new node.
     *
     * @param c String this node represents.
     */
    public Node(String c) {
        this.val = c;
        this.singleVisited = false;
        this.doubleVisited = false;
        this.previous = null;
        this.dist = 0;
    }

    /**
     * Used to enable sorting of a collection of these nodes by length.
     */
    @Override
    public int compareTo(Node b) {
        return this.val.length() - b.val.length();
    }
}
