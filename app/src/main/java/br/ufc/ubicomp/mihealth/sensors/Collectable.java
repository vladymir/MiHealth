package br.ufc.ubicomp.mihealth.sensors;

public interface Collectable<A> extends Runnable {
    public A collect();
}
