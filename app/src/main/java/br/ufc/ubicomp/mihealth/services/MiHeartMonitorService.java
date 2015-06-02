package br.ufc.ubicomp.mihealth.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.service.FitnessSensorService;
import com.google.android.gms.fitness.service.FitnessSensorServiceRequest;

import java.util.List;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;

public class MiHeartMonitorService extends FitnessSensorService {

    private BleDevice heartMonitorDevice = null;
    private GoogleApiClient sensorClient = null;

    @Override
    public void onCreate() {

        super.onCreate();

        Toast.makeText(this,"Service on create", Toast.LENGTH_SHORT).show();

        MainEventBus.register(this);
        MainEventBus.notify(new RequestSensorClientEvent());
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
                // A device that provides the requested data types is available
                // -> Claim this BLE device (See next example)

                // After the platform invokes your callback
                // with a compatible BLE device (bleDevice):

                // 1. Invoke the Bluetooth Low Energy API with:
                // - The Google API client
                // - The BleDevice object provided in the callback
                heartMonitorDevice = device;
                PendingResult<Status> pendingResult = Fitness.BleApi.claimBleDevice(sensorClient, heartMonitorDevice);
            }
            @Override
            public void onScanStopped() {
                // TODO
                Toast.makeText(MiHeartMonitorService.this, "Erro lendo dados do monitor",Toast.LENGTH_SHORT).show();
            }
        };

        // 2. Create a scan request object:
        // - Specify the data types you're interested in
        // - Provide the callback object
        StartBleScanRequest request = new StartBleScanRequest.Builder()
                .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                .setBleScanCallback(callback)
                .build();

        // 3. Invoke the Bluetooth Low Energy API with:
        // - The Google API client
        // - The scan request
        PendingResult<Status> pendingResult =
                Fitness.BleApi.startBleScan(sensorClient, request);

        // 2. Create DataSource representations of your software sensor(s).
        // 3. Initialize some data structure to keep track of a registration for each sensor.


    }

    @Override
    public List<DataSource> onFindDataSources(List<DataType> dataTypes) {
        // 1. Find which of your software sensors provide the data types requested.
        // 2. Return those as a list of DataSource objects.

        Toast.makeText(MiHeartMonitorService.this, "Implementar onFindDataSources", Toast.LENGTH_SHORT).show();

        return null;
    }

    @Override
    public boolean onRegister(FitnessSensorServiceRequest request) {

        // 1. Determine which sensor to register with request.getDataSource().
        // 2. If a registration for this sensor already exists, replace it with this one.
        // 3. Keep (or update) a reference to the request object.
        // 4. Configure your sensor according to the request parameters.
        // 5. When the sensor has new data, deliver it to the platform by calling
        //    request.getDispatcher().publish(List<DataPoint> dataPoints)

        Toast.makeText(MiHeartMonitorService.this, "Implementar onRegister", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public boolean onUnregister(DataSource dataSource) {

        if (sensorClient == null) {
            MainEventBus.notifyStick(new RequestSensorClientEvent());
            return false;
        }

        // 1. Configure this sensor to stop delivering data to the platform
        // 2. Discard the reference to the registration request object

        // When you no longer need the BLE device

        // 1. Invoke the Bluetooth Low Energy API with:
        // - The Google API client
        // - The BLE device (from the initial scan)
        PendingResult<Status> pendingResult =
                Fitness.BleApi.unclaimBleDevice(sensorClient, heartMonitorDevice);

        // 2. Check the result (see other examples)

        return true;
    }
}