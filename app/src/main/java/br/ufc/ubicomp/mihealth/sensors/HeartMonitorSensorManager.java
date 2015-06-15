package br.ufc.ubicomp.mihealth.sensors;


import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Random;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class HeartMonitorSensorManager extends MiSensorManager implements HashCollectable {


    public HeartMonitorSensorManager() {
    }

    public void dispatchEvent(double newHeartFrequency) {
        MainEventBus.notifySticky(new HeartMonitorEvent(newHeartFrequency));
    }

    /**
     * Faça a leitura do sensor aqui!
     * @return Dado obtido do sensor
     */
    @Override
    public Tuple<Sensor, Double> collect() {
        Tuple<Sensor,Double> result = new Tuple<>(Sensor.HEARTBEAT,
                new Double(new Random().nextInt((150 - 60) + 1) + 60 ));
        return result;
    }

    @Override
    public Tuple<Sensor, Double> call() throws Exception {
        Tuple<Sensor,Double> result = this.collect();
        MainEventBus.notify(new HeartMonitorEvent(result.second));
        return result;
    }
}
