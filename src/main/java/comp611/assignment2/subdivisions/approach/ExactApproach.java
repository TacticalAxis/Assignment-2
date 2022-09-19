package comp611.assignment2.subdivisions.approach;

import comp611.assignment2.subdivisions.gui.LandGUI;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;
import comp611.assignment2.subdivisions.land.Subdivision;

import javax.swing.*;
import java.util.HashMap;

@SuppressWarnings("unused")
public class ExactApproach extends Approach {

    private Node root;

    private int currentLandValue;
    private int subdivisions;

    private final HashMap<Subdivision, Integer> subMap = new HashMap<>();

    public ExactApproach(Land area) {
        super(area, "Exact Approach");
    }

    @Override
    public Result solve() {
        startTimer();

        //recursive search
        findSubDivisions(getLand().getArea());

        stopTimer();

        //call the solution method
        Area area = getSolution();
        if(area != null) {
            //solution found
            getLand().setArea(area);

            //return result
            return new Result(this, area.getLand().getValue(), getSubdivisions());
        }

        //no solution found
        return null;
    }

    @Override
    public double getBestValue() {
        return currentLandValue;
    }

    //recursive method to find all the subdivisions
    private void findSubDivisions(Area area) {
        //get all the subdivisions
        //loop through all the subdivisions
        for(Subdivision sub : area.getPossibleSubdivisions().keySet()) {
            //subdivide the area
            area.subdivide(sub);
            //increment the subdivisions
            incrementSubdivisions();
            //add the subdivision to the hashmap
            subMap.put(sub, subdivisions);
            //check if the value is greater than the current value
            if(area.getLand().getValue() > currentLandValue) {
                //if it is, set the current value to the new value
                currentLandValue = (int) area.getLand().getValue();
                //call the recursive method again
                findSubDivisions(area.getArea1());
                findSubDivisions(area.getArea2());
            } else {
                //if it is not, unSubdivide the area
                area.unSubdivide();
                //remove the subdivision from the hashmap
                subMap.remove(sub);
                //decrement the subdivisions
                subdivisions--;
            }
        }
    }

    //method to get the solution
    private Area getSolution() {
        //get the area
        Area area = getLand().getArea();
        //loop through the hashmap
        for(Subdivision sub : subMap.keySet()) {
            //compare the area with the best area found
            if(area.getValue() > currentLandValue) {
                //if it is, return the area
                return area;
            } else {
                //if it is not, subdivide the area
                area.subdivide(sub);
                //call the recursive method again
                area = getSolution();
            }
        }
        //return the area
        return area;
//        //get the area
//        Area area = getLand().getArea();
//        //loop through the hashmap
//        for(Subdivision sub : subMap.keySet()) {
//            //subdivide the area
//            area.subdivide(sub);
//        }
//        //return the area
//        return area;
    }



    /**
     * If the root is null, create a new node and return it. If the root is not null, then recursively call the add method
     * on the left or right subtree depending on the value you want to insert
     *
     * @param value The value to be added to the tree.
     */
    public void add(int value){
        root = addRecursive(root, value);
    }

    /**
     * If the value is less than the current node, go left. If the value is greater than the current node, go right. If the
     * value is equal to the current node, do nothing
     *
     * @param current The current node we are looking at.
     * @param value The value to be added to the tree.
     * @return The current node.
     */
    private Node addRecursive(Node current, int value){
        if(current == null){
            return new Node(value);
        }

        if(value < current.value){
            current.left = addRecursive(current.left, value);
        } else if(value > current.value){
            current.right = addRecursive(current.right, value);
        } else {
            //value already exists
            return current;
        }
        return current;
    }


    //recursive method to find a node'
    private boolean containsRecursiveNode(Node current, int value){
        if(current == null){
            return false;
        }
        if(value == current.value){
            return true;
        }
        return value < current.value
                ? containsRecursiveNode(current.left, value)
                : containsRecursiveNode(current.right, value);
    }

    private Node deleteRecursive(Node current, int value){
        if(current == null){
            return null;
        }

        if(value == current.value){
            //code to remove the node
            switch (current.getNumberOfChildren()){
                case 0:
                    return null;
                case 1:
                    return current.left == null ? current.right : current.left;
                case 2:
                    int smallestValue = findSmallestValue(current.right);
                    current.value = smallestValue;
                    current.right = deleteRecursive(current.right, smallestValue);
                    return current;
            }
        }

        if(value < current.value){
            current.left = deleteRecursive(current.left, value);
            return current;
        }else{
            current.right = deleteRecursive(current.right, value);
            return current;
        }
    }

    public void traverseInOrder(Node node){
        if(node != null){
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }

    private int findSmallestValue(Node right) {
        return right.left == null ? right.value : findSmallestValue(right.left);
    }

    public static void main(String[] args) {
        ExactApproach exactApproach = new ExactApproach(new Land(10,8, 50, 20,1000));
        Result solution = exactApproach.solve();
        if(solution != null) {
            System.out.println("Exact Solution Found: " + solution.getValue());
            System.out.println("This took " + exactApproach.getTime() + "s");
            System.out.println("Subdivisions Found: " + exactApproach.getSubdivisions());
            System.out.println("Solution:\n" + exactApproach.getLand());

        }

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.getContentPane().add(new LandGUI(exactApproach, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    /**
     * A Node object has a value, a left child, and a right child
     */
    class Node{
        int value;
        Node left;
        Node right;

        Node (int value){
            this.value = value;
            right = null;
            left = null;
        }

        public int getNumberOfChildren() {
            int count = 0;
            if(left != null){
                count++;
            }
            if(right != null){
                count++;
            }
            return count;
        }
    }


}
