package br.ufc.ubicomp.mihealth.context;

import java.util.List;
import java.util.concurrent.Future;

import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.MiEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

/**
 * TODO: Add a class header comment!
 */
public class AggregateContext extends MiEvent {

    public final List<Future<Tuple<Sensor,Double>>> futures;

    public AggregateContext(List<Future<Tuple<Sensor,Double>>> futures) {
        super("AggregateContext");
        this.futures = futures;
    }
}
