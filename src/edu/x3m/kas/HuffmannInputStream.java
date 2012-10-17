package edu.x3m.kas;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *
 * @author Hans
 */
public class HuffmannInputStream extends FileInputStream {


    public static final int BUFFER_SIZE = 8 * 1024;
    //
    byte[] buffer = new byte[BUFFER_SIZE];



    public HuffmannInputStream (File file) throws FileNotFoundException {
        super (file);
    }



    public byte[] getNextNormalBuffer () throws IOException {
        int size = read (buffer);
        if (size == -1) return null;

        byte[] result = new byte[size];
        System.arraycopy (buffer, 0, result, 0, size);
        return result;
    }
}
