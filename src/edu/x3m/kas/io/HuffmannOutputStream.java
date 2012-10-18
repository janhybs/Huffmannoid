package edu.x3m.kas.io;


import edu.x3m.kas.core.HuffmannEncoder;
import edu.x3m.kas.core.structures.SimpleNode;
import edu.x3m.kas.utils.BinaryUtil;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Hans
 */
public class HuffmannOutputStream extends DataOutputStream {


    public static final int BUFFER_SIZE = 8 * 1024;
    //
    byte[] buffer = new byte[BUFFER_SIZE];
    int bufferIndex = 0;



    public HuffmannOutputStream (File file) throws FileNotFoundException {
        super (new FileOutputStream (file));
    }



    public void writePrequel () throws IOException {
        write (HuffmannEncoder.PREQUEL);
    }



    public void writeAlphabet (final SimpleNode[] ABC) throws IOException {
        int validChars = 0, i;
        SimpleNode node;


        //# counting valid chars
        for (i = 0; i < ABC.length; i++)
            if (ABC[i] != null) validChars++;

        writeInt (validChars);
        for (i = 0; i < ABC.length; i++) {
            node = ABC[i];
            if (node != null) {
                write ((char) node.character);
                writeInt (node.count);

                //# old version encoding...
                //writeByte (node.codeLength);
                //write (node.finalCode);
            }
        }
    }



    public void write (String s) throws IOException {
        write (s.getBytes ());
    }



    public void writeLastByte (String s) throws IOException {
        write (new byte[] {BinaryUtil.format (s)});
    }



    @Override
    public synchronized void write (int b) throws IOException {
        buffer[bufferIndex++] = (byte) b;

        if (bufferIndex >= BUFFER_SIZE) {
            write (buffer);
            bufferIndex = 0;
        }
    }



    @Override
    public void close () throws IOException {
        if (bufferIndex > 0)
            write (buffer, 0, bufferIndex);

        super.close ();
    }
}
