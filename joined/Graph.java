package JoinedUpWriting;

import java.util.*;

public class Graph {

    private ArrayList<Node> nodes;
    private int[][] adjmat;

    public Graph() {
        this.nodes = new ArrayList<Node>();
    }

    public void addNode(String name) {
        System.out.println("Adding node with value: " + name);
        this.nodes.add(new Node(name));
    }

    public void generateAdj() {
        this.adjmat = new int[this.nodes.size()][this.nodes.size()];
        for (int i = 0; i < adjmat.length; i++) {
            for (int j = 0; j < adjmat.length; j++) {
                System.out.println("Checking " + this.nodes.get(i).name + " against " + this.nodes.get(j).name);
                if (linked(this.nodes.get(i).name, this.nodes.get(j).name) == 2) {
                    this.adjmat[i][j] = 2;
                } else if (linked(this.nodes.get(i).name, this.nodes.get(j).name) == 1) {
                    this.adjmat[i][j] = 1;
                } else {
                    this.adjmat[i][j] = 0;
                }
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
                        // System.out.println("Single links");
                        return 1;
                    }
                }
            }
        }
        // System.out.println("No links");
        return 0;
    }

    public void printNodes() {
        System.out.println();
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print(nodes.get(i).name + " ");
        }
        System.out.println();
    }

    public void printAdj() {
        for (int i = 0; i < this.adjmat.length; i++) {
            System.out.println();
            System.out.print(this.nodes.get(i).name + ":" + "\t");
            if (this.nodes.get(i).name.length() <= 6) {
                System.out.print("\t");
            }
            for (int j = 0; j < this.adjmat.length; j++) {
                System.out.print(this.adjmat[i][j] + " ");
            }
        }
    }

    public class Node {

        private String name;
        private boolean visited;

        public Node(String name) {
            this.name = name;
            this.visited = false;
        }
    }

}
