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
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.R;


public class UserRegistrationActivity extends Activity {
    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    Button ok;
    ImageButton main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        Log.d("US", "OrientationChange.onCreate");
        // detect the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;

        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);
        ok = (Button) findViewById(R.id.butOk);
        main = (ImageButton) findViewById(R.id.main);

        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ok.setOnClickListener(new View.OnClickListener()  {

            public void onClick(View v) {

                Toast.makeText(UserRegistrationActivity.this, "Dados Cadastrados Com Sucesso" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserRegistrationActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, ListContactsActivity.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(UserRegistrationActivity.this, MasterFooter.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("US", "UserRegistrationActivity.onConfigurationChanged");


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("US",
                    "UserRegistrationActivity.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait

            Intent intent = new Intent(UserRegistrationActivity.this, UserRegistrationActivity.class);
            startActivity(intent);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "UserRegistrationActivity.onConfigurationChanged (ORIENTATION_LANDSCAPE)");

            Intent intent = new Intent(UserRegistrationActivity.this, UserRegistrationActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
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
