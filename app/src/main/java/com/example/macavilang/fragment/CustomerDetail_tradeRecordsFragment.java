package com.example.macavilang.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.activity.TradeRecordDetailActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetail_tradeRecordsFragment extends Fragment {


    private View rootView;
    public CustomerDetail_tradeRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_customer_detail_trade_records,container,false);
        getCustomerTradeRecordsData();
        return rootView;
    }


    public void getCustomerTradeRecordsData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String customerId = bundle.getString("customerId");
            RequestQueue customerTradeRecordListQueue = Volley.newRequestQueue(getContext());
            String customerTradeUrl = getResources().getString(R.string.baseURL) + "api/fund/clients/trades/" + customerId;
            Uri.Builder customerTradeBuildUrl = Uri.parse(customerTradeUrl).buildUpon();
            customerTradeBuildUrl.appendQueryParameter("keyWords","");
            customerTradeBuildUrl.appendQueryParameter("page","1");
            customerTradeBuildUrl.appendQueryParameter("pageSize","10");
            customerTradeBuildUrl.appendQueryParameter("sort","-tradeDate");
            String customerTradeTotalUrl = customerTradeBuildUrl.build().toString();
            StringRequest customerTradeRecordRequest = new StringRequest(Request.Method.GET, customerTradeTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {

                            Log.e("tradeRecordError",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement customerTradeRecordJson = jsonElement.getAsJsonObject().get("list");
                            Type customerTradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                            final List<TradeRecordModel> tradeRecords = (List<TradeRecordModel>) gson.fromJson(customerTradeRecordJson,customerTradeRecordListType);

                            ListView customerTradeRecordListView = (ListView)rootView.findViewById(R.id.customerTradeRecordListView);
                            TradeRecordAdapter customerTradeRecordAdapter = new TradeRecordAdapter(getContext(),tradeRecords);
                            customerTradeRecordListView.setAdapter(customerTradeRecordAdapter);

                            customerTradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    TradeRecordModel tradeRecordModel = tradeRecords.get(i);
                                    Intent intent = new Intent(getContext(),TradeRecordDetailActivity.class);
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
                    SharedPreferences preferences = getContext().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                    String urlToken = preferences.getString("token",null);
                    header.put("X-Auth-Token",urlToken);
                    return header;
                }
            };
            customerTradeRecordListQueue.add(customerTradeRecordRequest);
        }
    }

}
