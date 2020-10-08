package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class BinarySearchTree implements Iterable<Integer> {

    static final int NOT_FOUND_ERROR = -1;
    static final int LEAF_HEIGHT = 0;

    int size;
    public Node root;
    Node currentNode;
    BstIterator iterator;


    public BinarySearchTree() {
        size = 0;
        root = null;
        currentNode = null;
    }

    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added,
     * false otherwise.
     */
    public boolean add(int newValue) {
        if (contains(newValue) == NOT_FOUND_ERROR) {
            Node toAdd = new Node(newValue);
            if (root == null) {
                root = toAdd;
                currentNode = toAdd;
            }
            else {
                if (newValue < currentNode.getValue())
                    currentNode.setLeft(toAdd);
                else currentNode.setRight(toAdd);
                toAdd.setParent(currentNode);
            }
            size++;
            return true;
        }
        return false;
    }

    /**
     *
     * @param node the node to check the height for.
     * @return height of the current node in the tree.
     */
    int getNodeHeight(Node node){
        if (node == null){return -1;}
        return Math.max(getNodeHeight(node.getLeft()),getNodeHeight(node.getRight())) + 1;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     *
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public abstract boolean delete(int toDelete);

    /**
     * Check whether the tree contains the given input value.
     *
     * @param searchVal the value to search for.
     * @return the depth of the node (0 for the root) with the given value if it was found in
     * the tree, -1 otherwise.
     */
    public int contains(int searchVal) {
        int depth = 0;
        currentNode = root;
        if (currentNode != null) {
            do {
                if (searchVal == currentNode.getValue())
                    return depth;
                else if (searchVal < currentNode.getValue()) {
                    if (currentNode.getLeft() == null)
                        break;
                    currentNode = currentNode.getLeft();
                } else {
                    if (currentNode.getRight() == null)
                        break;
                    currentNode = currentNode.getRight();
                }
                depth++;
            } while (getNodeHeight(currentNode) >= LEAF_HEIGHT);
        }
        return NOT_FOUND_ERROR;
    }

    /**
     * @return the number of size in the tree.
     */
    public int size() {
        return size;
    }

    /**
     *
     * @param next the which the method looking for its successor.
     * @return a node represent the successor if there is any. else return null.
     */
    static Node successor(Node next) {
        if (next.hasRight()) {
            next = next.getRight();
            while (next.hasLeft())
                next = next.getLeft();
            return next;
        } else while (next.hasParent()) {
            if (next.getValue() < next.getParent().getValue()) {
                next = next.getParent();
                return next;
            }
            else next = next.getParent();
        }
        return null;

    }

    /**
     *
     * @return the node with the smallest value in the tree.
     */
    private Node getMinimalNode() {
        Node minimumNode = root;
        while (minimumNode.getLeft() != null)
            minimumNode = minimumNode.getLeft();
        return minimumNode;
    }

    /**
     * Iterator class for a Binary Search Tree.
     */
    class BstIterator implements Iterator<Integer> {
        private Node next;

        /**
         * default Constructor.
         */
        BstIterator() {
            this.next = getMinimalNode();
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return next != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Integer next() {
            Node current = next;
            if (hasNext()) {
                next = successor(next);
                return current.getValue();
            }
            else throw new NoSuchElementException();
        }
    }
}
