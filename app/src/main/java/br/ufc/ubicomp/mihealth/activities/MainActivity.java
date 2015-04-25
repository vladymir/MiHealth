package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.GenericEvent;
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.services.MiService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = new Intent(this, MiService.class);
        this.startService(myIntent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainEventBus.register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onEvent(LocationEvent event) {
        Toast.makeText(this, "Lat: " + event.location.lat + " Lon: " + event.location.lon
                , Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        // TODO Código de teste apenas
        MainEventBus.notify(new GenericEvent());
    }
}

