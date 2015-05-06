package com.awesome.raman.pot_app5;

/**
 * Stores events defined in MainActivity
 * Created by raman on 19/3/15.
 */
public class EventData {
    private long timeStamp;

    private int event;

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getEvent() {
        return event;
    }

    public EventData(long timeStamp,int event){
        this.timeStamp = timeStamp;
        this.event = event;
    }

    @Override
    public String toString(){
        return getTimeStamp() + "," + getEvent();
    }
}
