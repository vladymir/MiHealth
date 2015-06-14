package br.ufc.ubicomp.mihealth.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.context.CollectContextData;
import br.ufc.ubicomp.mihealth.context.ContextManager;
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.sensors.BodyTemperatureSensorManager;
import br.ufc.ubicomp.mihealth.sensors.HeartMonitorSensorManager;
import br.ufc.ubicomp.mihealth.sensors.WeatherSensorManager;

public class MiService extends Service {

    private ContextManager contextManager;

    public MiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        contextManager = ContextManager.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("VLAD", "MY SERVICE STARTED");
        return super.onStartCommand(intent, flags, startId);
    }
}
