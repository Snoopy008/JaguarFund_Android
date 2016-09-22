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
import com.example.macavilang.adapter.ProductNetValueListAdapter;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductNetValueHistoryModel;
import com.example.macavilang.model.ProductNetValuePercentModel;
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
public class ProductDetail_netValueHistoryFragment extends Fragment {


    private View rootView;
    public ProductDetail_netValueHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_netvalue_history,container,false);
        getNetValuePercentData();
        getProductNetValueListData();
        return rootView;
    }


    public void getNetValuePercentData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue netValuePercentQueue = Volley.newRequestQueue(getContext());
            String netValuePercentUrl = getResources().getString(R.string.baseURL) + "api/fund/products/roi/" + productId;
            StringRequest productTradeRecordRequest = new StringRequest(Request.Method.GET, netValuePercentUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {
                            Type productNetValuePercentType = new TypeToken<ProductNetValuePercentModel>(){}.getType();
                            final ProductNetValuePercentModel productNetValuePercentModel = (ProductNetValuePercentModel) gson.fromJson(response,productNetValuePercentType);
                            TextView rencentOneMonth = (TextView) rootView.findViewById(R.id.rencentOneMonth);
                            rencentOneMonth.setText(productNetValuePercentModel.getOneMonthAgoRate());

                            TextView rencentThreeMonth = (TextView) rootView.findViewById(R.id.rencentThreeMonth);
                            rencentThreeMonth.setText(productNetValuePercentModel.getThreeMonthAgoRate());

                            TextView rencentSixMonth = (TextView) rootView.findViewById(R.id.rencentSixMonth);
                            rencentSixMonth.setText(productNetValuePercentModel.getSixMonthAgoRate());

                            TextView rencentOneYear = (TextView) rootView.findViewById(R.id.rencentOneYear);
                            rencentOneYear.setText(productNetValuePercentModel.getOneYearAgoRate());

                            TextView rencentThreeYear = (TextView) rootView.findViewById(R.id.rencentThreeYear);
                            rencentThreeYear.setText(productNetValuePercentModel.getThreeYearAgoRate());

                            TextView fromFound = (TextView) rootView.findViewById(R.id.fromFound);
                            fromFound.setText(productNetValuePercentModel.getTotalRate());

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
            netValuePercentQueue.add(productTradeRecordRequest);
        }


    }

    public void getProductNetValueListData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue productNetValueListQueue = Volley.newRequestQueue(getContext());
            String netValueListUrl = getResources().getString(R.string.baseURL) + "api/fund/products/net-value";
            Uri.Builder productNetValueListBuildUrl = Uri.parse(netValueListUrl).buildUpon();
            productNetValueListBuildUrl.appendQueryParameter("productId",productId);
            productNetValueListBuildUrl.appendQueryParameter("page","1");
            productNetValueListBuildUrl.appendQueryParameter("pageSize","15");
            productNetValueListBuildUrl.appendQueryParameter("sort","-marketDate");
            String productNetValueListTotalUrl = productNetValueListBuildUrl.build().toString();
            StringRequest productNetValueListRequest = new StringRequest(Request.Method.GET, productNetValueListTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {

                            Log.e("tradeRecordError",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement productNetValueListJson = jsonElement.getAsJsonObject().get("list");
                            Type productNetValueListType = new TypeToken<List<ProductNetValueHistoryModel>>(){}.getType();
                            final List<ProductNetValueHistoryModel> productNetValueHistorys = (List<ProductNetValueHistoryModel>) gson.fromJson(productNetValueListJson,productNetValueListType);

                            ListView productNetValueListView = (ListView)rootView.findViewById(R.id.prodcutNetValueListView);
                            ProductNetValueListAdapter productNetValueListAdapter = new ProductNetValueListAdapter(getContext(),productNetValueHistorys);
                            productNetValueListView.setAdapter(productNetValueListAdapter);
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
            productNetValueListQueue.add(productNetValueListRequest);
        }
    }



}
