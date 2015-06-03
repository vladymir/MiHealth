package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import br.ufc.ubicomp.mihealth.R;


public class master_footer extends Activity {

    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_footer);

        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);



        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                    Intent intent = new Intent(master_footer.this, UserRegistrationActivity.class);
                    startActivity(intent);
                }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(master_footer.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(master_footer.this, lista_contato.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(master_footer.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /* metodo exibir dados do usuario
    public void exibirDados() {
        setContentView(R.layout.activity_cadastrar_usuario);
    }

    // metodo exibir lista de medicamentos
    public void exibirListMedic() {
        setContentView(R.layout.activity_lista_medic);
    }

    // metodo exibir lista de contatos
    public void exibirListCont() {
        setContentView(R.layout.activity_lista_contato);
    }
    // metodo exibir ajustes
    public void exibirAjustes() {
        setContentView(R.layout.activity_master_footer);
    }
*/


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
