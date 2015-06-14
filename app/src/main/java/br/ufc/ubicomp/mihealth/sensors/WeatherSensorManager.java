package br.ufc.ubicomp.mihealth.sensors;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.WeatherEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class WeatherSensorManager extends MiSensorManager implements HashCollectable {

    @Override
    public Tuple<Sensor, Double> collect() {
        Tuple<Sensor, Double> result = new Tuple<>(Sensor.WEATHERTEMPERATURE, new Double( Math.random() * 40 ));
        return result;
    }

    @Override
    public Tuple<Sensor, Double> call() throws Exception {
        Tuple<Sensor, Double> result = this.collect();
        MainEventBus.notify(new WeatherEvent(result.second));
        return result;
    }
}
