package edu.x3m.kas.core;


import edu.x3m.kas.io.HuffmannInputStream;
import edu.x3m.kas.io.HuffmannOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Hans
 */
public class Main {


    public static void main (String[] args) throws FileNotFoundException, IOException {

        
        File f1 = new File ("./test-files/test-01");
        File f2 = new File ("./test-files/test-01.x3m.huff");
        
        
        HuffmannEncoder h = new HuffmannEncoder (f1);
        h.encode ();

        
        HuffmannDecoder d = new HuffmannDecoder (f2);
        d.decode ();
    }
}
