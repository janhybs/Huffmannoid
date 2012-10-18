package edu.x3m.kas.core;


import edu.x3m.kas.core.structures.GroupNode;
import edu.x3m.kas.core.structures.SimpleNode;
import edu.x3m.kas.io.HuffmannBinaryInputStream;
import edu.x3m.kas.io.HuffmannOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Class for decoding encoded file which used Huffmann's algorithm
 *
 * @author Hans
 */
public class HuffmannDecoder {


    //
    protected SimpleNode[] ABC;
    protected final File sourceFile;
    protected final File destFile;
    //
    protected HuffmannBinaryInputStream his;
    protected HuffmannOutputStream hos;
    private GroupNode root;
    private ParseResult parseResult;



    /**
     * Creates HuffmannDecoder which decodes given file into given file;
     *
     * @param sourceFile source file
     * @param destFile   destination file
     */
    public HuffmannDecoder (File sourceFile, File destFile) {
        this.sourceFile = sourceFile;
        this.destFile = destFile;
    }



    /**
     * Creates HuffmannDecoder which decodes given file into given file + "x3m.huff" file
     *
     * @param sourceFile source file
     */
    public HuffmannDecoder (File sourceFile) {
        this (sourceFile, new File (sourceFile.getPath () + ".x3m.huff"));
    }



    /**
     * Method decodes file specified in constructor into file specified in constructor.
     *
     * @throws UnsupportedFileException when file is not supported
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void decode () throws FileNotFoundException, IOException, UnsupportedFileException {
        his = new HuffmannBinaryInputStream (sourceFile);
        if (!his.isSupported ())
            throw new UnsupportedFileException ();

        hos = new HuffmannOutputStream (destFile);


        ABC = his.readAlphabet ();
        root = Huffmann.createBinaryTree (ABC);

        parseResult = new ParseResult (0, 0);
        byte[] binary;
        int reminder;
        int validBits;
        byte[] result;


        while ((binary = his.readNextBinaryBuffer ()) != null) {

            //# getting valid bits count
            if (his.hasNext ()) validBits = binary.length;
            else validBits = binary.length - 8 - (8 - his.getValidBitsInLastByte ());

            //# creates needed data holders and searching for prefixes
            result = new byte[binary.length];
            parseBytes (binary, result, validBits);
            reminder = parseResult.reminderLength;
            his.setReminderLength (reminder);

            //# writing to out file
            hos.write (result, 0, parseResult.validBytes);
        }

        hos.close ();

    }

    //--------------------------------------
    //# Privates
    //--------------------------------------


    /**
     * Prefix search.
     *
     * @param binary    source array
     * @param result    final array
     * @param validBits number of valid bites
     */
    private void parseBytes (byte[] binary, byte[] result, int validBits) {
        int from = 0;
        int validBytes = 0;
        SimpleNode node;


        if (validBits != binary.length) {
            byte[] tmp = new byte[validBits];
            System.arraycopy (binary, 0, tmp, 0, validBits);
            binary = tmp;
        }

        while ((node = root.find (binary, from, 0)) != null) {
            from += node.codeLength;
            result[validBytes++] = (byte) node.character;
        }
        parseResult.reminderLength = (binary.length - from);
        parseResult.validBytes = validBytes;
    }


    class ParseResult {


        public int reminderLength, validBytes;



        public ParseResult (int reminderLength, int validBytes) {
            this.reminderLength = reminderLength;
            this.validBytes = validBytes;
        }
    }
}
