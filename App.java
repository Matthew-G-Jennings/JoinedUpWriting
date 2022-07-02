package JoinedUpWriting;

import java.util.*;

/**
 * Class to calculate the shortest path through a dictionary between two words
 *
 * Based on the notion of words being joined if a portion of a words suffix
 * matches a portion of the next words prefix.
 *
 * @author Oliver O'Connor, Patric Steenkamp, Matthew Jennings
 *
 */
public class App {

    public static Queue<Node> queue;
    public static ArrayList<Node> currNeigh;
    public static int singleCount = 0;
    public static int doubleCount = 0;

    /**
     * Main method, parses a dictionary and calls methods to calculate the shortest
     * path.
     *
     * @param args Two Strings, first is starting point word, second is end point
     *             word.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java JoinedUpWriting.App <word> <word> < <dict>");
            System.exit(1);
        }
        Node first = new Node(args[0]);
        Node last = new Node(args[1]);
        Scanner scanner = new Scanner(System.in);
        ArrayList<Node> dict = new ArrayList<Node>();
        currNeigh = new ArrayList<Node>();
        queue = new LinkedList<Node>();
        while (scanner.hasNextLine()) {
            dict.add(new Node(scanner.nextLine()));
        }
        Collections.sort(dict);
        System.out.println(args[0] + " " + args[1]);
        String singleString = shortPathSingle(first, last, dict);
        if (singleCount > 0) {
            singleCount = singleCount + 1;
            System.out.println(singleCount + " " + args[0] + " " + singleString);
        } else {
            System.out.println("0");
        }
        queue = new LinkedList<Node>();
        String doubleString = shortPathDouble(first, last, dict);
        if (doubleCount > 0) {
            doubleCount = doubleCount + 1;
            System.out.println(doubleCount + " " + args[0] + " " + doubleString);
        } else {
            System.out.println("0");
        }
    }

    /**
     * Calculates the shortest path through the dictionary requiring at least singly
     * joined words.
     *
     * Djikstra's based algorithm
     *
     * @param first starting node
     * @param last  finishing node
     * @param dict  dictionary of all words as nodes, not linked.
     * @return
     */
    public static String shortPathSingle(Node first, Node last, ArrayList<Node> dict) {
        Node curr; // set current node to the first node
        boolean found = false;
        queue.add(first); // add the first node to the queue
        while (!queue.isEmpty() && !found) { // while we have not found a path
            curr = queue.remove(); // pop from queue
            if (linked(curr.val, last.val) >= 1) { // if the popped node links to the last
                last.previous = curr;
                found = true;
                break;
            }
            getNeighborsSingle(curr, dict); // get the neighbors of the current node
            for (Node n : currNeigh) {
                queue.add(n);
                n.previous = curr;
            }
        }
        if (!found) {
            return "x";
        }
        String singlePath = "";
        for (Node n = last; n.previous != null; n = n.previous) {
            singleCount++;
            singlePath = n.val + " " + singlePath;
        }
        return singlePath;

    }

    /**
     * Calculates the shortest path through the dictionary requiring doubly joined
     * words.
     *
     * Djikstra's based algorithm
     *
     * @param first starting node
     * @param last  finishing node
     * @param dict  dictionary of all words as nodes, not linked.
     * @return
     */
    public static String shortPathDouble(Node first, Node last, ArrayList<Node> dict) {
        Node curr; // set current node to the first node
        boolean found = false;
        queue.add(first); // add the first node to the queue
        while (!queue.isEmpty() && !found) { // while we have not found a path
            curr = queue.remove(); // pop from queue
            if (linked(curr.val, last.val) == 2) { // if that neighbor links to the last node
                last.previous = curr;
                found = true;
                break;
            }
            getNeighborsDouble(curr, dict); // get the neighbors of the current node
            for (Node n : currNeigh) { // for each neighbor
                queue.add(n);
                n.previous = curr;
            }
        }
        if (!found) {
            return "x";
        }
        String doublePath = "";
        for (Node n = last; n.previous != null; n = n.previous) {
            doubleCount++;
            doublePath = n.val + " " + doublePath;
        }
        return doublePath;

    }

    /**
     * Generates the valid singly joined neightbors of a given node, stores them in
     * a static ArrayList
     *
     * @param curr the word to check.
     * @param dict the nodes to check.
     */
    public static void getNeighborsSingle(Node curr, ArrayList<Node> dict) {
        currNeigh = new ArrayList<Node>();
        for (Node n : dict) {
            if (linked(curr.val, n.val) >= 1 && n.singleVisited == false) {
                n.singleVisited = true;
                currNeigh.add(n);
            }
        }
    }

    /**
     * Generates the valid doubly joined neightbors of a given node, stores them in
     * a static ArrayList
     *
     * @param curr the word to check.
     * @param dict the nodes to check.
     */
    public static void getNeighborsDouble(Node curr, ArrayList<Node> dict) {
        currNeigh = new ArrayList<Node>();
        for (Node n : dict) {
            if (linked(curr.val, n.val) == 2 && n.doubleVisited == false) {
                // System.out.println(curr.val + " added to neighbors");
                n.doubleVisited = true;
                currNeigh.add(n);
            }
        }
    }

    /**
     * Generates all prefixes present in a given words
     *
     * @param word String word to generate prefixes for
     * @return String[] of all prefixes
     */
    public static String[] genPrefix(String word) {
        String[] result = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            result[i] = word.substring(i, word.length());
        }
        return result;
    }

    /**
     * Generates all suffixes present in a given words
     *
     * @param word String word to generate suffixes for
     * @return String[] of all suffixes
     */
    public static String[] genSuffix(String word) {
        String[] result = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            result[i] = word.substring(0, i + 1);
        }
        return result;
    }

    /**
     * Checks two String arrays to determine if the words they represent are linked.
     *
     * Will return 2 if they are doubly linked, 1 if they are singly linked, 0 if
     * they are not linked.
     *
     * @param first
     * @param second
     * @return
     */
    public static int linked(String first, String second) {
        if (first.equals(second)) {
            return 0;
        }
        // System.out.println(first + " and " + second);
        String[] firstarray = genPrefix(first);
        String[] secondarray = genSuffix(second);
        int flen = first.length();
        int slen = second.length();
        if (flen % 2 != 0) {
            flen++;
        }
        if (slen % 2 != 0) {
            slen++;
        }
        for (String a : firstarray) {
            for (String b : secondarray) {
                if (a.equals(b)) {
                    if (a.length() >= (flen / 2) && a.length() >= (slen / 2)) {
                        // System.out.println(first + " Double links " + second);
                        return 2;
                    } else if (a.length() >= (flen / 2) || a.length() >= (slen / 2)) {
                        // System.out.println(first + " Single links " + second);
                        return 1;
                    }
                }
            }
        }
        // System.out.println("No links");
        return 0;
    }

}
