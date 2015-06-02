package br.ufc.ubicomp.mihealth.events;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Nelson on 02/06/2015.
 */
public class ResponseSensorClientEvent extends MiEvent {

    public final GoogleApiClient client;

    public ResponseSensorClientEvent(GoogleApiClient client) {
        super("ResponseSensorClientEvent");
        this.client = client;
    }

}
