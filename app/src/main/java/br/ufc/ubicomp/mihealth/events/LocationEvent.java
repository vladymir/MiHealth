package br.ufc.ubicomp.mihealth.events;

import br.ufc.ubicomp.mihealth.data.MiLocation;

public class LocationEvent extends MiEvent {

    public final MiLocation location;

    public LocationEvent(MiLocation location) {
        super("LastLocation");
        this.location = location;
    }
}
