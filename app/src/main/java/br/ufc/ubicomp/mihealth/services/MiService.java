package br.ufc.ubicomp.mihealth.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import br.ufc.ubicomp.mihealth.context.ContextManager;

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
