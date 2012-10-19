package edu.x3m.kas.io;


import edu.x3m.kas.core.Huffmann;
import edu.x3m.kas.core.HuffmannDecoder;
import edu.x3m.kas.core.structures.SimpleNode;
import edu.x3m.kas.utils.BinaryUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Class for writing huffmann's code.
 *
 * @see HuffmannDecoder
 */
public class HuffmannBinaryInputStream extends DataInputStream {


    public static final int BYTE = 8;
    public static final int BUFFER_SIZE = 4 * 1024;
    //
    public final long bytesTotal;
    private long bytesLoaded;
    private int bufferSize = -1;
    private byte[] buffer;
    private byte[] result;
    private boolean prequelReaded;
    private boolean isSupported;
    private int reminderLength;
    private boolean firstBinaryRead = true;
    private byte[] reminderBuffer;



    /**
     * Construct HuffmannBinaryInputStream
     *
     * @param file containing huffmann's code
     * @throws FileNotFoundException
     */
    public HuffmannBinaryInputStream (File file) throws FileNotFoundException {
        super (new FileInputStream (file));
        bytesTotal = file.length ();
        bytesLoaded = 0;

    }



    /**
     * Tells whether has this file huffmann's code
     *
     * @return t/f
     *
     * @throws IOException
     */
    public boolean isSupported () throws IOException {
        if (prequelReaded) return isSupported;

        buffer = new byte[Huffmann.PREQUEL.length ()];
        readBuffer (buffer);
        prequelReaded = true;

        //# comparing
        return isSupported = new String (buffer).equalsIgnoreCase (Huffmann.PREQUEL);
    }



    /**
     * Reads alphabet from header
     *
     * @return array of {@link SimpleNode}
     *
     * @throws IOException
     */
    public SimpleNode[] readAlphabet () throws IOException {
        SimpleNode[] result = new SimpleNode[256];
        boolean isInt = readNextBoolean ();
        int validChars = readNextInt ();

        int ch, count;
        for (int i = 0; i < validChars; i++) {
            ch = readNextChar ();
            count = isInt ? readNextInt () : readNextChar ();
            result[ch] = new SimpleNode (ch, count);
        }

        return result;
    }



    /**
     * Method reads next "piece" od file. Its size can vary. Depending on remaining file size and
     * previous not-used bits.
     *
     * @return array of bytes (e.g [240, 15]). reminder + buffer
     *
     * @throws IOException
     */
    public byte[] readNextBinaryBuffer () throws IOException {

        //# when comming to the end, expand buffer so whole rest of the file os readed
        if ((bytesTotal - bytesLoaded) < BUFFER_SIZE * 2) {
            bufferSize = calculateBufferSize ();
            buffer = new byte[bufferSize];
            firstBinaryRead = false;
        }

        //# first binary read
        if (firstBinaryRead) {
            buffer = new byte[BUFFER_SIZE];
            bufferSize = BUFFER_SIZE;
            firstBinaryRead = false;
        }


        //# creating result array and copying previous unnused bits
        result = new byte[bufferSize * BYTE + reminderLength];
        if (reminderLength > 0)
            System.arraycopy (reminderBuffer, 0, result, 0, reminderLength);

        //# reading next buffer
        int size = readBuffer (buffer);
        if (size == -1 || size == 0) return null;

        //# converting into result
        BinaryUtil.convertIntTo10 (buffer, result, reminderLength, size);

        return result;
    }



    /**
     * Tells wheter there is another data in this file.
     *
     * @return t/f
     */
    public boolean hasNext () {
        return bytesTotal > bytesLoaded;
    }



    /**
     * Sets reminder new value and saves those bytes for later use
     *
     * @see #readNextBinaryBuffer()
     * @param reminderLength value of reminder
     */
    public void setReminderLength (int reminderLength) {
        this.reminderLength = reminderLength;
        reminderBuffer = new byte[reminderLength];
        System.arraycopy (result, result.length - reminderLength, reminderBuffer, 0, reminderLength);
    }



    /**
     * Method return how many valid bits are in last byte of data When whole file has been loaded.
     *
     * @return byte from 0 to 8
     */
    public int getValidBitsInLastByte () {
        if (hasNext ()) return result.length;

        return ((int) buffer[buffer.length - 1] + 256) % 256;
    }


    //--------------------------------------
    //# Privates
    //--------------------------------------

    private int readBuffer (byte[] buffer) throws IOException {
        return readBuffer (buffer, 0, buffer.length);
    }



    private int readBuffer (byte[] buffer, int pos, int length) throws IOException {
        final int tmp = read (buffer, pos, length);
        bytesLoaded += tmp;
        return tmp;
    }



    private int readNextInt () throws IOException {
        bytesLoaded += 4;
        return readInt ();
    }



    private int readNextChar () throws IOException {
        bytesLoaded += 1;
        return ((int) readByte () + 256) % 256;
    }



    private int calculateBufferSize () {
        final int bytesLeft = (int) (bytesTotal - bytesLoaded);
        if (bytesLeft <= BUFFER_SIZE)
            return bytesLeft;

        double bufferCount = (bytesLeft / BUFFER_SIZE);
        return (int) Math.ceil (bytesLeft / bufferCount);
    }



    private boolean readNextBoolean () throws IOException {
        bytesLoaded += 1;
        return readBoolean ();
    }
}
