package br.ufc.ubicomp.mihealth.events;

import com.google.android.gms.fitness.data.DataSource;

/**
 * Created by Nelson on 03/06/2015.
 */
public class RegisterDataListenerEvent extends MiEvent {

    public final DataSource dataSource;

    public RegisterDataListenerEvent(DataSource dataSource) {
        super("RegisterDataListenerEvent");

        this.dataSource = dataSource;
    }

}
