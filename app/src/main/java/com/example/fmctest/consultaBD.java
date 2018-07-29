package com.example.fmctest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class consultaBD extends AppCompatActivity  {

    private TextView textText;
    private RequestQueue request;
    private ProgressDialog progreso;


    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_bd);

        textText = findViewById(R.id.textid);


        consultarUsuario();
    }

    private void consultarUsuario() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();

        request = Volley.newRequestQueue(this);
        Integer id =1;
        String url = "http://192.168.1.4/pruebaBD/JSONConsulta.php?documento="+id.toString();


        //jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,


                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progreso.hide();

                        Toast.makeText(getBaseContext(),
                                " Funciona!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Tu codigo entra aqui !!!");


                        Usuario miUsuario = new Usuario();

                        JSONArray json = response.optJSONArray("employees");
                        JSONObject jsonObject;

                        try {
                            jsonObject = json.getJSONObject(0);
                            miUsuario.setNombre(jsonObject.optString("last_name"));
                            textText.setText(miUsuario.getNombre());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },new Response.ErrorListener() {
                     @Override
                        public void onErrorResponse(VolleyError volleyError) {

                         progreso.hide();
                         String message = null;
                         if (volleyError instanceof NetworkError) {
                             message = "Cannot connect to Internet..." +
                                     "Please check your connection!";
                         } else if (volleyError instanceof ServerError) {
                             message = "The server could not be found. " +
                                     "Please try again after some time!!";
                         } else if (volleyError instanceof AuthFailureError) {
                             message = "Cannot connect to Internet..." +
                                     "Please check your connection!";
                         } else if (volleyError instanceof ParseError) {
                             message = "Parsing error! Please try again after some time!!";
                         } else if (volleyError instanceof NoConnectionError) {
                             message = "Cannot connect to Internet..." +
                                     "Please check your connection!";
                         } else if (volleyError instanceof TimeoutError) {
                             message = "Connection TimeOut! " +
                                     "Please check your internet connection.";
                         }

                            Toast.makeText(getBaseContext(),
                        message, Toast.LENGTH_SHORT).show();
            }
        });



        // Add the request to the RequestQueue.
        request.add(jsonObjectRequest);
    }



}
