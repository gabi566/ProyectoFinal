package tienda.app.splash.aplicacionsplash.Activitys;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import tienda.app.splash.aplicacionsplash.R;
import tienda.app.splash.aplicacionsplash.WebViews.AcercaActivity;

public class MainActivity extends AppCompatActivity {

    Button ir_login,btn_acerca;

    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mp = MediaPlayer.create(this,R.raw.lock);
        ir_login=findViewById(R.id.ir_login);
        ir_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
            }
        });

        btn_acerca=findViewById(R.id.btn_acerca);
        btn_acerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            startActivity(new Intent(MainActivity.this, AcercaActivity.class));
            }
        });


    }
}
