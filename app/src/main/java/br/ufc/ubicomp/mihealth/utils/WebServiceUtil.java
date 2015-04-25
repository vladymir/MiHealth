package br.ufc.ubicomp.mihealth.utils;

import com.google.gson.Gson;
import com.google.inject.Inject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by vladymirbezerra on 25/04/15.
 */
public class WebServiceUtil {
    private final HttpClient httpClient = new DefaultHttpClient();

    public static void sendData(Gson data) {

    }
}
