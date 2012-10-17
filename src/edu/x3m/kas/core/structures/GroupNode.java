package edu.x3m.kas.core.structures;


/**
 *
 * @author Hans
 */
public class GroupNode extends AbstractNode {


    protected SimpleNode left, right;



    public GroupNode (SimpleNode left, SimpleNode right) {
        this.left = left;
        this.right = right;
        this.count = (left == null ? 0 : left.count) + (right == null ? 0 : right.count);
        this.createTree ();
    }



    @Override
    protected void append (char c) {
        if (left != null)
            left.append (c);
        if (right != null)
            right.append (c);
    }



    public final void createTree () {
        if (left != null)
            left.append ('0');
        if (right != null)
            right.append ('1');
    }
}
