package edu.x3m.kas.utils;


/**
 *
 * @author Hans
 */
public class BinaryUtil {


    public static int[] convert10ToInt (byte[] buffer, boolean requireLastByte) {
        final boolean isOctalDivider = buffer.length % 8 == 0;
        final int length = isOctalDivider ? buffer.length / 8 : (requireLastByte ? 1 : 0) + buffer.length / 8;
        final int[] result = new int[length];

        boolean lastByte;
        String seq;
        for (int i = 0; i < length; i++) {
            lastByte = i == length - 1;

            if (lastByte && requireLastByte) {
                seq = new String (buffer, i * 8, buffer.length - i * 8);
                result[i] = Integer.parseInt (seq, 2);
            } else {
                seq = new String (buffer, i * 8, 8);
                result[i] = Integer.parseInt (seq, 2);
            }
        }

        return result;
    }



    public static byte[] convertIntTo10 (int[] buffer) {
        final int length = buffer.length * 8;
        final byte[] result = new byte[length];
        
        int value;
        byte[] tmp;
        for (int i = 0; i < buffer.length; i++) {
            value = buffer[i];
            tmp = toBinary8 (value).getBytes ();
            System.arraycopy (tmp, 0, result, i*8, 8);
        }
        return result;
    }
    
    private static String toBinary8 (int value) {
        String result = Integer.toBinaryString (value);
        while (result.length () < 8) result += "0";
        return result;
    }
}
