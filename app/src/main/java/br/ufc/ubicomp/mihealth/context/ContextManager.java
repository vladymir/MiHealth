package br.ufc.ubicomp.mihealth.context;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.WeatherEvent;

public class ContextManager {

    private static ContextManager instance;

    private ContextManager() {
        MainEventBus.register(this);
    }

    public static ContextManager getInstance() {
        if(instance == null) {
            instance = new ContextManager();
        }
        return instance;
    }

    public void onEvent(HeartMonitorEvent heartMonitorEvent) {
        // TODO persist data
        // TODO notify GUI
    }

    public void onEvent(WeatherEvent weatherEvent) {
        //TODO persist?
        // TODO notify GUI
    }
}
