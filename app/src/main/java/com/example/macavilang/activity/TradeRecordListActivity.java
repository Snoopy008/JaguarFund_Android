package com.example.macavilang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.TradeRecordModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeRecordListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_record_list);


        getTradeRecordListData();
    }



    public void getTradeRecordListData(){
        RequestQueue tradeRecordListQueue = Volley.newRequestQueue(this);
        String tradeUrl = getResources().getString(R.string.baseURL) + "api/fund/trades";
        Uri.Builder tradeBuildUrl = Uri.parse(tradeUrl).buildUpon();
        tradeBuildUrl.appendQueryParameter("keyWords","");
        tradeBuildUrl.appendQueryParameter("page","1");
        tradeBuildUrl.appendQueryParameter("pageSize","10");
        tradeBuildUrl.appendQueryParameter("sort","-tradeDate");
        String tradeTotalUrl = tradeBuildUrl.build().toString();
        StringRequest tradeRecordRequest = new StringRequest(Request.Method.GET, tradeTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {

                        Log.e("tradeRecordError",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement tradeRecordJson = jsonElement.getAsJsonObject().get("list");
                        Type tradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                        final List<TradeRecordModel> tradeRecords = (List<TradeRecordModel>) gson.fromJson(tradeRecordJson,tradeRecordListType);

                        ListView tradeRecordListView = (ListView)findViewById(R.id.TradeRecordListView);
                        TradeRecordAdapter tradeRecordAdapter = new TradeRecordAdapter(TradeRecordListActivity.this);
                        tradeRecordAdapter.updatetradeList(tradeRecords);
                        tradeRecordListView.setAdapter(tradeRecordAdapter);

                        tradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    TradeRecordModel tradeRecordModel = tradeRecords.get(i);
                                    Intent intent = new Intent(TradeRecordListActivity.this,TradeRecordDetailActivity.class);
                                    intent.putExtra("tradeRecordId",tradeRecordModel.getId());
                                    startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tradeRecordError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        tradeRecordListQueue.add(tradeRecordRequest);
    }
}
