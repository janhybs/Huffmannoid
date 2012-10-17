package edu.x3m.kas.core.structures;


/**
 * @author Hans
 */
public class SimpleNode extends AbstractNode {


    public int character;
    public String finalCode;
    public int codeLength;



    public SimpleNode (int character) {
        this.character = character;
    }



    public SimpleNode (int character, String code) {
        this.character = character;
        this.finalCode = code;
        this.codeLength = code.length ();
    }



    @Override
    public String toString () {
        return String.format ("[N %s %d %s]", (char) character, count, finalCode);
    }



    public void endCode () {
        finalCode = code.reverse ().toString ();
        codeLength = finalCode.length ();
    }
}
