package br.ufc.ubicomp.mihealth.events;

/**
 * Created by nelson on 25/04/15.
 */
public class HeartMonitorEvent extends MiEvent {

    public Double heartFrequency;
    // TODO Remove
    @Deprecated
    public String heartFrequencyStr;

    public HeartMonitorEvent(Double heartFrequency) {
        super("Heart monitor event");
        this.heartFrequency = heartFrequency;
    }

    public HeartMonitorEvent(String heartFrequency) {
        super("Heart monitor event");
        this.heartFrequencyStr = heartFrequency;
    }

}
