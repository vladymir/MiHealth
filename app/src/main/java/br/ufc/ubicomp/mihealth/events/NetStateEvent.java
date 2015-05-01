package br.ufc.ubicomp.mihealth.events;

import br.ufc.ubicomp.mihealth.enums.NetworkState;

/**
 * Created by vladymirbezerra on 24/04/15.
 */
public class NetStateEvent extends MiEvent {
    public final NetworkState state;

    public NetStateEvent(NetworkState state){
        super("NetworkState");
        this.state = state;
    }
}
