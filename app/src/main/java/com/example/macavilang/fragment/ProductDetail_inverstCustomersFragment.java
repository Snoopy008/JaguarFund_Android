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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.activity.CustomerDetailActivity;
import com.example.macavilang.adapter.CustomerListAdapter;
import com.example.macavilang.adapter.ProductListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;
import com.example.macavilang.model.ProductModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetail_inverstCustomersFragment extends Fragment {


    private View rootView;
    private int currentPage =1;
    private int totalPage;
    private SearchView productInverstCustomersSearchView;
    private PullToRefreshListView productInverstCustomer_listView;
    private List<CustomerModel> customers;
    private CustomerListAdapter productInverstCustomerListAdapter;


    public ProductDetail_inverstCustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_inverst_customers,container,false);
        productInverstCustomersSearchView = (SearchView)rootView.findViewById(R.id.productInverstCustomersSearchView);
        productInverstCustomer_listView = (PullToRefreshListView)rootView.findViewById(R.id.productInverstCustomersListView);
        productInverstCustomer_listView.setMode(PullToRefreshBase.Mode.BOTH);
        productInverstCustomerListAdapter = new CustomerListAdapter(getContext(),true);
        productInverstCustomer_listView.setAdapter(productInverstCustomerListAdapter);
        getproductInverstCustomersData("",currentPage,false);

        //SearchView点击搜索
        productInverstCustomersSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getproductInverstCustomersData(s,currentPage,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        productInverstCustomer_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (productInverstCustomer_listView.isShownHeader()){
                    currentPage = 1;
                    if (productInverstCustomersSearchView.toString().equals(null))
                    {
                        getproductInverstCustomersData("",currentPage,false);
                    }else {
                        getproductInverstCustomersData(productInverstCustomersSearchView.getQuery().toString(),currentPage,false);
                    }
                }

                //上拉加载
                if (productInverstCustomer_listView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (productInverstCustomersSearchView.toString().equals(null))
                        {
                            getproductInverstCustomersData("",currentPage,true);
                        }else {
                            getproductInverstCustomersData(productInverstCustomersSearchView.getQuery().toString(),currentPage,true);
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                        productInverstCustomer_listView.onRefreshComplete();
                    }
                }
            }
        });


        return rootView;
    }

    public void getproductInverstCustomersData(String searchWords, int page, final Boolean isLoad){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue productInverstCustomerListQueue = Volley.newRequestQueue(getContext());
            String productInverstCustomersUrl = getResources().getString(R.string.baseURL) + "api/fund/products/" + productId + "/clients";
            Uri.Builder productInverstCustomersBuildUrl = Uri.parse(productInverstCustomersUrl).buildUpon();
            productInverstCustomersBuildUrl.appendQueryParameter("keyWords",searchWords);
            productInverstCustomersBuildUrl.appendQueryParameter("page", String.valueOf(page));
            productInverstCustomersBuildUrl.appendQueryParameter("pageSize","10");
            productInverstCustomersBuildUrl.appendQueryParameter("sort","-investShareAmount");
            String productInverstCustomersTotalUrl = productInverstCustomersBuildUrl.build().toString();
            StringRequest productInverstCustomerListRequest = new StringRequest(Request.Method.GET, productInverstCustomersTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {
                            Log.e("product_customer",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement productInverstCustomerListJson = jsonElement.getAsJsonObject().get("list");
                            Type productInverstCustomerListType = new TypeToken<List<CustomerModel>>(){}.getType();
                            if (isLoad){
                                List<CustomerModel> moreCustomers = (List<CustomerModel>) gson.fromJson(productInverstCustomerListJson,productInverstCustomerListType);
                                customers.addAll(moreCustomers);
                            }else {
                                customers = (List<CustomerModel>) gson.fromJson(productInverstCustomerListJson,productInverstCustomerListType);
                            }
                            productInverstCustomerListAdapter.updateCustomerList(customers);
                            productInverstCustomerListAdapter.notifyDataSetChanged();
                            productInverstCustomer_listView.onRefreshComplete();




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
