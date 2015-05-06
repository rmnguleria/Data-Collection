package com.awesome.raman.pot_app5;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * roadEvent object . 
 */
public enum roadEvent {
    POTHOLE(1),BUMP(2),BRAKE(3),ROUGH_ROAD(4);

    private int value;

    private roadEvent(int value){
        this.value = value;
    }

    private static final Map<Integer,roadEvent> lookup
            = new HashMap<>();

    static {
        for(roadEvent w : EnumSet.allOf(roadEvent.class))
            lookup.put(w.getValue(), w);
    }

    public int getValue() { return value; }

    public static roadEvent get(int code) {
        return lookup.get(code);
    }
}
