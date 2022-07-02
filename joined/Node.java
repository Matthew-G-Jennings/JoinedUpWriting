package JoinedUpWriting;

import java.lang.Comparable;

public class Node implements Comparable<Node> {

    String val;
    boolean visited;
    Node previous;
    int dist;

    public Node(String c) {
        this.val = c;
        this.visited = false;
        this.previous = null;
        this.dist = 0;
    }

    @Override
    public int compareTo(Node b) {
        return this.val.length() - b.val.length();
    }
}
