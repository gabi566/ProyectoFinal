package tienda.app.splash.aplicacionsplash.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import tienda.app.splash.aplicacionsplash.BD.EstructuraBD;
import tienda.app.splash.aplicacionsplash.BD.HelperBD;
import tienda.app.splash.aplicacionsplash.R;


public class MenuPrincipal extends AppCompatActivity {

    //DECLARACION DE VARIABLES
    private ListView lista;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);



        prefs = getSharedPreferences("tienda.app.splash.aplicacionsplash.SharedPreferences", Context.MODE_PRIVATE);

        //INSTANCIAR OBJETOS DE XML A JAVA
        lista = findViewById(R.id.opciones);

        //MÉTODO QUE NOS PERMITE ACCEDER A UN ACTIVITY SEGUN LA SELECCIÓN HECHA
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                         @Override
                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                             //SE EVALÚA QUE LA SELECCIÓN NO FUESE NULA
                                             if(position != -1) {
                                                 //En caso de ser cualquier opcion, excepto por el carrito, envía al Activity2
                                                 if (position != 2) {
                                                     Intent intento = new Intent(MenuPrincipal.this, Activity2.class);
                                                     intento.putExtra("posicion", position);
                                                     startActivity(intento);

                                                 } else {//Caso contratio envía al ActivityStore que muestra el carrito
                                                     Intent intento = new Intent(MenuPrincipal.this, ActivityStore.class);
                                                     startActivity(intento);
                                                 }//Fin If
                                             }//Fin If
                                         }//Fin onItemClick()
                                     }//Fin setOnItem..
        );
    }//Fin onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item2:
                removeSharedPreferences();
                Logout();
                return true;
        default:
            return super.onOptionsItemSelected(item);

        }
    }

    private void Logout() {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        try {
            HelperBD helper = new HelperBD(this);
            SQLiteDatabase db = helper.getReadableDatabase();
            db.execSQL(EstructuraBD.SQL_DELETE_REGISTERS);
            onCreate(null);
        }catch (Exception e){

        }//Fin Try-Catch
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }



    boolean doubleBackPressed=false;
    @Override
    public void onBackPressed() {

        if(doubleBackPressed) {
            super.onBackPressed();
        }
        else {
            doubleBackPressed=true;
            final CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorLayout);

            Snackbar.make(coordinatorLayout,getString(R.string.pressbackagain), Snackbar.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed=false;
                }
            },2000);

        }

    }



}//Fin Class

