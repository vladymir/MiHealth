package br.ufc.ubicomp.mihealth.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.context.CollectContextData;
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.sensors.BodyTemperatureSensorManager;
import br.ufc.ubicomp.mihealth.sensors.HeartMonitorSensorManager;

public class MiService extends Service {

    private CollectContextData contextReader;

    public MiService() {
        contextReader = new CollectContextData(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainEventBus.register(this);
        new Thread(contextReader).start();
    }

    @SuppressWarnings("Method called by EventBus objects")
    public void onEvent(LocationEvent event) {
        Log.d("LOCATION_EVENT","Location: "+ event.location.lat);
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
