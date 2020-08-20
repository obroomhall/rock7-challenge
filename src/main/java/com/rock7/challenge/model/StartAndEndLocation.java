package com.rock7.challenge.model;

public class StartAndEndLocation {

    private final Location start;
    private final Location end;

    public StartAndEndLocation(Location start, Location end) {
        this.start = start;
        this.end = end;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }
}
