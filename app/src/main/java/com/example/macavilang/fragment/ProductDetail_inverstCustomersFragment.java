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
import com.example.macavilang.activity.CustomerDetailActivity;
import com.example.macavilang.adapter.CustomerListAdapter;
import com.example.macavilang.jaguarfund_android.R;
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
public class ProductDetail_inverstCustomersFragment extends Fragment {


    private View rootView;

    public ProductDetail_inverstCustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_inverst_customers,container,false);
        getproductInverstCustomersData();
        return rootView;
    }

    public void getproductInverstCustomersData(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue productInverstCustomerListQueue = Volley.newRequestQueue(getContext());
            String productInverstCustomersUrl = getResources().getString(R.string.baseURL) + "api/fund/products/" + productId + "/clients";
            Uri.Builder productInverstCustomersBuildUrl = Uri.parse(productInverstCustomersUrl).buildUpon();
            productInverstCustomersBuildUrl.appendQueryParameter("keyWords","");
            productInverstCustomersBuildUrl.appendQueryParameter("page","1");
            productInverstCustomersBuildUrl.appendQueryParameter("pageSize","10");
            productInverstCustomersBuildUrl.appendQueryParameter("sort","-investShareAmount");
            String productInverstCustomersTotalUrl = productInverstCustomersBuildUrl.build().toString();
            StringRequest productInverstCustomerListRequest = new StringRequest(Request.Method.GET, productInverstCustomersTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {
                            Log.e("customerListError",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement productInverstCustomerListJson = jsonElement.getAsJsonObject().get("list");
                            Type productInverstCustomerListType = new TypeToken<List<CustomerModel>>(){}.getType();
                            final List<CustomerModel> customers = (List<CustomerModel>) gson.fromJson(productInverstCustomerListJson,productInverstCustomerListType);

                            ListView productInverstCustomer_listView = (ListView)rootView.findViewById(R.id.productInverstCustomersListView);
                            CustomerListAdapter productInverstCustomerListAdapter = new CustomerListAdapter(getContext(),customers,true);
                            productInverstCustomer_listView.setAdapter(productInverstCustomerListAdapter);

//                            customer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    CustomerModel customerModel = customers.get(i);
//                                    Intent intent = new Intent(getContext(),CustomerDetailActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("customerModel",customerModel);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

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
            productInverstCustomerListQueue.add(productInverstCustomerListRequest);
        }
    }

}
