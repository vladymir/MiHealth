package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import br.ufc.ubicomp.mihealth.R;


public class ListContactsActivity extends Activity {

    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    ImageButton main;
    ImageButton addCont;
    Button _ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contato);

        Log.d("US", "OrientationChange.onCreate");
        // detect the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;


        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);
        main = (ImageButton) findViewById(R.id.main);
        _ok = (Button) findViewById(R.id.okCont);
        addCont = (ImageButton) findViewById(R.id.addCont);

        _ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // colocar código de persistencia
                Intent intent = new Intent(ListContactsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        addCont.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, ContactsReg.class);
                startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, ListContactsActivity.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ListContactsActivity.this, MasterFooter.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("US", "OrientationChange.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("US", "OrientationChange.onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("US", "OrientationChange.onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("US", "OrientationChange.onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("US", "OrientationChange.onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("US", "ListContactsActivity.onConfigurationChanged");
        setContentView(R.layout.activity_lista_contato);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("US",
                    "ListContactsActivity.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait
            Intent intent = new Intent(ListContactsActivity.this, ListContactsActivity.class);
            startActivity(intent);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "ListContactsActivity.onConfigurationChanged (ORIENTATION_LANDSCAPE)");
            Intent intent = new Intent(ListContactsActivity.this, ListContactsActivity.class);
            startActivity(intent);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_contato, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
