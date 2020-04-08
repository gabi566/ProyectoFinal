package tienda.app.splash.aplicacionsplash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        final TextView mensaje = (TextView)findViewById(R.id.et_bienvenido);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        //int edad = i.getIntExtra("edad",-1);
        mensaje.setText(mensaje.getText()+" "+nombre);
    }
}
