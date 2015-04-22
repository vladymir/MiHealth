package br.ufc.ubicomp.mihealth.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.events.GetLocationEvent;
import br.ufc.ubicomp.mihealth.events.LastLocationEvent;
import br.ufc.ubicomp.mihealth.events.MiEvent;
import de.greenrobot.event.EventBus;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MiService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

    private final EventBus eventBus = EventBus.getDefault();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    public MiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        eventBus.register(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void onEvent(GetLocationEvent event) {
        Log.d("EVENTBUS", "GET LOCATION EVENT CAPTURED!");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (null != mLastLocation) {
            Log.d("EVENTBUS", "SENDING LOCATION");
            eventBus.post(new LastLocationEvent("ok", mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
        Toast.makeText(this, "EVENT " + event.message + " CAPTURED!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
