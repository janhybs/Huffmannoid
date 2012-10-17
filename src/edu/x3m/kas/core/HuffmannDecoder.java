package edu.x3m.kas.core;


import edu.x3m.kas.core.structures.SimpleNode;
import edu.x3m.kas.io.HuffmannInputStream;
import edu.x3m.kas.io.HuffmannOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Hans
 */
public class HuffmannDecoder {


    public static final String PREQUEL = "x3m-huff";
    protected SimpleNode[] ABC;
    protected final File sourceFile;
    protected final File destFile;
    //
    protected HuffmannInputStream his;
    protected HuffmannOutputStream hos;



    public HuffmannDecoder (File sourceFile, File destFile) {
        this.sourceFile = sourceFile;
        this.destFile = destFile;
    }



    public HuffmannDecoder (File sourceFile) {
        this (sourceFile, new File (sourceFile.getPath () + ".x3m.huff"));
    }



    public void decode () throws FileNotFoundException, IOException {
        his = new HuffmannInputStream (sourceFile);
        hos = new HuffmannOutputStream (destFile);
        ABC = his.getAlphabet ();

        byte[] buffer;
        int pos;
        int validUntil;
        SimpleNode node;
        String reminder = "";
        while ((buffer = his.getNextBinaryBuffer (reminder.length ())) != null) {

            if (!reminder.isEmpty ())
                System.arraycopy (reminder.getBytes (), 0, buffer, 0, reminder.length ());

            validUntil = buffer.length;

            if (!his.hasNext ()) {
                validUntil -= 8 + (8 - Byte.parseByte (new String (buffer, buffer.length - 8, 8), 2));

            }

            pos = 0;
            while ((node = getNextCode (buffer, pos, validUntil)) != null) {
                pos += node.codeLength;
                hos.write (node.character);
            }
            
            reminder = new String (buffer, pos, buffer.length - pos);
        }
        
        hos.close ();
    }



    private SimpleNode getNextCode (byte[] buffer, int pos, int validUntil) {
        boolean[] mask = new boolean[ABC.length];
        
        //System.out.println (new String (buffer, pos, validUntil-pos));

        SimpleNode node, correct = null;
        int i, j, c;
        
        for (i = 0; i < buffer.length - pos; i++) {
            if ((i+pos) >= validUntil)
                return null;
            
            byte b = buffer[i + pos];

            c = 0;
            for (j = 0; j < ABC.length; j++) {
                //# already excluded
                if (mask[j]) continue;

                //# init
                node = ABC[j];

                //# too  long
                if (i >= node.codeLength) {
                    mask[j] = true;
                    continue;
                }


                if (node.finalCode.charAt (i) == b) {
                    correct = node;
                    c++;
                } else {
                    mask[j] = true;
                }
            }

            if (c == 1) {
                if (correct.character == 99);
                return correct;
            }
        }
        return null;
    }
}
