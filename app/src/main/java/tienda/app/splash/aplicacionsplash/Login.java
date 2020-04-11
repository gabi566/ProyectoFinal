package tienda.app.splash.aplicacionsplash;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button btn_ir_crearCuenta;
    private RadioButton RBsesion;
    private boolean isActivateRadioButton;
    private static final String STRING_PREFERENCES = "tienda.app.splash.aplicacionsplash.SharedPreferences";
    private static final String PREFERENCES_ESTADO_BUTTOM = "estado.Buttom.sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        if(obtenerEstadoButtom()){
            Intent bienvenido = new Intent(Login.this, MenuPrincipal.class);
            Login.this.startActivity(bienvenido);
            Login.this.finish();
        }

        final Button btn_iniciar_sesion = (Button)findViewById(R.id.btn_iniciar_sesion);
        final EditText usuariot = (EditText)findViewById(R.id.et_correo);
        final EditText clavet = (EditText)findViewById(R.id.et_password);

        btn_ir_crearCuenta=findViewById(R.id.btn_ir_crearCuenta);
        btn_ir_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
                finish();
            }
        });

        RBsesion = (RadioButton) findViewById(R.id.Login_Sesion);
        isActivateRadioButton = RBsesion.isChecked(); //boton desactivado
        RBsesion.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View v) {
                if(isActivateRadioButton){
                    RBsesion.setChecked(false);
                }
                isActivateRadioButton = RBsesion.isChecked();
            }
        });


        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarEstadoButtom();
                final String usuario = usuariot.getText().toString();
                final String clave = clavet.getText().toString();

                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonrespuesta = new JSONObject(response);
                            boolean ok = jsonrespuesta.getBoolean("success");
                            if
                            (usuario.isEmpty()){
                                usuariot.setError("Usuario Obligatorio");
                            }else if(clave.isEmpty()){
                                clavet.setError("Contraseña Obligatoria");
                            }else if(ok==true) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Cargando...").setNegativeButton("Reintentar",null).create().show();
                                Intent bienvenido = new Intent(Login.this, MenuPrincipal.class);
                                Login.this.startActivity(bienvenido);
                                finish();
                            }else {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Usuario o contraseña incorrectos").setNegativeButton("Reintentar",null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };

                LoginRequest r = new LoginRequest(usuario,clave,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);
            }
        });
    }


    public void guardarEstadoButtom(){
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        preferences.edit().putBoolean(PREFERENCES_ESTADO_BUTTOM,RBsesion.isChecked()).apply();
    }

    public boolean obtenerEstadoButtom() {
        SharedPreferences preferences = getSharedPreferences(STRING_PREFERENCES,MODE_PRIVATE);
        return preferences.getBoolean(PREFERENCES_ESTADO_BUTTOM,false);
    }

}
