package br.ufc.ubicomp.mihealth.sensors;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.data.MiLocation;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.LocationEvent;

/**
 * Created by vladymirbezerra on 24/04/15.
 * Modified by nelson on 25/04/15.
 */
public class LocationSensorManager extends MiSensorManager {

    private LocationAgent locationAgent;

    public LocationSensorManager(Context context) {
        this.locationAgent = new LocationAgent(context);
    }

    public MiLocation getLocation() {
        return new MiLocation( this.locationAgent.getLastKnownLocation() ,  this.locationAgent.getLastAddress() );
    }

    /**
     * É notificado pelo agente para disparar o evento de mudança de localização
     * @param location Nova localização
     */
    private void dispathEvent(Location location) {

        if (location == null) {
            ErrorHandlerEventBus.signal(new ErrorEvent(new RuntimeException("Variáveis não podem ser nulas...")));
            // TODO O mecanismo de tratamento de erro precisa para a execução neste ponto
            return;
        }

        MiLocation miLocation = new MiLocation(location, this.locationAgent.getLastAddress());
        LocationEvent lastLocation = new LocationEvent(miLocation);
        MainEventBus.notify(lastLocation);

        Toast.makeText(this.locationAgent.context, "Lat " + miLocation.lat + "Lon " + miLocation.lon, Toast.LENGTH_SHORT).show();
    }

    private class LocationAgent implements LocationListener {

        Context context;
        LocationManager locationManager;

        LocationAgent(Context context) {
            this.context = context;
            this.locationManager = configureLocationManager();
        }

        public void onLocationChanged(Location location) {
            dispathEvent(location);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO implementar
        }

        public void onProviderEnabled(String provider) {
            // TODO implementar
        }

        public void onProviderDisabled(String provider) {
            // TODO implementar
        }

        LocationManager configureLocationManager() {
            return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        /**
         *  void requestSinglePosition() {
         *      locationManager.requestLocationUpdates(NETWORK_PROVIDER, 400, 1, this);
         *      locationManager.removeUpdates(this);
         *  }
         */

        Location getLastKnownLocation() {
            return this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        Address getLastAddress() {
            Location loc = this.getLastKnownLocation();
            Geocoder gcd = new Geocoder(this.context, Locale.getDefault());
            List<Address> addressList = new ArrayList<Address>();

            try {
                addressList = gcd.getFromLocation(loc.getLatitude(),loc.getLongitude(), 1);
                if(addressList.size() > 0)
                    return addressList.get(0);

            } catch (IOException e) {
                e.printStackTrace();
                ErrorHandlerEventBus.signal(new ErrorEvent(e));
            }

            return new Address(Locale.getDefault());
        }
    }
}