package br.ufc.ubicomp.mihealth.events;

import br.ufc.ubicomp.mihealth.enums.NetworkState;

public class NetStateEvent extends MiEvent {
    public final NetworkState state;

    public NetStateEvent(NetworkState state){
        super("NetworkState");
        this.state = state;
    }
}
