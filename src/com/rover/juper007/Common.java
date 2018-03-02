package com.rover.juper007;

import java.util.HashSet;

public class Common {
    public static int getSitterScore(String sitterName){
        sitterName.toLowerCase();
        HashSet<Character> hs = new HashSet<>();

        int size = sitterName.length();
        for (int i=0; i < size; i++) {
            if (Character.isLetter(sitterName.charAt(i)) && !hs.contains(sitterName.charAt(i))) {
                hs.add(sitterName.charAt(i));
            }
        }
        return hs.size();
    }
}
