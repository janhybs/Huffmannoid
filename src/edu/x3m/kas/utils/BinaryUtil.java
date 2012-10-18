package edu.x3m.kas.utils;


/**
 *
 * @author Hans
 */
public class BinaryUtil {


    /**
     * Method converts byte data to binary data
     *
     *
     * <pre>
     * [1,1,1,1,0,0,0,0, 0,0,0,0,1,1,1,1] to [240, 15]
     * </pre>
     * <p/>
     * @param buffer
     * @return array of bytes ([240, 15])
     */
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



    /**
     * Method converts array of bytes (e.g. [48, 49]) to array of bytes (e.g. [240, 15])
     * When writing
     *
     * @param src     from where
     * @param dest    to where
     * @param destPos from where position
     * @param length  how many
     */
    public static void convertIntTo10 (byte[] src, byte[] dest, int destPos, int length) {
        int value;
        byte[] tmp;
        for (int i = 0; i < length; i++) {
            value = ((int) src[i] + 256) % 256;
            tmp = toBinary (value).getBytes ();
            System.arraycopy (tmp, 0, dest, i * 8 + destPos, 8);
        }
    }



    /**
     * Method converts binary data to byte data
     * When reading
     *
     * <pre>
     * [240, 15] to [1,1,1,1,0,0,0,0, 0,0,0,0,1,1,1,1]
     * </pre>
     * <p/>
     * @param buffer
     * @return array of bytes ([48, 49, 48])
     */
    public static byte[] convertIntTo10 (byte[] buffer) {
        final int length = buffer.length * 8;
        final byte[] result = new byte[length];

        int value;
        byte[] tmp;
        for (int i = 0; i < buffer.length; i++) {
            value = ((int) buffer[i] + 256) % 256;
            tmp = toBinary (value).getBytes ();
            System.arraycopy (tmp, 0, result, i * 8, 8);
        }
        return result;
    }



    /**
     * Method formats given value to 8b length
     *
     * @param value
     * @return byte value after transformation
     */
    public static byte format (String value) {
        while (value.length () < 8) value += "0";
        return (byte) Integer.parseInt (value, 2);
    }



    /**
     * Method transform given value to 8b string
     *
     * @param value
     * @return 8b 
     */
    public static String toBinary (int value) {
        String result = Integer.toBinaryString (value);
        while (result.length () < 8) result = "0" + result;
        return result;
    }
}
