package tienda.app.splash.aplicacionsplash.Activitys;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tienda.app.splash.aplicacionsplash.BD.EstructuraBD;
import tienda.app.splash.aplicacionsplash.BD.HelperBD;
import tienda.app.splash.aplicacionsplash.R;


public class ActivityStore extends AppCompatActivity {
    Cursor cursor;
    ListView lista;
    TextView total;
    Button btnconfirmar;
    private ProgressDialog mPdConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        definirLista();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPdConfirmar = new ProgressDialog(this);
        btnconfirmar = findViewById(R.id.btn_confirmar);


        btnconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPdConfirmar.setMessage("Solicitando Pedido...");
                mPdConfirmar.show();
                Toast.makeText(ActivityStore.this, "Pedido solicitado correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }




    public void ejecutarQuery(){
        HelperBD helper = new HelperBD(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                EstructuraBD.COLUMNA2,
                EstructuraBD.COLUMNA4,
                EstructuraBD.COLUMNA5
        };
        cursor = db.query(
                EstructuraBD.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }


    public void definirLista(){
        try {
            ejecutarQuery();
            cursor.moveToFirst();
            lista = findViewById(R.id.lista);
            total = findViewById(R.id.txt_total);
            int acum = 0;
            ArrayList<String> producto = new ArrayList<String>();
            do{
                int cant = Integer.parseInt(cursor.getString(1));
                int precio = Integer.parseInt(cursor.getString(2));
                producto.add(cursor.getString(0) + " \nCantidad: " + cant + " \nPrecio unitario: $" + precio +
                        " \nSubtotal: $" + (precio * cant));
                acum = acum + (precio * cant);
            }while (cursor.moveToNext());//Fin Do-While
            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, producto);
            lista.setAdapter(adaptador);
            total.setText(total.getText() + "$" + acum);
            cursor.close();
        }catch(Exception e ){
            Toast.makeText(this,"NO HAY ARTICULOS EN EL CARRITO",Toast.LENGTH_LONG).show();
        }
    }

    public void borrarPedido(View view){
        try {
            HelperBD helper = new HelperBD(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            db.execSQL(EstructuraBD.SQL_DELETE_REGISTERS);
            onCreate(null);
        }catch (Exception e){
            Toast.makeText(this,"SE HA VACIADO EL CARRITO",Toast.LENGTH_LONG).show();
            Intent intento = new Intent(this,MenuPrincipal.class);
            startActivity(intento);
            ActivityStore.this.finish();
        }
    }
}
