package br.ufc.ubicomp.mihealth.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufc.ubicomp.mihealth.events.LastLocationEvent;
import br.ufc.ubicomp.mihealth.services.MiService;
import de.greenrobot.event.EventBus;

import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by vladymirbezerra on 24/04/15.
 */
public class LocationUtil implements LocationListener {
    private Context context;
    private LocationManager locationManager;

    public LocationUtil(Context context) {
        this.context = context;
        this.locationManager = configureLocationManager();
    }

    private LocationManager configureLocationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void onLocationChanged(Location location) {
        LastLocationEvent lastLocation = new LastLocationEvent(location.getLatitude(),location.getLongitude());
        EventBus.getDefault().postSticky(lastLocation);
        Toast.makeText(this.context, "Lat " + lastLocation.lat +
                "Lon " + lastLocation.lon, Toast.LENGTH_SHORT).show();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {}
    public void onProviderEnabled(String provider) {}
    public void onProviderDisabled(String provider) {}

    public void requestSinglePosition() {
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 400, 1, this);
        locationManager.removeUpdates(this);
    }

    public Location getLastKnownLocation() {
        return this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public Address getLastAddress() {
        Location loc = this.getLastKnownLocation();
        Geocoder gcd = new Geocoder(this.context, Locale.getDefault());
        List<Address> addressList = new ArrayList<Address>();
        try {
            addressList = gcd.getFromLocation(loc.getLatitude(),loc.getLongitude(), 1);
            if(addressList.size() > 0)
                return addressList.get(0);

        } catch (IOException e) {
            e.printStackTrace();
            //post an error event;
        }

        return new Address(Locale.getDefault());
    }
}
