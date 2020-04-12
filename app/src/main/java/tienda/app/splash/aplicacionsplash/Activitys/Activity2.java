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

/**CLASE ENCARGADA DE MOSTRAR LOS PRODUCTOS DE CADA TIPO, VISUALIZADOS EN ACTIVITY2
 * @version 20/09/2018
 * @author Bastian Vidal
 */
public class Activity2 extends AppCompatActivity {
    //DECLARACIÓN DE VARIABLES
    ListView listaCervezas;
    ListView listaPizzas;
    public static int pos;

    @Override
    /**
     * Método de creación del activity por defecto
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //En la variable "pos" se obtiene el valor de posición enviado a través del intent
        //se declara valor por defecto "-1"
        pos = getIntent().getIntExtra("posicion",-1);
        //Llamado al método para definir los datos de la Listview que corresponda
        definirLista(pos);
    }//Fin onCreate()


    /**
     * Método encargado de definir los valores del ListView según corresponda
     * @param pos Valor de la posición del item seleccionado en el activity "MainActivity"
     */
    public void definirLista(int pos) {
        //SE EVALÚA EL ITEM SELECCIONADO EN EL ACTIVITY ANTERIOR PARA LISTAR LOS DATOS CORRESPONDIENTES
        if (pos == 0) {//EN CASO DE CERVEZAS
            //SE VISUALIZAN TODOS LOS PRODUCTOS CORRESPONDIENTES AL TIPO SELECCIONADO MEDIANTE UNA LISTA,
            //DE DECLARA EL ARREGLO MEDIANTE UN ADAPTADOR
            listaCervezas = findViewById(R.id.lista);
            //Se define un adaptador que contiene un ArrayList de Producto.cervezas
            ArrayAdapter<Producto> adaptador = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, Producto.cervezas);
            //Definición del adaptador
            listaCervezas.setAdapter(adaptador);
            //Creación de ItemClickListener para el ListView ListaCervezas
            listaCervezas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position != -1) {//SE REALIZA UN INTENT CON EL ITEM SELECCIONADO DE LA LISTA
                        Intent intento = new Intent(Activity2.this, Activity3.class);
                        //SE ENVIAN LOS PARAMETROS NECESARIOS PARA VISUALIZAR EL ITEM EN EL SGTE ACTIVITY
                        intento.putExtra("posicion", String.valueOf(position));
                        intento.putExtra("producto", "cerveza");
                        //LLamado al método onPause()
                        onPause();
                        //Utilización del Intent para llamar al sgte activity
                        startActivity(intento);
                    }//Fin If
                }//Fin onItemClick
            });//Fin setOn..
        }//Fin If

        if (pos == 1) {//EN CASO DE PIZZAS
            //SE VISUALIZAN TODOS LOS PRODUCTOS CORRESPONDIENTES AL TIPO SELECCIONADO MEDIANTE UNA LISTA,
            //DE DECLARA EL ARREGLO MEDIANTE UN ADAPTADOR
            listaPizzas = findViewById(R.id.lista);
            //Se define un adaptador que contiene un ArrayList de Producto.pizzas
            ArrayAdapter<Producto> adaptador = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, Producto.pizzas);
            //Definición de adaptador
            listaPizzas.setAdapter(adaptador);
            //Creación de ItemClickListener para el ListView listaPizzas
            listaPizzas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i != -1) {//SE REALIZA UN INTENT CON EL ITEM SELECCIONADO DE LA LISTA
                        Intent intento = new Intent(Activity2.this, Activity3.class);
                        //SE ENVIAN LOS PARAMETROS NECESARIOS PARA VISUALIZAR EL ITEM EN EL SGTE ACTIVITY
                        intento.putExtra("posicion", String.valueOf(i));
                        intento.putExtra("producto", "pizza");
                        //LLamado al método onPause()
                        onPause();
                        //Utilización del Intent para llamar al sgte activity
                        startActivity(intento);
                    }//Fin If
                }//Fin OnItemClick
            });//Fin SetOn..
        }//Fin If
    }//Fin DefinirLista()

    /**
     * Método heredado de la Super Clase Activity para que al momento de salir del activity actual
     * la ListView quede guardada mediante SharedPreferences y poder volver al activity sin problemas
     */
    public void onPause(){
        //LLamado al método Super
        super.onPause();
        //Instanciacion de SharedPreferences
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        //Se habilita el objeto de tipo SharedPreferences para poder editar el "archivo" local
        SharedPreferences.Editor editor = datos.edit();
        //Se ingresa el valor de posicion traido del activity anterior en el "archivo" mediante un método
        // key + value
        editor.putInt("posicion", pos);
        //Se aplican los cambios, en este caso un ingreso de datos , al "archivo"
        editor.apply();
    }//Fin onPause()

    /**
     * Método heredado de la Super Clase Activity para que al momento de volver al Activity2 se cargen los valores
     * guardados mediante SharedPreferences
     * NOTA: El método onResume, por defecto, es llamado siempre que el Activity es llamado
     */
    public void onResume () {
        //Llamado el método Super
        super.onResume();
        //Si el valor de pos es -1 se obtiene el valor guardado en el "archivo" local mediante SharedPreferences
        if(pos == -1){
            //Se intancia la clase SharedPreferences
            SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
            //Se obtiene el valor de "posicion" guardado en el "archivo" local
            pos = datos.getInt("posicion", 0);
            //LLamado al método definirLista con parámetro obtenido mediante SharedPreferences
            definirLista(pos);
        }else{//En caso contrario, cuando se llama por primera vez el activity.
            //LLamado al método definirListan con parámetro obtenido mediante el getIntent();
            definirLista(pos);
        }//Fin If
    }//Fin onResume()
}//Fin Class
