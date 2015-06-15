package br.ufc.ubicomp.mihealth.context;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.sensors.Collectable;
import br.ufc.ubicomp.mihealth.sensors.HashCollectable;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class CollectContextData implements Runnable {

    BlockingQueue<Collectable> sensors = new LinkedBlockingQueue<Collectable>();
    int timeBetweenCollectInSeconds;
    boolean isActive;
    ExecutorService pool;
    Context context;


    public CollectContextData(Context context) {
        //SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE);
        this.context = context;
        this.timeBetweenCollectInSeconds = 5; //prefs.getInt("TimeBetweenReadings", 5);
        isActive = true;
        pool = Executors.newSingleThreadExecutor();
    }

    synchronized public void addSensor(Collectable newSensor) {
        this.sensors.offer(newSensor);
    }

    synchronized public void removeSensor(Collectable oldSensor) {
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
        synchronized (sensors) {
            while (isActive) {
                List<Future<Tuple<Sensor,Double>>> futures = new ArrayList<>();
                for (Collectable sensor : sensors) {
                    futures.add(pool.submit(sensor));
                }

                try {
                    MainEventBus.notify(new AggregateContext(futures));
                    Log.d("CONTEXTCOLLECTOR", "Running context reader");
                    Thread.sleep(timeBetweenCollectInSeconds * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
