package br.ufc.ubicomp.mihealth.events;

public class ErrorEvent extends MiEvent {

    public final Exception exception;

    public ErrorEvent(Exception exception) {
        super("Error event.");

        this.exception = exception;
    }
}
