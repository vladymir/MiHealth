package br.ufc.ubicomp.mihealth.sensors;


import android.os.AsyncTask;

import br.ufc.ubicomp.mihealth.bus.ErrorHandlerEventBus;
import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import br.ufc.ubicomp.mihealth.events.FinalizeEvent;
import br.ufc.ubicomp.mihealth.events.HeartMonitorEvent;

/**
 * Created by nelson on 25/04/15.
 */
public class HeartMonitorSensorManager extends MiSensorManager {

    private HeartMonitorAgent heartMonitorAgent;
    private boolean isActive;

    public HeartMonitorSensorManager() {
        heartMonitorAgent = new HeartMonitorAgent();

        // Assina no EventBus para receber a notificação de finalização de execução
        MainEventBus.register(this);

        this.isActive = true;
        while(this.isActive) {
            heartMonitorAgent.execute();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                ErrorHandlerEventBus.signal( new ErrorEvent(e) );
            }
        }
    }

    public void onEvent(FinalizeEvent finalizeEvent) {
        // Para a execução do sensor
        this.isActive = false;
    }

    public void dispatchEvent(double newHeartFrequency) {
        MainEventBus.notifyStick( new HeartMonitorEvent(newHeartFrequency));
    }

    private class HeartMonitorAgent extends AsyncTask<Void, Void, Double> {
        @Override
        protected Double doInBackground(Void... values) {
            // TODO Pegar valor na cinta de monitoramento
            return new Double( Math.random() * 80 );
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            dispatchEvent(aDouble);
        }
    }

}
