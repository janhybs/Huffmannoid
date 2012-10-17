package edu.x3m.kas.core;


import edu.x3m.kas.utils.BinaryUtil;



/**
 *
 * @author Hans
 */
public class Main {


    public static void main (String[] args) {
        String s = "1111000000001111101";
        
        BinaryUtil.convert10ToInt (s.getBytes (), false);
        
        BinaryUtil.convertIntTo10 (new int[]{240,15,5});
    }
}
