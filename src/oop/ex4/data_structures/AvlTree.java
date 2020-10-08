package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Avl Tree class.
 */
public class AvlTree extends BinarySearchTree implements Iterable<Integer> {

    static private final int BINARY_FACTOR = 2;


    /**
     * The default constructor.
     */
    public AvlTree() {
        super();
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input
     * array.
     *
     * @param data the values to add to tree.
     */
    public AvlTree(int[] data) {
        super();
        if (data.length > 0)
            for (int value : data)
                add(value);
    }

    /**
     * A copy constructor that creates a deep copy of the given oop.ex4.data_structures.AvlTree. The new tree
     * contains all the values of the given tree, but not necessarily in the same structure.
     *
     * @param avlTree an AVL tree.
     */
    public AvlTree(AvlTree avlTree) {
        super();
        if (avlTree.root != null)
            for (int value : avlTree)
                add(value);
    }

    public boolean add(int newValue) {
        if (super.add(newValue)) {
            while (!doBalance(currentNode) && currentNode.hasParent())
                currentNode = currentNode.getParent();
            return true;
        }
        return false;
    }

    public boolean delete(int toDelete) {
        if (contains(toDelete) != NOT_FOUND_ERROR) {
            if (!currentNode.hasSons()) { // no childrens.
                if (currentNode.hasParent()) {
                    if (currentNode.getValue() < currentNode.getParent().getValue())
                        currentNode.getParent().setLeft(null);
                    else currentNode.getParent().setRight(null);
                    currentNode = currentNode.getParent();
                } else root = null;
            } else if (currentNode.hasLeft() && !currentNode.hasRight()) { // only left child.
                currentNode.getLeft().setParent(currentNode.getParent());
                if (currentNode.getValue() < currentNode.getParent().getValue())
                    currentNode.getParent().setLeft(currentNode.getLeft());
                else currentNode.getParent().setRight(currentNode.getLeft());
            } else if (currentNode.hasRight() && !currentNode.hasLeft()) { // only right child.
                currentNode.getRight().setParent(currentNode.getParent());
                if (currentNode.getValue() < currentNode.getParent().getValue())
                    currentNode.getParent().setLeft(currentNode.getRight());
                else currentNode.getParent().setRight(currentNode.getRight());
            } else {
                Node supporter = successor(currentNode);
                delete(supporter.getValue());
                supporter.setParent(currentNode.getParent());
                if (!currentNode.hasParent())
                    root = supporter;
                supporter.setRight(currentNode.getRight());
                supporter.setLeft(currentNode.getLeft());
            }
            while (currentNode != null) {
                doBalance(currentNode);
                currentNode = currentNode.getParent();
            }
            this.size--;
            return true;
        }
        return false;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new BstIterator();
    }

    /**
     * A method to consider and handle 4 situations of un-balanced tree.
     *
     * @param node A node represent the root of a tree.
     * @return True if the handled tree is now Avl Tree, false if node is null.
     */
    private boolean doBalance(Node node) {
        int balanceFactor = balanceFactor(node);
        if (Math.abs(balanceFactor) <= 1)
            return false;
        if (balanceFactor >= 2) {
            if (balanceFactor(node.getLeft()) >= 1) {
                rotateRight(node);
            } else if (balanceFactor(node.getLeft()) <= -1) {
                rotateLeft(node.getLeft());
                rotateRight(node);
            }
        } else if (balanceFactor <= -2) {
            if (balanceFactor(node.getRight()) <= -1) {
                rotateLeft(node);
            } else if (balanceFactor(node.getRight()) >= 1) {
                rotateRight(node.getRight());
                rotateLeft(node);
            }
        }
        return true;
    }

    /**
     * @param node the node to calcuate the balance for.
     * @return the difference bitween height of left node and right node.
     */
    private int balanceFactor(Node node) {
        return (getNodeHeight(node.getLeft()) - getNodeHeight(node.getRight()));
    }

    /**
     * A method to used to balance the tree via rotating the node left.
     *
     * @param pivot A node in the tree to rotate left.
     */
    private void rotateLeft(Node pivot) {
        Node supporter = pivot.getRight();
        pivot.setRight(supporter.getLeft());
        if (supporter.hasLeft())
            supporter.getLeft().setParent(pivot);
        supporter.setLeft(pivot);
        if (pivot.hasParent()) {
            if (pivot.getValue() < pivot.getParent().getValue())
                pivot.getParent().setLeft(supporter);
            else pivot.getParent().setRight(supporter);
        } else root = supporter;
        supporter.setParent(pivot.getParent());
        pivot.setParent(supporter);
    }

    /**
     * A method to used to balance the tree via rotating the node right.
     *
     * @param pivot A node in the tree to rotate right.
     */
    private void rotateRight(Node pivot) {
        Node supporter = pivot.getLeft();
        pivot.setLeft(supporter.getRight());
        if (supporter.hasRight())
            supporter.getRight().setParent(pivot);
        supporter.setRight(pivot);
        if (pivot.hasParent()) {
            if (pivot.getValue() < pivot.getParent().getValue())
                pivot.getParent().setLeft(supporter);
            else pivot.getParent().setRight(supporter);
        } else root = supporter;
        supporter.setParent(pivot.getParent());
        pivot.setParent(supporter);
    }

    /**
     * Calculates the minimum number of size in an AVL tree of height h.
     *
     * @param h the height of the tree (a non􀀀negative number) in question.
     * @return the minimum number of size in an AVL tree of the given height.
     */
    public static int findMinNodes(int h) {
        if (h < 0)
            return 0;
        int a = 1;
        int b = 2;
        int c;
        for (int i = 1; i < h; i++) {
            c = a + b + 1;
            a = b;
            b = c;
        }
        return b;
    }

    /**
     * Calculates the maximum number of size in an AVL tree of height h.
     *
     * @param h the height of the tree (a non􀀀negative number) in question.
     * @return the maximum number of size in an AVL tree of the given height.
     */
    public static int findMaxNodes(int h) {
        return (int) Math.pow(BINARY_FACTOR, (h + 1)) - 1;
    }

}
