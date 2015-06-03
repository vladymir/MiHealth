package br.ufc.ubicomp.mihealth.events;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public abstract class MiEvent {
    public final String message;

    public MiEvent(String message){
        this.message = message;
    }
}
