package edu.x3m.kas.core;


import edu.x3m.kas.core.structures.AbstractNode;
import edu.x3m.kas.core.structures.GroupNode;
import edu.x3m.kas.core.structures.SimpleNode;
import java.util.PriorityQueue;


/**
 * Class containgn global fields and methods when using Huffmann's encoding and decoding.
 * 
 * @author Hans
 */
public class Huffmann {


    /** Value which will be written at the beginning of each Huffmann's encoded file */
    public static final String PREQUEL = "x3m-huff";



    /**
     * Method creates binary tree from given alphabet.
     * 
     * @param alphabet
     * @return root of the tree
     */
    public static GroupNode createBinaryTree (SimpleNode[] alphabet) {
        PriorityQueue<AbstractNode> items = new PriorityQueue<> ();
        GroupNode root = null;
        int mergesLeft;

        //# pushing only used items to array
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == null) continue;
            items.add (alphabet[i]);
        }

        //# defining how many steps it'll take to created weighted tree
        mergesLeft = items.size () - 1;


        //# no tree, so it's easy
        if (mergesLeft == 0) {
            items.add (root = new GroupNode (items.poll (), null));
        } else {

            //# removing two last items, and add new one
            while (mergesLeft-- > 0)
                items.add (root = new GroupNode (items.poll (), items.poll ()));
        }


        //# closing codes
        for (int i = 0; i < alphabet.length; i++)
            if (alphabet[i] != null) alphabet[i].endCode ();

        return root;
    }
}
