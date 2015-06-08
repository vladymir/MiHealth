package br.ufc.ubicomp.mihealth.events;

import br.ufc.ubicomp.mihealth.sensors.BodyTemperatureSensorManager;

/**
 * TODO: Add a class header comment!
 */
public class BodyTemperatureEvent extends MiEvent {
    public final Double bodyTemperature;

    public BodyTemperatureEvent(Double bodyTemperature) {
        super("Body Temp");
        this.bodyTemperature = bodyTemperature;
    }
}
