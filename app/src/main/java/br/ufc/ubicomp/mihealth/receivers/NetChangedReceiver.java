package br.ufc.ubicomp.mihealth.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.events.NetStateEvent;
import br.ufc.ubicomp.mihealth.utils.NetManager;

import static br.ufc.ubicomp.mihealth.events.NetStateEvent.ConnState.CONNECTED;
import static br.ufc.ubicomp.mihealth.events.NetStateEvent.ConnState.DISCONNECTED;

public class NetChangedReceiver extends BroadcastReceiver {
    public NetChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(NetManager.isOnline(context)) {
            Toast.makeText(context, "Is connected!!", Toast.LENGTH_SHORT).show();
            MainEventBus.notifyStick(new NetStateEvent(CONNECTED));
        } else {
            Toast.makeText(context, "Is offline!!", Toast.LENGTH_SHORT).show();
            MainEventBus.notifyStick(new NetStateEvent(DISCONNECTED));
        }
    }
}
