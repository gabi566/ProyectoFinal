package tienda.app.splash.aplicacionsplash;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String ruta = "https://gabrielhosting.000webhostapp.com/registro.php";
    private Map<String, String> parametros;
    public RegisterRequest(String nombre, String usuario, String clave, String edad, Response.Listener<String> listener){
        super(Request.Method.POST,ruta, listener,null );
        parametros = new HashMap<>();
        parametros.put("nombre",nombre+"");
        parametros.put("usuario",usuario+"");
        parametros.put("clave",clave+"");
        parametros.put("edad",edad+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parametros;
    }
}
