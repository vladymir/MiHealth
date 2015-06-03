package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
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
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;
import br.ufc.ubicomp.mihealth.services.MiHeartMonitorService;
import br.ufc.ubicomp.mihealth.services.MiService;


public class MainActivity extends Activity {
    public ImageButton dados_us;
    public ImageButton cad_med;
    public ImageButton cad_us;
    public ImageButton ajust;

    // TODO remover essa dependencia
    private GoogleApiClient mClient = null;

    private static final int    REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;

    @Override
    protected void onStart() {
        super.onStart();

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

        Intent heartMonitorService = new Intent(this, MiHeartMonitorService.class);
        this.startService(heartMonitorService);


            dados_us = (ImageButton) findViewById(R.id.dados_usuario);
            cad_med = (ImageButton) findViewById(R.id.cad_med);
            cad_us = (ImageButton) findViewById(R.id.cad_cont);
            ajust = (ImageButton) findViewById(R.id.ajustes);

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
                Intent intent = new Intent(MainActivity.this, lista_contato.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, master_footer.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    @Override
    protected void onDestroy() {
        // Sinaliza os componentes que a aplicação se encerrou
        MainEventBus.notify(new FinalizeEvent());
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
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Toast.makeText(MainActivity.this, "Connected!!!", Toast.LENGTH_SHORT).show();
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                            }
                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Toast.makeText(MainActivity.this, "Connection lost.  Cause: Network Lost.", Toast.LENGTH_SHORT).show();
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Toast.makeText(MainActivity.this, "Connection lost.  Reason: Service Disconnected", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Toast.makeText(MainActivity.this, "Connection failed. Cause: " + result.toString(), Toast.LENGTH_SHORT).show();
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Toast.makeText(MainActivity.this, "Attempting to resolve failed connection", Toast.LENGTH_SHORT).show();
                                        authInProgress = true;
                                        result.startResolutionForResult(MainActivity.this, REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Toast.makeText(MainActivity.this, "Exception while starting resolution activity", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                )
                .build();
    }
}