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
import com.example.macavilang.adapter.CustomerInverstProductListAdapter;
import com.example.macavilang.adapter.CustomerListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerInverstProductModel;
import com.example.macavilang.model.CustomerModel;
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
public class CustomerDetail_inverstProductsFragment extends Fragment {


    private View rootView;

    public CustomerDetail_inverstProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_customer_detail_inverst_products,container,false);
        getCustomerInverstProductsData();
        return rootView;
    }



    public void getCustomerInverstProductsData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String customerId = bundle.getString("customerId");
            RequestQueue customerInverstProductsListQueue = Volley.newRequestQueue(getContext());
            String customerInverstProductsUrl = getResources().getString(R.string.baseURL) + "api/fund/clients/" + customerId;
            StringRequest customerInverstProductsListRequest = new StringRequest(Request.Method.GET, customerInverstProductsUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {
                            Log.e("customerListError",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement customerInverstProductsListJson = jsonElement.getAsJsonObject().get("investInfoList");
                            Type customerInverstProductsListType = new TypeToken<List<CustomerInverstProductModel>>(){}.getType();
                            final List<CustomerInverstProductModel> customerInverstProducts = (List<CustomerInverstProductModel>) gson.fromJson(customerInverstProductsListJson,customerInverstProductsListType);

                            ListView customerInverstProducts_listView = (ListView)rootView.findViewById(R.id.customerInverstProductsListView);
                            CustomerInverstProductListAdapter customerInverstProductsListAdapter = new CustomerInverstProductListAdapter(getContext(),customerInverstProducts);
                            customerInverstProducts_listView.setAdapter(customerInverstProductsListAdapter);


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
            customerInverstProductsListQueue.add(customerInverstProductsListRequest);
        }
    }

}
