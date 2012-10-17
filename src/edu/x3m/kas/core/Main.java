package edu.x3m.kas.core;


import edu.x3m.kas.io.HuffmannInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Hans
 */
public class Main {


    public static void main (String[] args) throws FileNotFoundException, IOException {

        
        File f = new File ("./test-files/test-01");
        HuffmannEncoder h = new HuffmannEncoder (f);
        h.encode ();

        h.printAlphabet ();
        
        new HuffmannInputStream (new File ("./test-files/test-01.x3m.huff")).printBinaryAll ();
        new HuffmannInputStream (new File ("./test-files/test-01")).printBinaryAll ();

    }
}
