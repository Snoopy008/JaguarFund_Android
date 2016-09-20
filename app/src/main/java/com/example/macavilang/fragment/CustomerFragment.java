package com.example.macavilang.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.adapter.CustomerListAdapter;
import com.example.macavilang.adapter.ProductListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;
import com.example.macavilang.model.ProductModel;
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
public class CustomerFragment extends Fragment {


    private View rootView;
    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_customer,container,false);
        getCustomerListData();
        return rootView;
    }


    public void getCustomerListData(){
        RequestQueue customerListQueue = Volley.newRequestQueue(getContext());
        String customerUrl = getResources().getString(R.string.baseURL) + "api/fund/clients";
        Uri.Builder customerBuildUrl = Uri.parse(customerUrl).buildUpon();
        customerBuildUrl.appendQueryParameter("keyWords","");
        customerBuildUrl.appendQueryParameter("page","1");
        customerBuildUrl.appendQueryParameter("pageSize","10");
        customerBuildUrl.appendQueryParameter("sort","investShareAmountTotal");
        String customerTotalUrl = customerBuildUrl.build().toString();
        StringRequest customerListRequest = new StringRequest(Request.Method.GET, customerTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("customerListError",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement customerListJson = jsonElement.getAsJsonObject().get("list");
                        Type customerListType = new TypeToken<List<CustomerModel>>(){}.getType();
                        final List<CustomerModel> customers = (List<CustomerModel>) gson.fromJson(customerListJson,customerListType);

                        ListView customer_listView = (ListView)rootView.findViewById(R.id.customer_listView);
                        CustomerListAdapter productListAdapter = new CustomerListAdapter(getContext(),customers);
                        customer_listView.setAdapter(productListAdapter);

//                        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                TradeRecordModel tradeRecordModel = tradeRecords.get(i);
//                                Intent intent = new Intent(getContext(),TradeRecordDetailActivity.class);
//                                intent.putExtra("tradeRecordId",tradeRecordModel.getId());
//                                startActivity(intent);
//                            }
//                        });

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
        customerListQueue.add(customerListRequest);
    }

}