package edu.x3m.kas.io;


import edu.x3m.kas.utils.BinaryUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Hans
 */
public class HuffmannOutputStream extends FileOutputStream {


    public HuffmannOutputStream (File file) throws FileNotFoundException {
        super (file);
    }



    public void writeLastByte (String s) throws IOException {
        write (new byte[] {BinaryUtil.format (s)});
    }
}
