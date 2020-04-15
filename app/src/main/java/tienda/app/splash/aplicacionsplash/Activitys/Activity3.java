package tienda.app.splash.aplicacionsplash.Activitys;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tienda.app.splash.aplicacionsplash.BD.EstructuraBD;
import tienda.app.splash.aplicacionsplash.BD.HelperBD;
import tienda.app.splash.aplicacionsplash.BD.Producto;
import tienda.app.splash.aplicacionsplash.R;

public class Activity3 extends AppCompatActivity {
    private Producto producto = new Producto();
    private Producto[] cervezas = producto.cervezas;
    private Producto[] pizzas = producto.pizzas;
    private TextView nombre,detalle,precio,cant;
    private ImageView imagen1;
    private String valor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nombre = findViewById(R.id.txt_nombre);
        detalle = findViewById(R.id.txt_desc);
        imagen1 = findViewById(R.id.imagen1);
        precio = findViewById(R.id.txt_precio);
        cant = findViewById(R.id.txt_cantidad);
        Bundle bundle = getIntent().getExtras();
        int id = Integer.parseInt(bundle.getString("posicion"));
        String producto  = bundle.getString("producto");

        Producto bebida = Producto.cervezas[id];
        Producto pizza = Producto.pizzas[id];


        if (producto.equals("cerveza")) {
            nombre.setText(bebida.getNombre());
            detalle.setText(bebida.getDescripcion());
            imagen1.setImageResource(bebida.getImagenID());
            valor = String.valueOf(bebida.getPrecio());
            precio.setText("Precio: $" + valor);
        }

        if (producto.equals("pizza")) {

            nombre.setText(pizza.getNombre());
            detalle.setText(pizza.getDescripcion());
            imagen1.setImageResource(pizza.getImagenID());
            valor = String.valueOf(pizza.getPrecio());
            precio.setText("Precio: $" + valor);
        }
    }

    public void agregar(View view){
        String valor = cant.getText().toString();
        int aux = Integer.parseInt(valor);
        cant.setText(""+(aux+1));
    }


    public void restar(View view){
        String valor = cant.getText().toString();
        int aux = Integer.parseInt(valor);
        if (aux == 1){
            cant.setText(""+1);
        }else {
            cant.setText("" + (aux - 1));
        }
    }

    HelperBD helperBD = new HelperBD(this);

    public void agregarcarrito(View view){
        SQLiteDatabase db = helperBD.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBD.COLUMNA2, nombre.getText().toString());
        values.put(EstructuraBD.COLUMNA3, detalle.getText().toString());
        values.put(EstructuraBD.COLUMNA4, cant.getText().toString());
        values.put(EstructuraBD.COLUMNA5, valor);
        long newRowId = db.insert(EstructuraBD.TABLE_NAME, null, values);

        Toast.makeText(getApplicationContext(),"SE HA AÑADIDO EL ARTÍCULO AL CARRITO" ,Toast.LENGTH_LONG).show();
    }
}

