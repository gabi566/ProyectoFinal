package tienda.app.splash.aplicacionsplash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText etnombre,etusuario,etpassword,etedad;
    Button btn_registrar,btn_ir_inicioSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etnombre = findViewById(R.id.et_nombre);
        etusuario = findViewById(R.id.et_usuario);
        etpassword = findViewById(R.id.et_password);
        etedad = findViewById(R.id.et_edad);

        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_ir_inicioSesion = (Button) findViewById(R.id.btn_ir_inicioSesion);

        btn_registrar.setOnClickListener(this);

        btn_ir_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }


}