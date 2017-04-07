/**
 * Created by GozdeDogan on 30.03.2017.
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Class for a binary tree that stores type E objects.
 * @author Koffman and Wolfgang
 **/
public class BinaryTree<E> implements Serializable{
    /** Class to encapsulate a tree node. */
    protected static class Node<E> implements Serializable {
        // Data Fields

        /** The information stored in this node. */
        protected E data;
        /** Reference to the left child. */
        protected Node<E> left;
        /** Reference to the right child. */
        protected Node<E> right;

        /**
         * Construct a node with given data and no children.
         * @param data The data to store in this node
         */
        public Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        public Node(Node<E> node){
            this.data = node.data;
            this.left = node.left;
            this.right = node.right;
        }

        /**
         * Returns a string representation of the node.
         * @return A string representation of the data fields
         */
        @Override
        public String toString() {
            return data.toString();
        }
    }

    /**
     * http://cs.nyu.edu/courses/spring10/V22.0101-002/html/BinaryTree.html sitesinden yararlanildi.
     * BinaryTree elemanalari uzerinde pre-order olarak gezmeyi saglaya Iterator class'i
     * @param <E>
     */
    protected static class BinaryTreeIter<E> implements Iterator<E>{
        private ArrayList<E> list = new ArrayList<E>(); //BinaryTree elemanlarinin atilacagi liste
        private int current = 0;

        /**
         * one-parameter constructor
         * @param root
         */
        public BinaryTreeIter(Node<E> root) {
            preorder(root);
        }

        /**
         * preorder traversal from a subtree
         */
        private void preorder(Node<E> root) {
            if (root != null) {
                list.add(root.data);
                preorder(root.left);
                preorder(root.right);
            }
        }

        /**
         * Next element for traversing
         */
        public boolean hasNext() {
            if (current < list.size())
                return true;
            return false;
        }

        /**
         * Get the current element and move cursor to the next
         */
        public E next() {
            return list.get(current++);
        }

        /**
         * remove metodunun kullanilmamasi saglandi.
         */
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    /** The root of the binary tree */
    protected Node<E> root;

    /** Construct an empty BinaryTree */
    public BinaryTree() {
        root = null;
    }

    /**
     * Construct a BinaryTree with a specified root.
     * Should only be used by subclasses.
     * @param root The node that is the root of the tree.
     */
    protected BinaryTree(Node<E> root) {
        this.root = root;
    }

    /**
     * Constructs a new binary tree with data in its root,leftTree
     * as its left subtree and rightTree as its right subtree.
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        root = new Node<E>(data);
        if (leftTree != null) {
            root.left = leftTree.root;
        } else {
            root.left = null;
        }
        if (rightTree != null) {
            root.right = rightTree.root;
        } else {
            root.right = null;
        }
    }

    /**
     * Iterator<E> return eden iterator metodu implement edildi.
     */
    public Iterator<E> iterator(){
        return new BinaryTreeIter<E>(root);
    }


    /**
     * Return the left subtree.
     * @return The left subtree or null if either the root or
     * the left subtree is null
     */
    public BinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new BinaryTree<E>(root.left);
        } else {
            return null;
        }
    }

    /**
     * Return the right sub-tree
     * @return the right sub-tree or
     *         null if either the root or the
     *         right subtree is null.
     */
    public BinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new BinaryTree<E>(root.right);
        } else {
            return null;
        }
    }

    /**
     * Return the data field of the root
     * @return the data field of the root
     *         or null if the root is null
     */
    public E getData() {
        if (root != null) {
            return root.data;
        } else {
            return null;
        }
    }

    /**
     * Determine whether this tree is a leaf.
     * @return true if the root has no children
     */
    public boolean isLeaf() {
        return (root == null || (root.left == null && root.right == null));
    }

    /**
     * toString metodu iterator kullanilarak implement edildi.
     * iterator seklinde elemanlar StringBuilder'e eklenir.
     * StringBuilder return edilir.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PRE-ORDER TRAVELS ==>> binaryTree:\n[");
        Iterator<E> iter = iterator();
        while(iter.hasNext()) {
            E item = iter.next();
            sb.append(item);
            if(iter.hasNext())
                sb.append(" ==>> ");
        }
        sb.append("]");
        //System.out.println("sb: " + sb.toString());
        return sb.toString();
    }

    /**
     * Perform a preorder traversal.
     * @param node The local root
     * @param depth The depth
     * @param sb The string buffer to save the output
     */
    private void preOrderTraverse(Node<E> node, int depth,
                                  StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    /**
     * Arrayden elemanlar alinir ve BinaryTree oluþturulur.
     * @param arr
     * @param size
     * @return
     * @throws IOException
     */
    public static BinaryTree <Integer> readBinaryTree(Integer[] arr, int size) throws IOException {
        BinaryTree<Integer> bt = readBinaryTree(arr, size, 0);
        //System.out.println(bt.toString());
        return bt;
    }

    /**
     * Arrayden elemanlar recursive olarak alinir ve BinaryTree oluþturulur.
     * @param arr
     * @param size
     * @param index
     * @return
     * @throws IOException
     */
    private static BinaryTree <Integer> readBinaryTree(Integer[] arr, int size, int index) throws IOException{
        //System.out.println("index: " + index);
        if (index >= size)
            return null;
        else{
            BinaryTree<Integer> leftTree = null;
            BinaryTree<Integer> rightTree = null;

            leftTree = readBinaryTree(arr, size, index+1);
            //rightTree = readBinaryTree(arr, size, index+2);

            //System.out.println("index: " + index + "\tindex+1: " + index+1 + "\tindex+2: " + index+2);
            return new BinaryTree<Integer>(arr[index], leftTree, rightTree);
        }
    }

}
