package br.ufc.ubicomp.mihealth.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.adapter.ContatosAdapter;
import br.ufc.ubicomp.mihealth.adapter.MedicinesAdapter;
import br.ufc.ubicomp.mihealth.dao.ContatosDAO;
import br.ufc.ubicomp.mihealth.dao.MedicinesDAO;
import br.ufc.ubicomp.mihealth.model.Contatos;
import br.ufc.ubicomp.mihealth.model.Medicines;


public class ContactsReg extends Activity {

    ImageButton dados_us;
    ImageButton cad_med;
    ImageButton cad_us;
    ImageButton ajust;
    ImageButton main;
    Button _ok;

    private EditText edNome, edTelefone;
    private ContatosDAO contatosDAO;
    private Contatos contatos;
    private int idContatos;

    //método de cadastro
    private void cadastrar(){
        boolean validacao = true;
        String nome = edNome.getText().toString();
        String telefone = edTelefone.getText().toString();

        if(nome == null || nome.equals("") ){validacao=false;
            edNome.setError(getString(R.string.campo_obrigatorio));}

        if(telefone == null || nome.equals("") ){validacao=false;
            edTelefone.setError(getString(R.string.campo_obrigatorio));}

        if(validacao){
            contatos = new Contatos();
            contatos.setNome(nome);
            contatos.setTelefone(telefone);

            //se atualizar
            if(idContatos >0){contatos.set_id(idContatos);}

            long resultado = contatosDAO.salvarContatos(contatos);

            if(resultado != -1){
                if(idContatos > 0){
                    Toast.makeText(this, "Success! ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Success!  ",  Toast.LENGTH_SHORT).show();}
                finish();
                startActivity( new Intent(this, MainActivity.class));
            }else {Toast.makeText(this, "Error! ", Toast.LENGTH_SHORT).show();}

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contato);

        contatosDAO = new ContatosDAO(this);

        edNome = (EditText) findViewById(R.id.edContNome);
        edTelefone= (EditText) findViewById(R.id.edContTel);



        Log.d("US", "OrientationChange.onCreate");
        // detect the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;

        dados_us = (ImageButton) findViewById(R.id.dados_usuario);
        cad_med = (ImageButton) findViewById(R.id.cad_med);
        cad_us = (ImageButton) findViewById(R.id.cad_cont);
        ajust = (ImageButton) findViewById(R.id.ajustes);
        main = (ImageButton) findViewById(R.id.main);
        _ok = (Button) findViewById(R.id.okContReg);

        _ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ContactsReg.this.cadastrar();
            }
        });

        main.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactsReg.this, MainActivity.class);
                startActivity(intent);
            }
        });

        dados_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactsReg.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });

        cad_med.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactsReg.this, MedicinesActivity.class);
                startActivity(intent);
            }


        });

        cad_us.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactsReg.this, ListContactsActivity.class);
                startActivity(intent);
            }
        });
        ajust.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ContactsReg.this, MasterFooter.class);
                startActivity(intent);
            }
        });

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
        contatosDAO.fechar();
        super.onDestroy();
        Log.i("US", "OrientationChange.onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("US",
                "ContactsReg.onConfigurationChanged");


        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i("US",
                    "ContactsReg.onConfigurationChanged (ORIENTATION_PORTRAIT)");
            // setting orientation portrait
            Intent intent = new Intent(ContactsReg.this, ContactsReg.class);
            startActivity(intent);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("US",
                    "ContactsReg.onConfigurationChanged (ORIENTATION_LANDSCAPE)");
            Intent intent = new Intent(ContactsReg.this, ContactsReg.class);
            startActivity(intent);
        }
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
