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
import android.widget.ListView;

import java.util.List;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.adapter.MedicinesAdapter;
import br.ufc.ubicomp.mihealth.dao.MedicinesDAO;
import br.ufc.ubicomp.mihealth.model.Medicines;

public class MedicinesActivity extends Activity {
    private ListView lista;
    private List<Medicines> medicinesList;
    private MedicinesAdapter medicinesAdapter;
    private MedicinesDAO medicinesDAO;


    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    ImageButton main;
    Button _ok;
    ImageButton addMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medic);

        medicinesDAO = new MedicinesDAO(this);
        medicinesList = medicinesDAO.listarMedicines();
        medicinesAdapter = new MedicinesAdapter(this, medicinesList);


        lista = (ListView) findViewById(R.id.lvMedicines);
        lista.setAdapter(medicinesAdapter);

        Log.d("US", "OrientationChange.onCreate");
        // detect the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;




        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);
        main = (ImageButton) findViewById(R.id.main);
        _ok = (Button) findViewById(R.id.okMed);
        addMed = (ImageButton) findViewById(R.id.addMed);

        _ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // colocar código de persistencia
                Intent intent = new Intent(MedicinesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // colocar código de persistencia
                Intent intent = new Intent(MedicinesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addMed.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MedicinesActivity.this, MedicinesReg.class);
                startActivity(intent);
            }
        });


        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MedicinesActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MedicinesActivity.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MedicinesActivity.this, ListContactsActivity.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MedicinesActivity.this, MasterFooter.class);
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
        Log.d("US", "MedicinesActivity.onConfigurationChanged");


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("US",
                    "MedicinesActivity.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait

            Intent intent = new Intent(MedicinesActivity.this, MedicinesActivity.class);
            startActivity(intent);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "MedicinesActivity.onConfigurationChanged (ORIENTATION_LANDSCAPE)");

            Intent intent = new Intent(MedicinesActivity.this, MedicinesActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_medic, menu);

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
