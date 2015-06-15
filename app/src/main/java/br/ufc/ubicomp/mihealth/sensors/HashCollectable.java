package br.ufc.ubicomp.mihealth.sensors;

import java.util.HashMap;

import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public interface HashCollectable extends Collectable<Tuple<Sensor,Double>> {
    @Override
    public Tuple<Sensor, Double> collect();

    @Override
    public Tuple<Sensor, Double> call() throws Exception;
}
