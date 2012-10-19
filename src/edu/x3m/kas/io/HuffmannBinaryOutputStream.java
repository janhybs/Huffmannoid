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
 * Class for writing huffmann's code.
 *
 * @see HuffmannEncoder
 * @author Hans
 */
public class HuffmannBinaryOutputStream extends DataOutputStream {


    public static final int BUFFER_SIZE = 4 * 1024;
    //
    byte[] buffer = new byte[BUFFER_SIZE];
    int bufferIndex = 0;



    /**
     * Construct HuffmannBinaryOutputStream
     *
     * @param file in which will be huffmann's code written
     * @throws FileNotFoundException
     */
    public HuffmannBinaryOutputStream (File file) throws FileNotFoundException {
        super (new FileOutputStream (file));
    }



    /**
     * Writes several header bytes
     *
     * @throws IOException
     */
    public void writePrequel () throws IOException {
        write (HuffmannEncoder.PREQUEL);
    }



    /**
     * Writes alphabet
     *
     * @param alphabet
     * @see SimpleNode
     * @throws IOException
     */
    public void writeAlphabet (final SimpleNode[] alphabet) throws IOException {
        int validChars = 0, i;
        boolean isInt;
        long max = Long.MIN_VALUE;
        SimpleNode node;


        //# counting valid chars
        for (i = 0; i < alphabet.length; i++) {
            if (alphabet[i] != null) {
                validChars++;
                max = alphabet[i].count > max ? alphabet[i].count : max;
            }
        }

        isInt = max < 255 ? false : true;
        writeBoolean (isInt);
        writeInt (validChars);
        for (i = 0; i < alphabet.length; i++) {
            node = alphabet[i];
            if (node != null) {
                writeByte ((char) node.character);
                
                if (isInt) writeInt (node.count);
                else writeByte (node.count);
                
            }
        }
    }



    /**
     * Writes ascii string
     *
     * @param string to be written
     * @throws IOException
     */
    private void write (String string) throws IOException {
        write (string.getBytes ());
    }



    /**
     * Formats and writes last incomplete byte
     *
     * @param string byte to be written
     * @throws IOException
     */
    public void writeLastByte (String string) throws IOException {
        write (new byte[] {BinaryUtil.format (string)});
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
