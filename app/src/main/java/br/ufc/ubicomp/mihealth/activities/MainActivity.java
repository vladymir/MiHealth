package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.events.GenericEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;
import br.ufc.ubicomp.mihealth.services.MiHeartMonitorService;
import br.ufc.ubicomp.mihealth.services.MiService;


public class MainActivity extends Activity {
    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    ImageButton main;

    // TODO remover essa dependencia
    private GoogleApiClient mClient = null;

    private static final int    REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("US", "OrientationChange.onStart");

        // Connect to the Fitness API
        Toast.makeText(this,"Connecting...",Toast.LENGTH_SHORT).show();
        mClient.connect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("US", "OrientationChange.onStop");
        if (mClient.isConnected()) {
            Toast.makeText(this,"Disconnecting...",Toast.LENGTH_SHORT).show();
            mClient.disconnect();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainEventBus.register(this);

        Intent myIntent = new Intent(this, MiService.class);
        this.startService(myIntent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        buildFitnessClient();

        Log.d("US", "OrientationChange.onCreate");
        int currentOrientation = getResources().getConfiguration().orientation;
        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);
        main = (ImageButton) findViewById(R.id.main);

        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListContactsActivity.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MasterFooter.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        Log.d("US", "MainActivity.onConfigurationChanged");


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            Log.i("US",
                    "MainActivity.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "MainActivity.onConfigurationChanged (ORIENTATION_LANDSCAPE)");
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("US", "OrientationChange.onResume");

    }
    /**
     * Método chamado pelo EventBus
     * @param event
     */
    public void onEvent(LocationEvent event) {
        Toast.makeText(this, "Lat: " + event.location.lat + " Lon: " + event.location.lon, Toast.LENGTH_SHORT).show();
    }

    /**
     * Responde a requisição do client de conexão ao sensores
     * @param event
     */
    public void onEvent(RequestSensorClientEvent event) {
        // TODO substituir por um wrapper
        Toast.makeText(this, "RequestSensorClient event", Toast.LENGTH_SHORT).show();
        MainEventBus.notify(new ResponseSensorClientEvent(mClient));
    }

    /**
     *
     * @param event
     */
    public void onEvent(HeartMonitorEvent event) {
        Toast.makeText(this, "Frenquencia Cardiaca: " + event.heartFrequencyStr, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("US", "OrientationChange.onPause");
    }


    @Override
    protected void onDestroy() {

        // Sinaliza os componentes que a aplicação se encerrou
        Log.i("US", "OrientationChange.onDestroy");
        MainEventBus.notify(new FinalizeEvent());

        super.onDestroy();
    }

    public void onClick(View view) {
        // TODO Código de teste apenas
        MainEventBus.notify(new GenericEvent());
    }

    /**
     *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
     *  (see documentation for details). Authentication will occasionally fail intentionally,
     *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     *  can address. Examples of this include the user never having signed in before, or having
     *  multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildFitnessClient() {
        // Create the Google API Client
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        builder.addApi(Fitness.SENSORS_API);
        builder.addApi(Fitness.BLE_API);

        builder.addScope(new Scope(Scopes.FITNESS_BODY_READ));

        builder.addConnectionCallbacks(
                new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Toast.makeText(MainActivity.this, "Conectado!", Toast.LENGTH_SHORT).show();

                        Intent heartMonitorService = new Intent(MainActivity.this, MiHeartMonitorService.class);
                        MainActivity.this.startService(heartMonitorService);
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        // If your connection to the sensor gets lost at some point,
                        // you'll be able to determine the reason and react to it here.
                        if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                            Toast.makeText(MainActivity.this, "Perda de conexao. Falha na rede.", Toast.LENGTH_SHORT).show();
                        } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                            Toast.makeText(MainActivity.this, "Perda de conexao. Servico desconectado.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        builder.addOnConnectionFailedListener(
                new GoogleApiClient.OnConnectionFailedListener() {
                    // Called whenever the API client fails to connect.
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Toast.makeText(MainActivity.this, "Erro de conexao." + result.toString(), Toast.LENGTH_SHORT).show();
                        if (!result.hasResolution()) {
                            // Show the localized error dialog
                            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
                            return;
                        }
                        if (!authInProgress) {
                            try {
                                Toast.makeText(MainActivity.this, "Nao foi possivel estabelecer uma conexao.", Toast.LENGTH_SHORT).show();
                                authInProgress = true;
                                result.startResolutionForResult(MainActivity.this, REQUEST_OAUTH);
                            } catch (IntentSender.SendIntentException e) {
                                Toast.makeText(MainActivity.this, "Excecao durante a tentativa de conexao.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        mClient = builder.build();
    }
}