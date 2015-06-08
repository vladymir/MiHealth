package br.ufc.ubicomp.mihealth.context;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.sensors.Collectable;

public class CollectContextData implements Runnable {

    List<Collectable> sensors;
    int timeBetweenCollectInSeconds;
    boolean isActive;
    ExecutorService pool;


    public CollectContextData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE);
        this.timeBetweenCollectInSeconds = prefs.getInt("TimeBetweenReadings", 5);
        this.sensors = new ArrayList<Collectable>();
        isActive = true;
        pool = Executors.newSingleThreadExecutor();
    }

    public void addSensor(Collectable newSensor) {
        this.sensors.add(newSensor);
    }

    public void removeSensor(Collectable oldSensor) {
        for(Collectable sensor : sensors) {
            if(sensor == oldSensor) {
                sensors.remove(sensor);
                return;
            }
        }
    }

    public void activate() {
        isActive = true;
    }

    public void onEvent(FinalizeEvent event) {
        isActive = false;
    }

    public void setTimeBetweenCollectInSeconds(int time) {
        this.timeBetweenCollectInSeconds = time;
    }

    @Override
    public void run() {
        while(isActive) {
            for(Collectable sensor : sensors) {
                pool.submit(sensor);
            }
            try {
                Thread.sleep(timeBetweenCollectInSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
