package tienda.app.splash.aplicacionsplash.WebViews;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;;
import tienda.app.splash.aplicacionsplash.R;

public class AcercaActivity extends AppCompatActivity {

    ImageButton ir_facebook;
    ImageButton ir_youtube;
    ImageButton ir_twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca);
        ir_facebook=findViewById(R.id.ir_facebook);
        ir_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcercaActivity.this,Main2Activity.class));

            }
        });

        ir_youtube=findViewById(R.id.ir_youtube);
        ir_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcercaActivity.this,Main3Activity.class));

            }
        });

        ir_twitter=findViewById(R.id.ir_twitter);
        ir_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AcercaActivity.this,Main4Activity.class));

            }
        });
    }
}