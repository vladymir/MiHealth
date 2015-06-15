package br.ufc.ubicomp.mihealth.context;

import java.util.List;

import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.MiEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

/**
 * TODO: Add a class header comment!
 */
public class UpdateUIEvent extends MiEvent {
    public final List<Tuple<Sensor,Double>> contextData;

    public UpdateUIEvent(List<Tuple<Sensor,Double>> contextData) {
        super("UpdateUIEvent");
        this.contextData = contextData;
    }
}
