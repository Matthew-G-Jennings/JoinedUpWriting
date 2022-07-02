package JoinedUpWriting;

import java.util.*;

public class JoinedUp {

    public static Queue<Node> queue;
    public static ArrayList<Node> currNeigh;

    public static void main(String[] args) {

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
        shortPath(first, last, dict, 2);
    }

    public static void shortPath(Node first, Node last, ArrayList<Node> dict, int max) {
        System.out.println(first.val + " " + last.val);
        Node curr; // set current node to the first node
        boolean found = false;

        queue.add(first); // add the first node to the queue

        while (!queue.isEmpty() && !found) { // while we have not found a path
            curr = queue.remove(); // pop from queue
            System.out.println("Popped: " + curr.val);
            getNeighbors(curr, dict, max); // get the neighbors of the current node
            for (Node n : currNeigh) { // for each neighbor
                System.out.println(n.val + " is a neighbor of current node: " + curr.val);
                queue.add(n);
                n.previous = curr;
                if (linked(n.val, last.val) >= max) { // if that neighbor links to the last node
                    System.out.println(n.val + " links to final node " + last.val);
                    curr = queue.remove();
                    last.previous = curr;
                    found = true;
                } else {
                    System.out.println(n.val + " Not linked to final node, popping " + queue.peek().val);
                    break;
                }
            }
        }

        for (Node n = last; n.previous != null; n = n.previous) {
            System.out.println("::" + n.val);
        }

    }

    public static void getNeighbors(Node curr, ArrayList<Node> dict, int max) {
        currNeigh = new ArrayList<Node>();
        for (Node n : dict) {
            if (n.visited == true) {
                continue;
            } else if (linked(curr.val, n.val) >= max) {
                n.visited = true;
                currNeigh.add(n);
            }
        }
    }

    public static String[] genPrefix(String word) {
        String[] result = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            result[i] = word.substring(i, word.length());
        }
        return result;
    }

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
                        // System.out.println("Double links");
                        return 2;
                    } else if (a.length() >= (flen / 2) || a.length() >= (slen / 2)) {
                        System.out.println("Single links");
                        return 1;
                    }
                }
            }
        }
        // System.out.println("No links");
        return 0;
    }

}
