import oop.ex4.data_structures.AvlTree;

public class TEST {
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        AvlTree tree = new AvlTree(arr);

        tree.add(5);
        System.out.println(tree.delete(5));

        BTreePrinter.printNode(tree.root);
        System.out.println(tree.size());
    }
}
