import java.io.*;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Created by GozdeDogan on 1.04.2017.
 */
public class q1_hw4_131044019 {
    public static void main(String[] arg) throws IOException {
        String fileName = new String("test.txt");
        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        BinaryTree<Integer> bt = new BinaryTree<Integer>();

        bt = readFile(fileName, bst);

        System.out.println("BinaryTree, PRE-ORDER travel:");
        System.out.println(bt.toString());
        System.out.println("\n");
        System.out.println("BinarySearchTree, LEVEL ORDER travel:");
        System.out.println(bst.toString());

    }

    private static BinaryTree<Integer> readFile(String fileName, BinarySearchTree<Integer> bst) throws IOException {
        try {
            //int size = 0;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            Integer files[] = new Integer[50000];
            int sizeFiles = 0;
            String line = new String();
            int temp;
            String temps;
            // read BinarySearchTree
            while((line = bufferedReader.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line, " ");
                while (tok.hasMoreElements()) { /**Her satir ait oldugu arraye eklenir.*/
                    temps = (String)tok.nextElement();
                    temp = Integer.parseInt(temps);
                    //System.out.println("temp:" + temp);
                    bst.add(temp);
                    files[sizeFiles++] = temp;
                    //size++;
                    //System.out.println();
                }
            }
            bufferedReader.close();
            sizeFiles++;
            //System.out.println("size: " + sizeFiles);

            //System.out.println("\n\nsize:" + size + "\n\n");
            return BinaryTree.readBinaryTree(files, sizeFiles);

        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return null;
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
            return null;
        }
    }
}