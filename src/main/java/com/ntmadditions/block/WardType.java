package com.ntmadditions.block;

/**
 * The two charm behaviours a ward can project.
 */
public enum WardType {

    /** Stops meteor strikes entirely, like NTM's Meteor Charm. */
    METEOR("message.ntmadditions.meteor_ward.radius"),

    /** Diverts meteor strikes and prevents their block damage, like the Charm of Protection. */
    PROTECTION("message.ntmadditions.protection_ward.radius");

    private final String radiusMessageKey;

    WardType(String radiusMessageKey) {
        this.radiusMessageKey = radiusMessageKey;
    }

    public String radiusMessageKey() {
        return radiusMessageKey;
    }
}
