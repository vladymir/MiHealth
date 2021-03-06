package br.ufc.ubicomp.mihealth.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.services.MiHeartMonitorService;
import br.ufc.ubicomp.mihealth.services.MiService;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, MiService.class);
        context.startService(myIntent);

        Intent anotherIntent = new Intent(context, MiHeartMonitorService.class);
        context.startService(anotherIntent);
    }
}
