package br.ufc.ubicomp.mihealth.events;

/**
 * Created by vladymirbezerra on 15/04/15.
 */
public class LastLocationEvent extends MiEvent {
    public final double lat;
    public final double lon;

    public LastLocationEvent(double lat, double lon) {
        super("LastLocation");
        this.lat = lat;
        this.lon = lon;
    }
}
