package br.ufc.ubicomp.mihealth.events;

/**
 * Created by nelson on 25/04/15.
 */
public class ErrorEvent extends MiEvent {

    public final Exception exception;

    public ErrorEvent(Exception exception) {
        super("Error event.");

        this.exception = exception;
    }
}
