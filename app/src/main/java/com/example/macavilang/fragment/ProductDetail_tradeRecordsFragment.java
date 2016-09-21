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
import com.example.macavilang.activity.TradeRecordListActivity;
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
public class ProductDetail_tradeRecordsFragment extends Fragment {


    private View rootView;

    public ProductDetail_tradeRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_trade_records, container, false);
        getProductTradeRecordsData();
        return rootView;
    }


    public void getProductTradeRecordsData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue productTradeRecordListQueue = Volley.newRequestQueue(getContext());
            String productTradeUrl = getResources().getString(R.string.baseURL) + "/api/fund/products/" + productId + "/history";
            Uri.Builder productTradeBuildUrl = Uri.parse(productTradeUrl).buildUpon();
            productTradeBuildUrl.appendQueryParameter("keyWords","");
            productTradeBuildUrl.appendQueryParameter("page","1");
            productTradeBuildUrl.appendQueryParameter("pageSize","10");
            productTradeBuildUrl.appendQueryParameter("sort","-tradeDate");
            String productTradeTotalUrl = productTradeBuildUrl.build().toString();
            StringRequest productTradeRecordRequest = new StringRequest(Request.Method.GET, productTradeTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {

                            Log.e("tradeRecordError",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement productTradeRecordJson = jsonElement.getAsJsonObject().get("list");
                            Type productTradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                            final List<TradeRecordModel> tradeRecords = (List<TradeRecordModel>) gson.fromJson(productTradeRecordJson,productTradeRecordListType);

                            ListView productTradeRecordListView = (ListView)rootView.findViewById(R.id.productTradeRecordListView);
                            TradeRecordAdapter productTradeRecordAdapter = new TradeRecordAdapter(getContext(),tradeRecords);
                            productTradeRecordListView.setAdapter(productTradeRecordAdapter);

                            productTradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            productTradeRecordListQueue.add(productTradeRecordRequest);
        }
    }

}
