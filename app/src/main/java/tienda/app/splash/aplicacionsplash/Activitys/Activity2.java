package tienda.app.splash.aplicacionsplash.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import tienda.app.splash.aplicacionsplash.BD.Producto;
import tienda.app.splash.aplicacionsplash.R;

public class Activity2 extends AppCompatActivity {
    ListView listaCervezas;
    ListView listaPizzas;
    public static int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //En la variable "pos" se obtiene el valor de posición enviado a través del intent
        //se declara valor por defecto "-1"
        pos = getIntent().getIntExtra("posicion",-1);
        //Llamado al método para definir los datos de la Listview que corresponda
        definirLista(pos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void definirLista(int pos) {

        if (pos == 0) {//EN CASO DE CERVEZAS
            listaCervezas = findViewById(R.id.lista);
            ArrayAdapter<Producto> adaptador = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, Producto.cervezas);
            listaCervezas.setAdapter(adaptador);
            listaCervezas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position != -1) {//SE REALIZA UN INTENT CON EL ITEM SELECCIONADO DE LA LISTA
                        Intent intento = new Intent(Activity2.this, Activity3.class);
                        intento.putExtra("posicion", String.valueOf(position));
                        intento.putExtra("producto", "cerveza");
                        onPause();
                        startActivity(intento);
                    }
                }//Fin onItemClick
            });
        }

        if (pos == 1) {

            listaPizzas = findViewById(R.id.lista);

            ArrayAdapter<Producto> adaptador = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, Producto.pizzas);
            listaPizzas.setAdapter(adaptador);

            listaPizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != -1) {
                        Intent intento = new Intent(Activity2.this, Activity3.class);
                        intento.putExtra("posicion", String.valueOf(i));
                        intento.putExtra("producto", "pizza");
                        onPause();
                        startActivity(intento);
                    }
                }
            });
        }
    }

    public void onPause(){
        super.onPause();
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = datos.edit();
        editor.putInt("posicion", pos);
        editor.apply();
    }

    public void onResume () {
        super.onResume();
        if(pos == -1){
            SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
            pos = datos.getInt("posicion", 0);
            definirLista(pos);
        }else{
            definirLista(pos);
        }
    }
}
