package tienda.app.splash.aplicacionsplash.Activitys;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import tienda.app.splash.aplicacionsplash.R;
import tienda.app.splash.aplicacionsplash.clases.RegisterRequest;

public class SignUp extends AppCompatActivity {

    EditText etnombre,etusuario,etpassword,etedad;
    Button btn_registrar,btn_ir_inicioSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        etnombre = findViewById(R.id.et_nombre);
        etusuario = findViewById(R.id.et_usuario);
        etpassword = findViewById(R.id.et_password);
        etedad = findViewById(R.id.et_edad);

        btn_ir_inicioSesion = (Button) findViewById(R.id.btn_ir_inicioSesion);
        btn_ir_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,Login.class));
                finish();
            }
        });

        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = etnombre.getText().toString().trim();
                final String usuario = etusuario.getText().toString().trim();
                final String clave = etpassword.getText().toString().trim();
                final String edad = etedad.getText().toString().trim();


                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonrespuesta = new JSONObject(response);
                            boolean ok = jsonrespuesta.getBoolean("success");
                            if (nombre.isEmpty()){
                                etnombre.setError("Nombre Obligatorio");
                            } else if (usuario.isEmpty()){
                                etusuario.setError("Usuario Obligatorio");
                            }else if (clave.isEmpty()){
                                etpassword.setError("Contraseña Obligatorio");
                            } else if (edad.isEmpty()){
                                etedad.setError("Telefono Obligatorio");
                            } else if (ok == true) {
                                AlertDialog.Builder alerta3 = new AlertDialog.Builder(SignUp.this);
                                alerta3.setMessage("Registrando cuenta...").setNegativeButton("Aceptar",null).create().show();
                                Intent i = new Intent(SignUp.this, Login.class);
                                SignUp.this.startActivity(i);
                                finish();
                            }
                        }catch (Exception e) {
                            AlertDialog.Builder alerta2 = new AlertDialog.Builder(SignUp.this);
                            alerta2.setMessage("Fallo al Registrarse").setNegativeButton("Reintentar",null).create().show();
                        }
                    }
                };

                RegisterRequest r = new RegisterRequest(nombre,usuario,clave,edad,respuesta);
                RequestQueue cola = Volley.newRequestQueue(SignUp.this);
                cola.add(r);
            }
        });
    }

}