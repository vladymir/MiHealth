package br.ufc.ubicomp.mihealth.data;

import android.location.Address;
import android.location.Location;

/**
 * Created by nelson on 25/04/15.
 */
public class MiLocation {

    /**
     * TODO remover a dependencia direta do componente nativo do Android e trafegar apenas os
     * dados necessários conforme o contrato dos métodos publicos. Por enquanto, deixaremos
     * apenas para mapear todos os dados que precisamos.
     */
    private Location location;
    private Address address;

    public final double lat;
    public final double lon;

    public MiLocation(Location location, Address address) {
        this.location = location;
        this.address = address;

        this.lat = this.location.getLatitude();
        this.lon = this.location.getLongitude();
    }

}
