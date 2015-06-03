package br.ufc.ubicomp.mihealth.events;

public class HeartMonitorEvent extends MiEvent {

    public final Double heartFrequency;

    public HeartMonitorEvent(Double heartFrequency) {
        super("Heart monitor event");
        this.heartFrequency = heartFrequency;
    }

}
