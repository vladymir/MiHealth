package br.ufc.ubicomp.mihealth.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.RequestSensorClientEvent;
import br.ufc.ubicomp.mihealth.events.ResponseSensorClientEvent;

public class MiHeartMonitorService extends FitnessSensorService implements OnDataPointListener {

    private BleDevice heartMonitorDevice = null;
    private GoogleApiClient sensorClient = null;
    private FitnessSensorServiceRequest mRequest;
    private Socket l2capSocket1;

    public MiHeartMonitorService() {
        MainEventBus.register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainEventBus.notifySticky(new RequestSensorClientEvent());
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        Log.d("NCT", "onDataPoint event....");
        for (Field field : dataPoint.getDataType().getFields()) {
            Value val = dataPoint.getValue(field);
            Double value = new Double(val.asFloat());
            HeartMonitorEvent event = new HeartMonitorEvent(value);
            Log.d("NCT", "Received value: " + value);
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
                                                Log.d("NCT", "Bluetooth device claimed....");
                                                if (status.isSuccess()) {
                                                    lerDadosBluetooth();
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
        Log.d("NCT", "Google Fit onFindDataSources");

        List<DataSource> sources = new ArrayList<DataSource>();

        try {
            String mac = this.heartMonitorDevice.getAddress();
            if (mac != null) {

                DataSource.Builder builder = new DataSource.Builder();
                builder.setDataType(DataType.TYPE_HEART_RATE_BPM);
                builder.setName("Ubicomp heart monitor device");
                builder.setType(DataSource.TYPE_RAW);
                builder.setDevice(new Device("Ubicomp", "HM BT", mac, 0));
                DataSource src = builder.build();
                sources.add(src);
            }

        } catch (Exception e) {
            Log.e("NCT", "onFindDataSources", e);
        }

        return sources;
    }

    @Override
    public boolean onRegister(FitnessSensorServiceRequest request) {
        mRequest = request;

        Log.d("NCT", "Google fit request stored");

        String mac = request.getDataSource().getDevice().getUid();
        if (mac != null) {
            //BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

            SensorRequest sensorRequest = new SensorRequest.Builder()
                                                            .setDataType(DataType.TYPE_HEART_RATE_BPM)
                                                            .setSamplingRate(5, TimeUnit.SECONDS)
                                                            .build();

            if (this.sensorClient.isConnected()) {
                Fitness.SensorsApi.add(sensorClient, sensorRequest, MiHeartMonitorService.this);
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onUnregister(DataSource dataSource) {
        mRequest = null;

        if (this.sensorClient.isConnected()) {
            this.sensorClient.disconnect();
        }

        Log.d("NCT", "Google Fit request forgotten");

        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NCT", "Bluetooth service started....");
        return super.onStartCommand(intent, flags, startId);
    }

    private void lerDadosBluetooth() {
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = adapter.getRemoteDevice(heartMonitorDevice.getAddress());
            Log.d("NCT","Lendo dados dispositivo conectado: " + device.getName());
            //BluetoothServerSocket serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(device.getName(), null);
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(null);

            socket.connect();
            if (socket.isConnected()) {
                InputStream iStream = socket.getInputStream();
                int singleByte = iStream.read();
                DoubleBuffer buffer = DoubleBuffer.allocate(20);
                while (singleByte != -1) {
                    buffer.put((byte)singleByte);
                    singleByte = iStream.read();
                }

                Double value = buffer.get();
                HeartMonitorEvent event = new HeartMonitorEvent(value);
                Log.d("NCT", "Received value: " + value);
                MainEventBus.notify(event);

                iStream.close();
            }

            socket.close();

        } catch (IOException e) {
            ErrorHandlerEventBus.signal(new ErrorEvent(e));
        } catch (Exception e) {
            e.printStackTrace();
            ErrorHandlerEventBus.signal(new ErrorEvent(e));
        } catch (Error e) {
            e.printStackTrace();
        }
    }
}