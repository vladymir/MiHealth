package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.events.GetLocationEvent;
import br.ufc.ubicomp.mihealth.events.LastLocationEvent;
import br.ufc.ubicomp.mihealth.events.MiEvent;
import br.ufc.ubicomp.mihealth.services.MiService;
import de.greenrobot.event.EventBus;


public class MainActivity extends Activity {

    private final EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent myIntent = new Intent(this, MiService.class);
        this.startService(myIntent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventBus.register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onEvent(LastLocationEvent event) {
        Toast.makeText(this, "Lat: " + event.lat + " Lon: " + event.lon
                , Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        //eventBus.post(new MiEvent("event "));
        eventBus.post(new GetLocationEvent("get location"));
    }
}

