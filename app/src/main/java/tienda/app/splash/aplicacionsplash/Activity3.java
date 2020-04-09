package tienda.app.splash.aplicacionsplash;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * CLASE QUE CORRESPONDE AL ACTIVITY 3 Y SUS COMPONENTES
 * @author Bastian Vidal
 * @version 21/09/2018
 */
public class Activity3 extends AppCompatActivity {
    //DECLARACIÓN DE VARIABLES
    private Producto producto = new Producto();
    private Producto[] cervezas = producto.cervezas;
    private Producto[] pizzas = producto.pizzas;
    private TextView nombre,detalle,precio,cant;
    private ImageView imagen1;
    private String valor;


    @Override
    /**
     * Métedo onCreate por defecto
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        getSupportActionBar().hide();

        //Instanciacion de XML a JAVA
        nombre = findViewById(R.id.txt_nombre);
        detalle = findViewById(R.id.txt_desc);
        imagen1 = findViewById(R.id.imagen1);
        precio = findViewById(R.id.txt_precio);
        cant = findViewById(R.id.txt_cantidad);
        //OBTENEMOS LA POSICION QUE ENVIA EL  ACTIVITY2
        Bundle bundle = getIntent().getExtras();
        //POSICION OBTENIDA ES DECLARADA COMO INTEGER
        int id = Integer.parseInt(bundle.getString("posicion"));
        //Se obtiene además el nombre del tipo de producto que se mostrarán
        String producto  = bundle.getString("producto");

        //INSTANCIAMOS LA CLASE PRODUCTO Y LOS ARREGLOS CORRESPONDIENTES PARA SELECCIÓN
        Producto bebida = Producto.cervezas[id];
        Producto pizza = Producto.pizzas[id];

        //LUEGO DE EVALUAR EL TIPO DE PRODUCTO ESCOGIDO SE VISUALIZAN LOS DATOS CORRESPONDIENTES
        // INSTANCIADOS EN LOS COMPONENTES DEL ACTIVITY3
        if (producto.equals("cerveza")) {//En el caso de que la varaiable "producto" obtenida sea cerveza
            //Los TextView son definidos con sus valores correspondientes
            nombre.setText(bebida.getNombre());
            detalle.setText(bebida.getDescripcion());
            imagen1.setImageResource(bebida.getImagenID());
            valor = String.valueOf(bebida.getPrecio());
            precio.setText("Precio: $" + valor);
        }//Fin If

        if (producto.equals("pizza")) {//En el caso de que la varaiable "producto" obtenida sea pizza
            //Los TextView son definidos con sus valores correspondientes
            nombre.setText(pizza.getNombre());
            detalle.setText(pizza.getDescripcion());
            imagen1.setImageResource(pizza.getImagenID());
            valor = String.valueOf(pizza.getPrecio());
            precio.setText("Precio: $" + valor);
        }//Fin If
    }//Fin onCreate()

    /**
     * Método utilizado para el botón que aumenta la cantidad del producto
     * @param view Parámetro por defecto
     */
    public void agregar(View view){
        //Obtiene el valor del TextView
        String valor = cant.getText().toString();
        //Se convierte  Integer
        int aux = Integer.parseInt(valor);
        //Se define el valor de una suma de + 1 en el TextView
        cant.setText(""+(aux+1));
    }//Fin agregar()

    /**
     * Método utilizado para el botón que desminuye la cantidad del producto
     * @param view
     */
    public void restar(View view){
        //Obtiene el valor del TextView
        String valor = cant.getText().toString();
        //Se convierte  Integer
        int aux = Integer.parseInt(valor);
        //Se define el valor de una resta de - 1 en el TextView, y en el caso de que el valor
        //sea igual a 1, se mantiene
        if (aux == 1){
            cant.setText(""+1);
        }else {
            cant.setText("" + (aux - 1));
        }//Fin If
    }//Fin restar()

    //Instanciación de la clase HelperBD para utilizar la conexión a SQLite
    HelperBD helperBD = new HelperBD(this);

    /**
     * Método encargado de agregar el Item a la base de datos SQLite
     * @param view Parámetro por defecto
     */
    public void agregarcarrito(View view){
        //Se habilita la base de datos para ingresar datos
        SQLiteDatabase db = helperBD.getWritableDatabase();
        //Se define una variable del tipo ContentValues dado que es el formato con el que funciona el ingreso de datos en una BD
        // del tipo SQLite
        ContentValues values = new ContentValues();
        //Se intgresan los valores del producto en la variable values
        values.put(EstructuraBD.COLUMNA2, nombre.getText().toString());
        values.put(EstructuraBD.COLUMNA3, detalle.getText().toString());
        values.put(EstructuraBD.COLUMNA4, cant.getText().toString());
        values.put(EstructuraBD.COLUMNA5, valor);
        //Se insertan los datos en la BD enviándo como parámetro el nombre de la tabla y la variable values con los datos
        long newRowId = db.insert(EstructuraBD.TABLE_NAME, null, values);
        //Se informa por pantalla ingreso exitoso
        Toast.makeText(getApplicationContext(),"SE HA AÑADIDO EL ARTÍCULO AL CARRITO" ,Toast.LENGTH_LONG).show();
    }//Fin agregarcarrito()
}//Fin Class

