package rohit.smarthome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button onbutton=(Button)findViewById(R.id.button_on);
        Button offbutton=(Button)findViewById(R.id.button_off);

        final RadioButton auto=(RadioButton)findViewById(R.id.button_autolight);
        RadioButton manual=(RadioButton)findViewById(R.id.button_manlight);

        onbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auto.isChecked()){
                lightmode("1");
                }else{
                    lightmanipulate("1");
                }
            }
        });

        offbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightmanipulate("0");
            }
        });

/*
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightmode("1");
            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightmode("0");
            }
        });
*/
    }


    public void lightmanipulate(String mode){
        String url = "http://192.168.43.26:5000/ledmanipulate";

        Map<String,String> params = new HashMap<String, String>();
        params.put("state", mode);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = response.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            Log.e("error",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }


        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void lightmode(String mode){
        String url = "http://192.168.43.26:5000/setlightmode";

        Map<String,String> params = new HashMap<String, String>();
        params.put("mode", mode);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String msg = response.getString("message");
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            Log.e("error",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }


        };
        Volley.newRequestQueue(this).add(postRequest);
    }

}
