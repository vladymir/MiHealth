package br.ufc.ubicomp.mihealth.sensors;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.BodyTemperatureEvent;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;

/**
 * TODO: Add a class header comment!
 */
public class BodyTemperatureSensorManager extends MiSensorManager implements Runnable,Collectable<Double> {

    public BodyTemperatureSensorManager() {
        MainEventBus.register(this);
    }

    public void run() {
        MainEventBus.notify(new BodyTemperatureEvent(collect()));
    }

    /**
     * Leia o sensor aqui!
     * @return Dado do sensor obtido.
     */
    @Override
    public Double collect() {
        return new Double( Math.random() * 40 );
    }
}
