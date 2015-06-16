package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import br.ufc.ubicomp.mihealth.R;


public class MasterFooter extends Activity {

    ImageButton main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_footer);
        Log.d("US", "OrientationChange.onCreate");
        // detect the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;

       main= (ImageButton) findViewById(R.id.mainF);



        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MasterFooter.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("US", "MasterFooter.onConfigurationChanged");


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("US",
                    "MasterFooter.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait


        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "MasterFooter.onConfigurationChanged (ORIENTATION_LANDSCAPE)");

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("US", "OrientationChange.onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("US", "OrientationChange.onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("US", "OrientationChange.onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("US", "OrientationChange.onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("US", "OrientationChange.onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_footer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
