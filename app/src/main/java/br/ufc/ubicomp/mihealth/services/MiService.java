package br.ufc.ubicomp.mihealth.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.events.GetLocationEvent;
import br.ufc.ubicomp.mihealth.utils.LocationUtil;
import de.greenrobot.event.EventBus;

import static android.location.LocationManager.*;

public class MiService extends Service {

    private final EventBus eventBus = EventBus.getDefault();
    private LocationUtil locationUtil;
    public MiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationUtil = new LocationUtil(this);
        eventBus.register(this);
    }

    public void onEvent(GetLocationEvent event) {
        Log.d("EVENTBUS","Location: "+locationUtil.getLastKnownLocation().getLatitude());
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
