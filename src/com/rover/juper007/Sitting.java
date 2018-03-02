package com.rover.juper007;

import java.util.Date;

public class Sitting {
    public int sittingId;
    public int userId;
    public int sitterId;
    public Date sittingStartDate;
    public Date sittingEndDate;
    public String sittingText;
    public int sittingRate;

    public Sitting(int sittingId, int userId, int sitterId, Date sittingStartDate, Date sittingEndDate, String sittingText, int sittingRate) {
        this.sittingId = sittingId;
        this.userId = userId;
        this.sitterId = sitterId;
        this.sittingStartDate = sittingStartDate;
        this.sittingEndDate = sittingEndDate;
        this.sittingText = sittingText;
        this.sittingRate = sittingRate;
    }
}
