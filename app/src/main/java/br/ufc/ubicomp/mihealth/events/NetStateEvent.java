package br.ufc.ubicomp.mihealth.events;

/**
 * Created by vladymirbezerra on 24/04/15.
 */
public class NetStateEvent extends MiEvent {
    public enum  ConnState {CONNECTED, DISCONNECTED}
    public final ConnState state;

    public NetStateEvent(ConnState state){
        super("NetworkState");
        this.state = state;
    }
}
