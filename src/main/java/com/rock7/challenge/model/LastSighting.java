package com.rock7.challenge.model;

import java.time.Instant;

public class LastSighting {

    private Instant lastSeen;
    private int timesSeen;

    public LastSighting(Instant lastSeen, int timesSeen) {
        this.lastSeen = lastSeen ;
        this.timesSeen = timesSeen;
    }

    public void updateLastSeen(Instant lastSeen, boolean incrementCount) {
        this.lastSeen = lastSeen;
        if (incrementCount) {
            this.timesSeen++;
        }
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public int getTimesSeen() {
        return timesSeen;
    }
}
