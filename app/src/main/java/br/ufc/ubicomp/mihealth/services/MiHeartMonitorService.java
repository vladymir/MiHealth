package br.ufc.ubicomp.mihealth.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
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
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.service.FitnessSensorService;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.RegisterDataListenerEvent;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;

public class MiHeartMonitorService extends FitnessSensorService {

    private BleDevice heartMonitorDevice = null;
    private GoogleApiClient sensorClient = null;
    private OnDataPointListener mListener;

    public MiHeartMonitorService() {
        MainEventBus.register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainEventBus.notifyStick(new RequestSensorClientEvent());
    }

    public void onEvent(ResponseSensorClientEvent response) {
        Toast.makeText(this,"Response message", Toast.LENGTH_SHORT).show();

        this.sensorClient = response.client;
        if (this.sensorClient == null) {
            // TODO Refatorar - nada tem a fazer por enquanto
            return;
        }

        // 1. Initialize your software sensor(s).
        // 1. Define a callback object
        BleScanCallback callback = new BleScanCallback() {
            @Override
            public void onDeviceFound(BleDevice device) {
                heartMonitorDevice = device;
                PendingResult<Status> pendingResult = Fitness.BleApi.claimBleDevice(sensorClient, heartMonitorDevice);
            }
            @Override
            public void onScanStopped() {
                Toast.makeText(MiHeartMonitorService.this, "Erro lendo dados do monitor",Toast.LENGTH_SHORT).show();
            }
        };

        StartBleScanRequest request = new StartBleScanRequest.Builder()
                                                             .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                                                             .setBleScanCallback(callback)
                                                             .build();

        PendingResult<Status> pendingResult =
                Fitness.BleApi.startBleScan(sensorClient, request);
    }

    @Override
    public List<DataSource> onFindDataSources(List<DataType> dataTypes) {
        ArrayList<DataSource> dataSources = new ArrayList<DataSource>();
        for (DataType type : dataTypes) {
            if (type.equals(DataType.TYPE_HEART_RATE_BPM)) {
                dataSources.add(new DataSource.Builder().setDataType(type).setType(DataSource.TYPE_RAW).build());
            }
        }

        return dataSources;
    }

    @Override
    public boolean onRegister(FitnessSensorServiceRequest request) {
        //registerFitnessDataListener(request.getDataSource(), DataType.TYPE_HEART_RATE_BPM);
        MainEventBus.notifyStick(new RegisterDataListenerEvent(request.getDataSource()));
        return true;
    }

    @Override
    public boolean onUnregister(DataSource dataSource) {
        if (sensorClient == null) {
            MainEventBus.notifyStick(new RequestSensorClientEvent());
            return false;
        }
        PendingResult<Status> pendingResult =
                Fitness.BleApi.unclaimBleDevice(sensorClient, heartMonitorDevice);
        return true;
    }

}