package edu.x3m.kas.core;


import edu.x3m.kas.io.HuffmannInputStream;
import edu.x3m.kas.io.HuffmannOutputStream;
import edu.x3m.kas.utils.Timer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Hans
 */
public class Main {
    
    private static Timer TIMER = new Timer ();


    public static void main (String[] args) throws FileNotFoundException, IOException, InterruptedException, UnsupportedFileException {

        
        //File f1 = new File ("./test-files/test-01");
        //File f2 = new File ("./test-files/test-01.x3m.huff");
        //File f1 = new File ("./test-files/screenshots.bmp");
        //File f2 = new File ("./test-files/screenshots.bmp.x3m.huff");
        //File f1 = new File ("./test-files/club-bleach.gif");
        //File f2 = new File ("./test-files/club-bleach.gif.x3m.huff");
        File f1 = new File ("./test-files/trololo.flv");
        File f2 = new File ("./test-files/trololo.flv.x3m.huff");
        
        
        
        TIMER.tic ();
        HuffmannEncoder h = new HuffmannEncoder (f1);
        h.encode ();
        TIMER.tac ("Encoded: ");
        
        //new HuffmannInputStream (f2).printBinaryAll ();

        
        TIMER.tic ();
        HuffmannDecoder d = new HuffmannDecoder (f2);
        d.decode ();
        TIMER.tac ("Decoded: ");
    }
}
