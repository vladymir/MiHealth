package br.ufc.ubicomp.mihealth.context;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;
import br.ufc.ubicomp.mihealth.events.WeatherEvent;
import br.ufc.ubicomp.mihealth.sensors.BodyTemperatureSensorManager;
import br.ufc.ubicomp.mihealth.sensors.Collectable;
import br.ufc.ubicomp.mihealth.sensors.HashCollectable;
import br.ufc.ubicomp.mihealth.sensors.HeartMonitorSensorManager;
import br.ufc.ubicomp.mihealth.sensors.WeatherSensorManager;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class ContextManager {

    private static ContextManager instance;
    private CollectContextData contextReader;
    private Context context;

    private ContextManager(Context context) {
        MainEventBus.register(this);
        this.context = context;
        contextReader = new CollectContextData(context);
        HashCollectable weather = new WeatherSensorManager();
        HashCollectable bodyTemp = new BodyTemperatureSensorManager();
        HashCollectable heartBeat = new HeartMonitorSensorManager();
        contextReader.addSensor(weather);
        contextReader.addSensor(bodyTemp);
        contextReader.addSensor(heartBeat);
        new Thread(contextReader).start();
    }

    public static ContextManager getInstance(Context context) {
        if(instance == null) {
            instance = new ContextManager(context);
        }
        return instance;
    }

    public void onEvent(AggregateContext aggregate) {
        Log.d("Futures", aggregate.toString());
        List<Tuple> contextData = getMapsFromAggregate(aggregate);
    }

    private List<Tuple> getMapsFromAggregate(AggregateContext aggregateContext) {
        List<Tuple> contextData = new ArrayList<>();
        for(Future<Tuple> future : aggregateContext.futures) {
            try {
                contextData.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return contextData;
    }

    private List<Tuple> makeInference(List<Tuple<Sensor,Double>> contextData) {
        for(Tuple<Sensor,Double> context : contextData) {
            switch (context.first) {
                case BODYTEMPERATURE:
                    break;
                case WEATHERTEMPERATURE:
                    break;
                case HEARTBEAT:
                    break;
                default:
                    break;
            }
        }
        return null;
    }
}
