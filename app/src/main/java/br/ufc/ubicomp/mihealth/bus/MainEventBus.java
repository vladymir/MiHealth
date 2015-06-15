package br.ufc.ubicomp.mihealth.bus;

import android.util.Log;

import br.ufc.ubicomp.mihealth.events.MiEvent;
import de.greenrobot.event.EventBus;

/**
 * Barramento principal de execução do MiHealth. Notificação de eventos de negócio
 * e comunicação entre os componentes.
 *
 * TODO implementar:
 * 1. Fila de notificação de eventos
 * 2. Separação de interesse na notificação dos eventos
 *
 * Created by nelson on 25/04/15.
 */
public class MainEventBus {

    private static final EventBus bus = EventBus.getDefault();

    public static void register(Object subscriber) {
        bus.register(subscriber);
    }

    public static void notify(MiEvent event) {
        bus.post(event);
    }

    // TODO Refatorar
    public static void notifySticky(MiEvent event) {
        bus.postSticky(event);
    }
}
