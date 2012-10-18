package edu.x3m.kas.io;


import edu.x3m.kas.utils.BinaryUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Extension of {@link DataInputStream}. Provides buffering and binary printing
 * @author Hans
 */
public class HuffmannInputStream extends DataInputStream {


    public static final int BYTE = 8;
    public static final int BUFFER_SIZE = 8 * 1024;
    //
    byte[] buffer = new byte[BUFFER_SIZE];



    public HuffmannInputStream (File file) throws FileNotFoundException {
        super (new FileInputStream (file));
    }



    public byte[] getNextNormalBuffer () throws IOException {
        int size = read (buffer);
        if (size == -1) return null;

        byte[] result = new byte[size];
        System.arraycopy (buffer, 0, result, 0, size);
        return result;
    }



    public void printBinaryAll () throws IOException {

        int ch, i = 0;
        while ((ch = read ()) != -1) {
            System.out.print (BinaryUtil.toBinary (ch) + "-");
            if (i++ != 0 && i % 16 == 0) System.out.println ();
        }
        System.out.println ("");
    }
}
