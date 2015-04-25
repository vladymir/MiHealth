package br.ufc.ubicomp.mihealth.events;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Created by vladymirbezerra on 09/04/15.
 */
public abstract class MiEvent {
    public final String message;

    public MiEvent(String message){
        this.message = message;
    }
}
