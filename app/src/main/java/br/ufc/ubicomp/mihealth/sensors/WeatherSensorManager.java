package br.ufc.ubicomp.mihealth.sensors;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import br.ufc.ubicomp.mihealth.bus.MainEventBus;
import br.ufc.ubicomp.mihealth.enums.Sensor;
import br.ufc.ubicomp.mihealth.events.WeatherEvent;
import br.ufc.ubicomp.mihealth.utils.Tuple;

public class WeatherSensorManager extends MiSensorManager implements HashCollectable {

    private final String URL = "http://api.openweathermap.org/data/2.5/weather?q=Fortaleza,br";

    @Override
    public Tuple<Sensor, Double> collect() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if(null != entity) {
                String responseBody = EntityUtils.toString(entity, HTTP.UTF_8);
                JSONObject json = new JSONObject(responseBody);
                Double temp = new Double(json.getJSONObject("main").getString("temp"));
                return new Tuple<>(Sensor.WEATHERTEMPERATURE, (temp-273));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Tuple<>(Sensor.WEATHERTEMPERATURE, 0.0);
    }

    @Override
    public Tuple<Sensor, Double> call() throws Exception {
        Tuple<Sensor, Double> result = this.collect();
        MainEventBus.notify(new WeatherEvent(result.second));
        return result;
    }
}
