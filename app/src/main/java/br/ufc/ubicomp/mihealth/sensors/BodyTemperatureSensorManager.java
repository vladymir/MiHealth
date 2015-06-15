package br.ufc.ubicomp.mihealth.sensors;

import java.util.HashMap;
import java.util.Random;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.BodyTemperatureEvent;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

/**
 * TODO: Add a class header comment!
 */
public class BodyTemperatureSensorManager extends MiSensorManager implements HashCollectable {

    public BodyTemperatureSensorManager() {
    }

    /**
     * Leia o sensor aqui!
     * @return Dado do sensor obtido.
     */
    @Override
    public Tuple<Sensor, Double> collect() {
        Tuple<Sensor,Double> result = new Tuple<>(Sensor.BODYTEMPERATURE,
                new Double( new Random().nextInt((45 - 30) + 1) + 30 ));
        return result;
    }

    @Override
    public Tuple<Sensor, Double> call() throws Exception {
        Tuple<Sensor,Double> result = this.collect();
        MainEventBus.notify(new BodyTemperatureEvent(result.second));
        return result;
    }
}
