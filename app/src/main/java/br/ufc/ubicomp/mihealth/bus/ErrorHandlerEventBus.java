package br.ufc.ubicomp.mihealth.bus;

import br.ufc.ubicomp.mihealth.events.ErrorEvent;
import de.greenrobot.event.EventBus;

/**
 * Barramento específico para a notificação de erros na aplicação.
 *
 * TODO implementar:
 * 1. Fila de notificação de eventos
 * 2. Gerenciamento do escopo de propagação do erro
 * 
 * Created by nelson on 25/04/15.
 */
public class ErrorHandlerEventBus {
    // TODO analisar a possibilidade de criar outro barramento
    private static final EventBus bus = EventBus.getDefault();

    public static void register(Object subscriber) {
        bus.register(subscriber);
    }

    public static void signal(ErrorEvent event) {
        bus.post(event);
    }
}
