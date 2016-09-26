package com.example.macavilang.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
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
import com.example.macavilang.activity.TradeRecordDetailActivity;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.TradeRecordModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetail_tradeRecordsFragment extends Fragment {


    private View rootView;
    private int currentPage =1;
    private int totalPage;
    private PullToRefreshListView customerTradeRecordListView;
    private SearchView customerTradeRecordSearchView;
    private List<TradeRecordModel> tradeRecords;
    private TradeRecordAdapter customerTradeRecordAdapter;
    public CustomerDetail_tradeRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_customer_detail_trade_records,container,false);
        customerTradeRecordListView = (PullToRefreshListView) rootView.findViewById(R.id.customerTradeRecordListView);
        customerTradeRecordSearchView = (SearchView) rootView.findViewById(R.id.customerTradeRecordSearchView);
        customerTradeRecordListView.setMode(PullToRefreshBase.Mode.BOTH);
        customerTradeRecordAdapter = new TradeRecordAdapter(getContext());
        customerTradeRecordListView.setAdapter(customerTradeRecordAdapter);
        getCustomerTradeRecordsData("",currentPage);




        //ListView点击行
        customerTradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TradeRecordModel tradeRecordModel = tradeRecords.get(i);
                Intent intent = new Intent(getContext(),TradeRecordDetailActivity.class);
                intent.putExtra("tradeRecordId",tradeRecordModel.getId());
                startActivity(intent);
            }
        });


        //SearchView点击搜索
        customerTradeRecordSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getCustomerTradeRecordsData(s,currentPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        customerTradeRecordListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (customerTradeRecordListView.isShownHeader()){
                    currentPage = 1;
                    if (customerTradeRecordSearchView.toString().equals(null))
                    {
                        getCustomerTradeRecordsData("",currentPage);
                    }else {
                        getCustomerTradeRecordsData(customerTradeRecordSearchView.getQuery().toString(),currentPage);
                    }

                    Log.e("-------","refresh");
                }

                //上拉加载
                if (customerTradeRecordListView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (customerTradeRecordSearchView.toString().equals(null))
                        {
                            getCustomerTradeRecordsData("",currentPage);
                        }else {
                            getCustomerTradeRecordsData(customerTradeRecordSearchView.getQuery().toString(),currentPage);
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                    }
                    customerTradeRecordListView.onRefreshComplete();
                }
            }
        });
        return rootView;
    }


    public void getCustomerTradeRecordsData(String searchWords, int page){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String customerId = bundle.getString("customerId");
            RequestQueue customerTradeRecordListQueue = Volley.newRequestQueue(getContext());
            String customerTradeUrl = getResources().getString(R.string.baseURL) + "api/fund/clients/trades/" + customerId;
            Uri.Builder customerTradeBuildUrl = Uri.parse(customerTradeUrl).buildUpon();
            customerTradeBuildUrl.appendQueryParameter("keyWords",searchWords);
            customerTradeBuildUrl.appendQueryParameter("page", String.valueOf(page));
            customerTradeBuildUrl.appendQueryParameter("pageSize","10");
            customerTradeBuildUrl.appendQueryParameter("sort","-tradeDate");
            String customerTradeTotalUrl = customerTradeBuildUrl.build().toString();
            StringRequest customerTradeRecordRequest = new StringRequest(Request.Method.GET, customerTradeTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {

                            Log.e("tradeRecordError------",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement customerTradeRecordJson = jsonElement.getAsJsonObject().get("list");
                            JsonElement totalPageJson = jsonElement.getAsJsonObject().get("totalPage");
                            totalPage = totalPageJson.getAsInt();

                            Type customerTradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                            tradeRecords = (List<TradeRecordModel>) gson.fromJson(customerTradeRecordJson,customerTradeRecordListType);

                            customerTradeRecordAdapter.updatetradeList(tradeRecords);
                            customerTradeRecordAdapter.notifyDataSetChanged();
                            customerTradeRecordListView.onRefreshComplete();

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
