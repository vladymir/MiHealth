package br.ufc.ubicomp.mihealth.sensors;


import android.os.AsyncTask;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;

public class HeartMonitorSensorManager extends MiSensorManager implements Runnable, Collectable<Double> {


    public HeartMonitorSensorManager() {
        // Assina no EventBus para receber a notificação de finalização de execução
        MainEventBus.register(this);
    }

    @Override
    public void run() {
        MainEventBus.notify(new HeartMonitorEvent(collect()));
    }

    public void dispatchEvent(double newHeartFrequency) {
        MainEventBus.notifySticky(new HeartMonitorEvent(newHeartFrequency));
    }

    /**
     * Faça a leitura do sensor aqui!
     * @return Dado obtido do sensor
     */
    @Override
    public Double collect() {
        return new Double(Math.random() * 80);
    }
}
