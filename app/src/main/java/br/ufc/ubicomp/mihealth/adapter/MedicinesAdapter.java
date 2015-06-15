package br.ufc.ubicomp.mihealth.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.ufc.ubicomp.mihealth.R;
import br.ufc.ubicomp.mihealth.dao.DatabaseHelper;
import br.ufc.ubicomp.mihealth.model.Medicines;

public class MedicinesAdapter extends BaseAdapter {
    private Context context;
    private List<Medicines> lista;

    public MedicinesAdapter(Context ctx, List<Medicines> medicinesList){
        this.context= ctx;
        this.lista = medicinesList;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Medicines medicine = lista.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.medicines, null);
        }
        TextView txtNome = (TextView) view.findViewById(R.id.listMedicines);
        txtNome.setText(medicine.getNome());

        return view;
    }
}
