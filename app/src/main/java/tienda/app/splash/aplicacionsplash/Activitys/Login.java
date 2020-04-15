package tienda.app.splash.aplicacionsplash.Activitys;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import tienda.app.splash.aplicacionsplash.clases.LoginRequest;
import tienda.app.splash.aplicacionsplash.R;

public class Login extends AppCompatActivity {
    Button btn_ir_crearCuenta;
    MediaPlayer mp,mp2;
    private RadioButton RBsesion;
    private ProgressDialog mPdLogin;
    private boolean isActivateRadioButton;
    private static final String STRING_PREFERENCES = "tienda.app.splash.aplicacionsplash.SharedPreferences";
    private static final String PREFERENCES_ESTADO_BUTTOM = "estado.Buttom.sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mPdLogin = new ProgressDialog(this);

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
                mp.start();
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

        mp = MediaPlayer.create(this,R.raw.lock);
        mp2 = MediaPlayer.create(this,R.raw.win);
        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
                mPdLogin.setMessage("Iniciando sesion...");
                mPdLogin.show();
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
                                mp2.start();
                                mPdLogin.dismiss();
                            }else if(clave.isEmpty()){
                                clavet.setError("Contraseña Obligatoria");
                                mp2.start();
                                mPdLogin.dismiss();
                            }else if(ok==true) {
                                Intent bienvenido = new Intent(Login.this, MenuPrincipal.class);
                                Login.this.startActivity(bienvenido);
                                mPdLogin.dismiss();
                                finish();
                            }else {
                                mp2.start();
                                mPdLogin.dismiss();
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
