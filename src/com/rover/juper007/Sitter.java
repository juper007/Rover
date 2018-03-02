package com.rover.juper007;

public class Sitter extends User {
    public int sitterScore;

    public Sitter(int sitterId, String sitterName, String sitterImage, String sitterPhone, String sitterEmail, int sitterScore) {
        super(sitterId, sitterName, sitterImage, sitterPhone, sitterEmail);
        this.sitterScore = sitterScore;
    }
}
