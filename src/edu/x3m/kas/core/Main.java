package edu.x3m.kas.core;


import edu.x3m.kas.io.HuffmannInputStream;
import edu.x3m.kas.monitors.IHuffmannMonitor;
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


        File f1 = new File ("C:/huff/testfiles/test-01");
        File f2 = new File ("C:/huff/testfiles/test-01.x3m.huff");
        //File f1 = new File ("C:/huff/testfiles/screenshots.bmp");
        //File f2 = new File ("C:/huff/testfiles/screenshots.bmp.x3m.huff");
        //File f1 = new File ("C:/huff/testfiles/club-bleach.gif");
        //File f2 = new File ("C:/huff/testfiles/club-bleach.gif.x3m.huff");
        //File f1 = new File ("C:/huff/testfiles/trololo.flv");
        //File f2 = new File ("C:/huff/testfiles/trololo.flv.x3m.huff");
        //File f1 = new File ("C:/huff/testfiles/galaxy.mp4");
        //File f2 = new File ("C:/huff/testfiles/galaxy.mp4.x3m.huff");



        TIMER.tic ();
        HuffmannEncoder h = new HuffmannEncoder (f1);
        h.setHuffmannMonitor (new ProgressMonitor ());
        h.encode ();
        TIMER.tac ("Encoded: ");

        new HuffmannInputStream (f2).printBinaryAll ();


        TIMER.tic ();
        HuffmannDecoder d = new HuffmannDecoder (f2);
        d.setHuffmannMonitor (new ProgressMonitor ());
        d.decode ();
        TIMER.tac ("Decoded: ");
    }


    static class ProgressMonitor implements IHuffmannMonitor {


        @Override
        public void onSectionStart (String section) {
            System.out.println ("start   : " + section);
        }



        @Override
        public void onSectionEnd (String section) {
            System.out.println ("end     : " + section);
        }



        @Override
        public void onSectionProgress (String section, int prc) {
            System.out.println ("progress: " + section + " (" + prc + "%)");
        }
    }
}
