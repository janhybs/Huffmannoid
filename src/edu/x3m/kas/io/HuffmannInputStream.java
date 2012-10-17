package edu.x3m.kas.io;


import edu.x3m.kas.core.HuffmannEncoder;
import edu.x3m.kas.core.structures.SimpleNode;
import edu.x3m.kas.utils.BinaryUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.activation.UnsupportedDataTypeException;


/**
 *
 * @author Hans
 */
public class HuffmannInputStream extends DataInputStream {


    public static final int BUFFER_SIZE = 2;//8 * 1024;
    //
    byte[] buffer = new byte[BUFFER_SIZE];
    public final long fileSize;
    public long bytesLeft;



    public HuffmannInputStream (File file) throws FileNotFoundException {
        super (new FileInputStream (file));
        fileSize = file.length ();
        bytesLeft = fileSize;
    }



    public byte[] getNextNormalBuffer () throws IOException {
        int size = read (buffer);
        if (size == -1) return null;
        bytesLeft -= size;

        byte[] result = new byte[size];
        System.arraycopy (buffer, 0, result, 0, size);
        return result;
    }



    public byte[] getNextBinaryBuffer (int extraSpace) throws IOException {
        if (bytesLeft - BUFFER_SIZE == 1) {
            buffer = new byte[BUFFER_SIZE + 1];
        }
        int size = read (buffer);
        if (size == -1) return null;
        bytesLeft -= size;

        byte[] result = new byte[size];
        System.arraycopy (buffer, 0, result, 0, size);
        result = BinaryUtil.convertIntTo10 (result);
        if (extraSpace == 0)
            return result;

        byte[] bigger = new byte[result.length + extraSpace];
        System.arraycopy (result, 0, bigger, extraSpace, result.length);
        return bigger;
    }



    public boolean hasNext () {
        return bytesLeft > 0;
    }



    public byte[] getNextBinaryBuffer () throws IOException {
        return getNextBinaryBuffer (0);
    }



    public void printBinaryAll () throws IOException {

        int ch, i = 0;
        while ((ch = read ()) != -1) {
            System.out.print (BinaryUtil.toBinary (ch) + "-");
            if (i++ != 0 && i % 16 == 0) System.out.println ();
        }
        System.out.println ("");
    }



    public SimpleNode[] getAlphabet () throws IOException {
        byte[] buffer = new byte[HuffmannEncoder.PREQUEL.length ()];
        bytesLeft -= HuffmannEncoder.PREQUEL.length ();

        SimpleNode[] result;
        read (buffer);

        String s = new String (buffer);

        if (!s.equalsIgnoreCase (HuffmannEncoder.PREQUEL))
            throw new UnsupportedDataTypeException ();

        int codeLength, ch;
        int validChars = readByte ();
        bytesLeft--;

        result = new SimpleNode[validChars];
        for (int i = 0; i < validChars; i++) {
            ch = (readByte () + 256) % 256;
            codeLength = (readByte () + 256) % 256;
            buffer = new byte[codeLength];

            read (buffer);
            bytesLeft -= 1 + 1 + codeLength;

            result[i] = new SimpleNode (ch, new String (buffer));
        }

        return result;
    }
}
