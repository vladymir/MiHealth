package br.ufc.ubicomp.mihealth.services;

import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.service.FitnessSensorService;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;

public class MiHeartMonitorService extends FitnessSensorService implements OnDataPointListener {

    private BleDevice heartMonitorDevice = null;
    private GoogleApiClient sensorClient = null;

    public MiHeartMonitorService() {
        MainEventBus.register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainEventBus.notifyStick(new RequestSensorClientEvent());
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        for (Field field : dataPoint.getDataType().getFields()) {
            Value val = dataPoint.getValue(field);
            HeartMonitorEvent event = new HeartMonitorEvent(val.asActivity());
            MainEventBus.notify(event);
        }
    }

    public void onEvent(ResponseSensorClientEvent response) {
        this.sensorClient = response.client;
        if (this.sensorClient == null) {
            Toast.makeText(this,"Cliente nao conectado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Initialize your software sensor(s).
        // 1. Define a callback object
        Toast.makeText(this,"Tentanto estabelecer conexao com a cinta cardiaca...", Toast.LENGTH_SHORT).show();

        BleScanCallback callback = new BleScanCallback() {
                                    @Override
                                    public void onDeviceFound(BleDevice device) {
                                        heartMonitorDevice = device;
                                        PendingResult<Status> pendingResult = Fitness.BleApi.claimBleDevice(sensorClient, heartMonitorDevice);

                                        ResultCallback<Status> resultCallback = new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {
                                                if (status.isSuccess()) {
                                                    SensorRequest sensorRequest = new SensorRequest.Builder()
                                                                                                .setDataType(DataType.TYPE_HEART_RATE_BPM)
                                                                                                .setSamplingRate(5, TimeUnit.SECONDS)
                                                                                                .build();

                                                    Fitness.SensorsApi.add(sensorClient, sensorRequest, MiHeartMonitorService.this);
                                                }
                                            }
                                        };

                                        pendingResult.setResultCallback(resultCallback);
                                    }
                                    @Override
                                    public void onScanStopped() {
                                    }
                                };

        StartBleScanRequest request = new StartBleScanRequest.Builder()
                                                             .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                                                             .setBleScanCallback(callback)
                                                             .build();

        PendingResult<Status> pendingResult = Fitness.BleApi.startBleScan(sensorClient, request);

    }

    @Override
    public List<DataSource> onFindDataSources(List<DataType> dataTypes) {
        Toast.makeText(MiHeartMonitorService.this,"onFindDataSources(List<DataType> dataTypes)", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Implementar");
    }

    @Override
    public boolean onRegister(FitnessSensorServiceRequest request) {
        Toast.makeText(MiHeartMonitorService.this,"onRegister(FitnessSensorServiceRequest request)", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Implementar");
    }

    @Override
    public boolean onUnregister(DataSource dataSource) {
        Toast.makeText(MiHeartMonitorService.this,"onUnregister(DataSource dataSource)", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Implementar");
    }

}