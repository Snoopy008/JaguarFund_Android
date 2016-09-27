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
import com.example.macavilang.activity.ProductDetailActivity;
import com.example.macavilang.activity.TradeRecordDetailActivity;
import com.example.macavilang.adapter.CustomerListAdapter;
import com.example.macavilang.adapter.ProductListAdapter;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;
import com.example.macavilang.model.ProductModel;
import com.example.macavilang.model.TradeRecordModel;
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
public class CustomerFragment extends Fragment {


    private View rootView;
    private int currentPage =1;
    private int totalPage;
    private SearchView customer_searchView;
    private PullToRefreshListView customer_listView;
    private List<CustomerModel> customers;
    private CustomerListAdapter customerListAdapter;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_customer,container,false);
        customer_searchView = (SearchView)rootView.findViewById(R.id.customer_searchView);
        customer_listView = (PullToRefreshListView)rootView.findViewById(R.id.customer_listView);
        customer_listView.setMode(PullToRefreshBase.Mode.BOTH);
        customerListAdapter = new CustomerListAdapter(getContext(),false);
        customer_listView.setAdapter(customerListAdapter);
        getCustomerListData("",currentPage,false);



        //ListView点击行
        customer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerModel customerModel = customers.get(i);
                Intent intent = new Intent(getContext(),CustomerDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("customerModel",customerModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //SearchView点击搜索
        customer_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getCustomerListData(s,currentPage,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        customer_listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (customer_listView.isShownHeader()){
                    currentPage = 1;
                    if (customer_searchView.toString().equals(null))
                    {
                        getCustomerListData("",currentPage,false);
                    }else {
                        getCustomerListData(customer_searchView.getQuery().toString(),currentPage,false);
                    }
                }

                //上拉加载
                if (customer_listView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (customer_searchView.toString().equals(null))
                        {
                            getCustomerListData("",currentPage,true);
                        }else {
                            getCustomerListData(customer_searchView.getQuery().toString(),currentPage,true);
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                        customer_listView.onRefreshComplete();
                    }
                }
            }
        });
        return rootView;
    }

    public void getCustomerListData(String searchWords, int page, final Boolean isLoad){
        RequestQueue customerListQueue = Volley.newRequestQueue(getContext());
        String customerUrl = getResources().getString(R.string.baseURL) + "api/fund/clients";
        Uri.Builder customerBuildUrl = Uri.parse(customerUrl).buildUpon();
        customerBuildUrl.appendQueryParameter("keyWords",searchWords);
        customerBuildUrl.appendQueryParameter("page", String.valueOf(page));
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
                        JsonElement totalPageJson = jsonElement.getAsJsonObject().get("totalPage");
                        totalPage = totalPageJson.getAsInt();
                        Type customerListType = new TypeToken<List<CustomerModel>>(){}.getType();
                        if (isLoad){
                           List<CustomerModel> moreCustomers = (List<CustomerModel>) gson.fromJson(customerListJson,customerListType);
                            customers.addAll(moreCustomers);
                        }else {
                            customers = (List<CustomerModel>) gson.fromJson(customerListJson,customerListType);
                        }


                        customerListAdapter.updateCustomerList(customers);
                        customerListAdapter.notifyDataSetChanged();
                        customer_listView.onRefreshComplete();

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
