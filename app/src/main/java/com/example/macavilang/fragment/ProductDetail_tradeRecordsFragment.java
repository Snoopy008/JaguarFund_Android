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
import com.example.macavilang.activity.TradeRecordDetailActivity;
import com.example.macavilang.activity.TradeRecordListActivity;
import com.example.macavilang.adapter.TradeRecordAdapter;
import com.example.macavilang.jaguarfund_android.R;
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
public class ProductDetail_tradeRecordsFragment extends Fragment {


    private View rootView;
    private int currentPage =1;
    private int totalPage;
    private PullToRefreshListView productTradeRecordListView;
    private SearchView productTradeRecordSearchView;
    private List<TradeRecordModel> tradeRecords;
    private TradeRecordAdapter productTradeRecordAdapter;
    public ProductDetail_tradeRecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_trade_records, container, false);

        productTradeRecordListView = (PullToRefreshListView) rootView.findViewById(R.id.productTradeRecordListView);
        productTradeRecordSearchView = (SearchView) rootView.findViewById(R.id.productTradeRecordSearchView);
        productTradeRecordListView.setMode(PullToRefreshBase.Mode.BOTH);
        productTradeRecordAdapter = new TradeRecordAdapter(getContext());
        productTradeRecordListView.setAdapter(productTradeRecordAdapter);
        getProductTradeRecordsData("",currentPage,false);




        //ListView点击行
        productTradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TradeRecordModel tradeRecordModel = tradeRecords.get(i);
                Intent intent = new Intent(getContext(),TradeRecordDetailActivity.class);
                intent.putExtra("tradeRecordId",tradeRecordModel.getId());
                startActivity(intent);
            }
        });


        //SearchView点击搜索
        productTradeRecordSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getProductTradeRecordsData(s,currentPage,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        productTradeRecordListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (productTradeRecordListView.isShownHeader()){
                    currentPage = 1;
                    if (productTradeRecordSearchView.toString().equals(null))
                    {
                        getProductTradeRecordsData("",currentPage,false);
                    }else {
                        getProductTradeRecordsData(productTradeRecordSearchView.getQuery().toString(),currentPage,false);
                    }

                    Log.e("-------","refresh");
                }

                //上拉加载
                if (productTradeRecordListView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (productTradeRecordSearchView.toString().equals(null))
                        {
                            getProductTradeRecordsData("",currentPage,true);
                        }else {
                            getProductTradeRecordsData(productTradeRecordSearchView.getQuery().toString(),currentPage,true);
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                    }
                    productTradeRecordListView.onRefreshComplete();
                }
            }
        });

        return rootView;
    }


    public void getProductTradeRecordsData(String searchWords, int page, final Boolean isLoad){
        Bundle bundle = getArguments();
        if (bundle != null) {
            String productId = bundle.getString("productId");
            RequestQueue productTradeRecordListQueue = Volley.newRequestQueue(getContext());
            String productTradeUrl = getResources().getString(R.string.baseURL) + "/api/fund/products/" + productId + "/history";
            Uri.Builder productTradeBuildUrl = Uri.parse(productTradeUrl).buildUpon();
            productTradeBuildUrl.appendQueryParameter("keyWords",searchWords);
            productTradeBuildUrl.appendQueryParameter("page", String.valueOf(page));
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
                            if (isLoad){
                                List<TradeRecordModel> moreTradeRecords = (List<TradeRecordModel>) gson.fromJson(productTradeRecordJson,productTradeRecordListType);
                                tradeRecords.addAll(moreTradeRecords);
                            }else {
                                tradeRecords = (List<TradeRecordModel>) gson.fromJson(productTradeRecordJson,productTradeRecordListType);

                            }
                            productTradeRecordAdapter.updatetradeList(tradeRecords);
                            productTradeRecordAdapter.notifyDataSetChanged();
                            productTradeRecordListView.onRefreshComplete();



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
