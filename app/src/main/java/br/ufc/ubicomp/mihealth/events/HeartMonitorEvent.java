package br.ufc.ubicomp.mihealth.events;

/**
 * Created by nelson on 25/04/15.
 */
public class HeartMonitorEvent extends MiEvent {

    public final double heartFrequency;

    public HeartMonitorEvent(Double heartFrequency) {
        super("Heart monitor event");
        this.heartFrequency = heartFrequency;
    }

}
