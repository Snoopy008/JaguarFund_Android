package com.example.macavilang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.jaguarfund_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
    }

    public void loginIn(View source){

        RequestQueue loginRequestQueue = Volley.newRequestQueue(this);

        String loginURL = getResources().getString(R.string.baseURL) + "api/authenticate";
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, loginURL,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LoginResponse",response);
                Gson gson = new Gson();
                HashMap<String,String> loginHashMap = gson.fromJson(response, new TypeToken<HashMap<String,String>>(){}.getType());
                Log.e("loginHashMap", String.valueOf(loginHashMap));
                SharedPreferences preferences = getSharedPreferences("accountInfo",MODE_PRIVATE);
                SharedPreferences.Editor preEditor = preferences.edit();
                preEditor.putString("token",(String)loginHashMap.get("token"));
                preEditor.putString("username",(String)loginHashMap.get("username"));
                preEditor.putString("displayName",(String)loginHashMap.get("displayName"));
                preEditor.commit();

                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MainPageActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LoginError",error.getMessage(),error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map = new HashMap<String, String>();
//                map.put("username",account.getText().toString());
//                map.put("password",password.getText().toString());
                map.put("username","vjfadmin");
                map.put("password","12345678");
//                map.put("username","root");
//                map.put("password","123456");

                return map;
            }
        };
        loginRequestQueue.add(loginStringRequest);


    }
}
