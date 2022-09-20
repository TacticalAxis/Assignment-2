package comp611.assignment2.subdivisions.approach;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class NumberSequenceTree {

    private final Node root;

    public NumberSequenceTree () {
        this.root = new Node();
    }

    public boolean contains(int... sequence) {
        Node current = root;
        for (int number : sequence) {
            for(Node n : current.children) {
                if (n.number == number) {
                    current = n;
                    break;
                }
            }

            if (current.number != number) {
                return false;
            }
        }

        return current.value != -1;
    }

    public static class NumberSequence {
        private final int[] sequence;

        public NumberSequence(int... sequence) {
            this.sequence = sequence;
        }

        public int[] getSequence() {
            return sequence;
        }
    }

    private static class Node {
        private final int number;
        private final double value;
        private final Node parent;
        private final List<Node> children;

        public Node(Node parent, int number, double value) {
            this.number = number;
            this.value = value;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        public Node() {
            this.number = 0;
            this.value = -1;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public Node hasNum(int i) {
            for(Node child : children) {
                if (child.number == i) {
                    return child;
                }
            }

            return null;
        }

        public Node addNum(int i, double value) {
            Node n = new Node(this, i, value);
            children.add(n);

            return n;
        }

        public int depth() {
            Node currentNode = this;
            int count = 0;
            while(currentNode.parent != null) {
                count += 1;
                currentNode = currentNode.parent;
            }

            return count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder( "{" + this.number + (this.value != -1 ? "," + this.value : "") + "}" + "\n");
            for(Node n : this.children) {
                for(int i = 0; i < n.depth(); i++) {
                    sb.append("\t");
                }
                sb.append(n);
                sb.append("\n");
            }
            return sb.toString().trim();
        }
    }

    public double add(double value, int... sequence) {
        return add(value, new NumberSequence(sequence));
    }

    public double add(double value, NumberSequence sequence) {
        double retVal = -1;
        Node currentNode = this.root;
        int[] seq = sequence.getSequence();
        for(int i = 0; i < seq.length; i++) {
            Node n = currentNode.hasNum(seq[i]);
            if (n == null) {
                retVal = (i == (seq.length - 1) ? value : -1);
                currentNode = currentNode.addNum(seq[i], retVal);
            } else {
                currentNode = n;
            }
        }

        return retVal;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static void main(String[] args) {
        NumberSequenceTree nst = new NumberSequenceTree();
        nst.add(6.0, new NumberSequence(1, 2, 3, 7, 8));
        nst.add(8.0, new NumberSequence(1, 2, 2, 2, 8));
        nst.add(3.0, new NumberSequence(1, 3, 4, 5, 7));
        nst.add(6.0, new NumberSequence(1, 2, 2, 6, 9));

        System.out.println(nst);
    }
}