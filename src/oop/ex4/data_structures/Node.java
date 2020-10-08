package oop.ex4.data_structures;

/**
 * Node class of a Binary Search Tree.
 */
public class Node {

    private int value;
    private Node parent;
    private Node left;
    private Node right;

    /**
     * Constructor of a Tree Node.
     * @param newValue value of the node.
     */
    public Node(int newValue) {
        this.value = newValue;
    }

    /**
     *
     * @return true if left node exist, false otherwise.
     */
    public boolean hasLeft() {
        return (left != null);
    }

    /**
     *
     * @return true if right node exist, false otherwise.
     */
    public boolean hasRight() {
        return (right != null);
    }

    /**
     *
     * @return true if left node or right node exist, false otherwise.
     */
    public boolean hasSons(){
        return hasRight() || hasLeft();
    }

    /**
     *
     * @return true if Parent node exist, false otherwise.
     */
    public boolean hasParent(){
        return (this.getParent() != null);
    }

    /**
     *
     * @return return a left child of current node.
     */
    public Node getLeft() {
        return left;
    }

    /**
     *
     * @return return a right child of current node.
     */
    public Node getRight() {
        return right;
    }

    /**
     *
     * @return return a Parent node of current node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     *
     * @return return the data of a node.
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param parent setter for parent node.
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     *
     * @param left setter for left node.
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     *
     * @param right setter for right node.
     */
    public void setRight(Node right) {
        this.right = right;
    }
}
