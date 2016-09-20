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
import com.example.macavilang.activity.ProductDetailActivity;
import com.example.macavilang.adapter.ProductListAdapter;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;
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
public class ProductFragment extends Fragment {


    private View rootView;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product,container,false);
        getProductListData();
        return rootView;

    }


    public void getProductListData(){
        RequestQueue productListQueue = Volley.newRequestQueue(getContext());
        String productUrl = getResources().getString(R.string.baseURL) + "api/fund/products";
        Uri.Builder productBuildUrl = Uri.parse(productUrl).buildUpon();
        productBuildUrl.appendQueryParameter("keyWords","");
        productBuildUrl.appendQueryParameter("page","1");
        productBuildUrl.appendQueryParameter("pageSize","10");
        productBuildUrl.appendQueryParameter("sort","-fundTotalAmount");
        String productTotalUrl = productBuildUrl.build().toString();
        StringRequest productListRequest = new StringRequest(Request.Method.GET, productTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("productListError",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement productListJson = jsonElement.getAsJsonObject().get("list");
                        Type productListType = new TypeToken<List<ProductModel>>(){}.getType();
                        final List<ProductModel> products = (List<ProductModel>) gson.fromJson(productListJson,productListType);

                        ListView productListView = (ListView)rootView.findViewById(R.id.productListView);
                        ProductListAdapter productListAdapter = new ProductListAdapter(getContext(),products);
                        productListView.setAdapter(productListAdapter);

                        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ProductModel productModel = products.get(i);
                                Intent intent = new Intent(getContext(),ProductDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("productModel",productModel);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("productListError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                SharedPreferences preferences = getActivity().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        productListQueue.add(productListRequest);
    }

}
