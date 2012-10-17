package edu.x3m.kas.utils;


/**
 *
 * @author Hans
 */
public class BinaryUtil {


    public static byte[] convert10ToInt (byte[] buffer) {
        final boolean isOctalDivider = buffer.length % 8 == 0;
        final int length = buffer.length / 8;
        final byte[] result = new byte[length];

        boolean lastByte;
        String seq;
        for (int i = 0; i < length; i++) {
            lastByte = i == length - 1;

            seq = new String (buffer, i * 8, 8);
            result[i] = (byte) Integer.parseInt (seq, 2);
        }

        return result;
    }



    public static byte[] convertIntTo10 (byte[] buffer) {
        final int length = buffer.length * 8;
        final byte[] result = new byte[length];

        int value;
        byte[] tmp;
        for (int i = 0; i < buffer.length; i++) {
            value = ((int) buffer[i] + 256) % 256;
            tmp = toBinary8 (value).getBytes ();
            System.arraycopy (tmp, 0, result, i * 8, 8);
        }
        return result;
    }



    public static byte format (String value) {
        while (value.length () < 8) value += "0";
        return (byte) Integer.parseInt (value, 2);
    }



    public static String toBinary (int value) {
        String result = Integer.toBinaryString (value);
        while (result.length () < 8) result = "0" + result;
        return result;
    }



    private static String toBinary8 (int value) {
        String result = Integer.toBinaryString (value);
        while (result.length () < 8) result += "0";
        return result;
    }
}
