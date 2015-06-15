package br.ufc.ubicomp.mihealth.context;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.sensors.BodyTemperatureSensorManager;
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
        List<Tuple<Sensor,Double>> contextData = getMapsFromAggregate(aggregate);
        makeInference(contextData);
        MainEventBus.notify(new UpdateUIEvent(contextData));
    }

    private List<Tuple<Sensor,Double>> getMapsFromAggregate(AggregateContext aggregateContext) {
        List<Tuple<Sensor,Double>> contextData = new ArrayList<>();
        for(Future<Tuple<Sensor,Double>> future : aggregateContext.futures) {
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

    private void sendAlert(String message) {
        //SmsUtil.sendSMS("8597493596", message);
    }

    private List<Tuple> makeInference(List<Tuple<Sensor,Double>> contextData) {
        for(Tuple<Sensor,Double> context : contextData) {
            switch (context.first) {
                case BODYTEMPERATURE:
                    if(context.second < 35)
                        sendAlert("Temperatura corporal muito baixa!");
                    else if(context.second > 37)
                        sendAlert("O paciente está com febre!");
                    break;
                case WEATHERTEMPERATURE:
                    break;
                case HEARTBEAT:
                    if(context.second < 60) {
                        sendAlert("Batimentos cardíacos abaixo do normal!");
                    } else if (context.second > 110) {
                        sendAlert("Batimento muito alto!");
                    }
                    break;
                default:
                    break;
            }
        }
        return null;
    }
}
