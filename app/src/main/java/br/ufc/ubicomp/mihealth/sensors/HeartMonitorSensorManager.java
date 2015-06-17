package br.ufc.ubicomp.mihealth.sensors;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class HeartMonitorSensorManager extends MiSensorManager implements HashCollectable {

    private Double bhf = -1d;

    public HeartMonitorSensorManager() {
        // TODO
    }

    public void dispatchEvent(double newHeartFrequency) {
        MainEventBus.notifySticky(new HeartMonitorEvent(newHeartFrequency));
    }

    public void onEvent(HeartMonitorEvent event) {
        this.bhf = event.heartFrequency;
    }

    /**
     * Faça a leitura do sensor aqui!
     * @return Dado obtido do sensor
     */
    @Override
    public Tuple<Sensor, Double> collect() {
        //Tuple<Sensor,Double> result = new Tuple<>(Sensor.HEARTBEAT,
        //        new Double(new Random().nextGaussian() + 75 ));
        Tuple<Sensor,Double> result = new Tuple<>(Sensor.HEARTBEAT, this.bhf);
        return result;
    }

    @Override
    public Tuple<Sensor, Double> call() throws Exception {
        Tuple<Sensor,Double> result = this.collect();
        MainEventBus.notify(new HeartMonitorEvent(result.second));
        return result;
    }
}
