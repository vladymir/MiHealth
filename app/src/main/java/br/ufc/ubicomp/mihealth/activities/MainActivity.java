package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.renderscript.Element;
import android.util.Log;
import android.view.Menu;
import android.view.View;
<<<<<<< HEAD
import android.widget.ImageButton;
=======
import android.widget.TextView;
>>>>>>> origin/master
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.concurrent.TimeUnit;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.events.GenericEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.LocationEvent;
import br.ufc.ubicomp.mihealth.events.RegisterDataListenerEvent;
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
    private OnDataPointListener mListener;

    private static final int    REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;

    public MainActivity() {
        MainEventBus.register(this);
    }

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
        Intent myIntent = new Intent(this, MiService.class);
        this.startService(myIntent);

        Intent heartMonitorService = new Intent(this, MiHeartMonitorService.class);
        this.startService(heartMonitorService);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        buildFitnessClient();
<<<<<<< HEAD

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

=======
>>>>>>> origin/master
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onEvent(HeartMonitorEvent event) {
        TextView heartFreq = (TextView) findViewById(R.id.heartFreq);
        heartFreq.setText(event.heartFrequencyStr);
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
        MainEventBus.notifyStick(new ResponseSensorClientEvent(mClient));
    }

    public void onEvent(RegisterDataListenerEvent event) {
        registerFitnessDataListener(event.dataSource, DataType.TYPE_HEART_RATE_BPM);
    }

    @Override
    protected void onDestroy() {
        // Sinaliza os componentes que a aplicação se encerrou
        MainEventBus.notifyStick(new FinalizeEvent());
    }

    public void onClick(View view) {
        // TODO Código de teste apenas
        MainEventBus.notifyStick(new GenericEvent());
    }

    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_BODY_READ))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnected(Bundle bundle) {
                                Toast.makeText(MainActivity.this, "Connected!!!", Toast.LENGTH_SHORT).show();

                                findFitnessDataSources();
                            }
                            @Override
                            public void onConnectionSuspended(int i) {
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
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
                                    return;
                                }
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

    private void findFitnessDataSources() {
        // [START find_data_sources]
        Fitness.SensorsApi.findDataSources(mClient, new DataSourcesRequest.Builder()
                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Toast.makeText(MainActivity.this,"Result: " + dataSourcesResult.getStatus().toString(), Toast.LENGTH_SHORT).show();

                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                            Toast.makeText(MainActivity.this,"Data source found: " + dataSource.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,"Data Source type: " + dataSource.getDataType().getName(), Toast.LENGTH_SHORT).show();
                            if (dataSource.getDataType().equals(DataType.TYPE_HEART_RATE_BPM) && mListener == null) {
                                Toast.makeText(MainActivity.this,"Data source for TYPE_HEART_RATE_BPM found!  Registering.", Toast.LENGTH_SHORT).show();
                                registerFitnessDataListener(dataSource, DataType.TYPE_HEART_RATE_BPM);
                            }
                        }
                    }
                });
    }

    /**
     * Register a listener with the Sensors API for the provided {@link DataSource} and
     * {@link DataType} combo.
     */
    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        // [START register_data_listener]
        mListener = new OnDataPointListener() {
            @Override
            public void onDataPoint(DataPoint dataPoint) {
                for (Field field : dataPoint.getDataType().getFields()) {
                    Value val = dataPoint.getValue(field);
                    MainEventBus.notifyStick(new HeartMonitorEvent(val.toString()));
                }
            }
        };

        Fitness.SensorsApi.add(
                mClient,
                new SensorRequest.Builder()
                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
                        .setDataType(dataType) // Can't be omitted.
                        .setSamplingRate(10, TimeUnit.SECONDS)
                        .build(),
                mListener)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Toast.makeText(MainActivity.this,"Listener registered!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,"Listener not registered!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}