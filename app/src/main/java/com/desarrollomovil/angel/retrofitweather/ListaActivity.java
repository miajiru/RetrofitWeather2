package com.desarrollomovil.angel.retrofitweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.desarrollomovil.angel.retrofitweather.Ciudades.cities;

public class ListaActivity extends AppCompatActivity {

    private ListView lista;
    private ArrayAdapter<String> adaptador;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista = (ListView)findViewById(R.id.lista);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Intent call = new Intent(ListaActivity.this,CiudadActivity.class);
                call.putExtra("ciudadPais",cities[pos]);
                startActivity(call);
            }
        });
    }
}
