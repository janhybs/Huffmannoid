package edu.x3m.kas.core.structures;


/**
 * @author Hans
 */
public class SimpleNode extends AbstractNode {


    public int character;



    public SimpleNode (int character) {
        this.character = character;
    }



    @Override
    public String toString () {
        return String.format ("[N %s %d]", (char)character, count);
    }
    
    
}
