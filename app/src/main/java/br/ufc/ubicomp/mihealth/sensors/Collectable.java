package br.ufc.ubicomp.mihealth.sensors;

import java.util.concurrent.Callable;

public interface Collectable<A> extends Callable<A> {

    A collect();
}
