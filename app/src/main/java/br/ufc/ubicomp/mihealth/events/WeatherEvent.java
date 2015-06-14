package br.ufc.ubicomp.mihealth.events;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.sensors.Collectable;

public class WeatherEvent extends MiEvent {
    public final Double temperature;

    public WeatherEvent(Double temp) {
        super("Weather event");
        this.temperature = temp;
    }

}
