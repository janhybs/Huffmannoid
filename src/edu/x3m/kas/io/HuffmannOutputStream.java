package edu.x3m.kas.io;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * Simple extension of {@link DataOutputStream}
 * @author Hans
 */
public class HuffmannOutputStream extends DataOutputStream {


    public HuffmannOutputStream (File file) throws FileNotFoundException {
        super (new FileOutputStream (file));
    }
}
