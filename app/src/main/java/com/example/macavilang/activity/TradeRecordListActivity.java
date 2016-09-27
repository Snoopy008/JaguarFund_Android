package com.example.macavilang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class TradeRecordListActivity extends AppCompatActivity {

    private int currentPage =1;
    private int totalPage;
    private PullToRefreshListView tradeRecordListView;
    private SearchView TradeRecordSearchView;
    private List<TradeRecordModel> tradeRecords;
    private TradeRecordAdapter tradeRecordAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_record_list);
        tradeRecordListView = (PullToRefreshListView) findViewById(R.id.TradeRecordListView);
        TradeRecordSearchView = (SearchView) findViewById(R.id.TradeRecordSearchView);
        tradeRecordListView.setMode(PullToRefreshBase.Mode.BOTH);
        tradeRecordAdapter = new TradeRecordAdapter(this);
        tradeRecordListView.setAdapter(tradeRecordAdapter);
        getTradeRecordListData("",currentPage,false);

        tradeRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TradeRecordModel tradeRecordModel = tradeRecords.get(i);
                Intent intent = new Intent(TradeRecordListActivity.this,TradeRecordDetailActivity.class);
                intent.putExtra("tradeRecordId",tradeRecordModel.getId());
                startActivity(intent);
            }
        });

        //SearchView点击搜索
        TradeRecordSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getTradeRecordListData(s,currentPage,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        tradeRecordListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (tradeRecordListView.isShownHeader()){
                    currentPage = 1;
                    if (TradeRecordSearchView.toString().equals(null))
                    {
                        getTradeRecordListData("",currentPage,false);
                    }else {
                        getTradeRecordListData(TradeRecordSearchView.getQuery().toString(),currentPage,false);
                    }

                    Log.e("-------","refresh");
                }

                //上拉加载
                if (tradeRecordListView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (TradeRecordSearchView.toString().equals(null))
                        {
                            getTradeRecordListData("",currentPage,true);
                        }else {
                            getTradeRecordListData(TradeRecordSearchView.getQuery().toString(),currentPage,true);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                    }
                    tradeRecordListView.onRefreshComplete();
                }
            }
        });

    }



    public void getTradeRecordListData(String searchWords, int page, final Boolean isLoad){
        RequestQueue tradeRecordListQueue = Volley.newRequestQueue(this);
        String tradeUrl = getResources().getString(R.string.baseURL) + "api/fund/trades";
        Uri.Builder tradeBuildUrl = Uri.parse(tradeUrl).buildUpon();
        tradeBuildUrl.appendQueryParameter("keyWords",searchWords);
        tradeBuildUrl.appendQueryParameter("page", String.valueOf(page));
        tradeBuildUrl.appendQueryParameter("pageSize","10");
        tradeBuildUrl.appendQueryParameter("sort","-tradeDate");
        String tradeTotalUrl = tradeBuildUrl.build().toString();
        StringRequest tradeRecordRequest = new StringRequest(Request.Method.GET, tradeTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {

                        Log.e("tradeRecordError",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement tradeRecordJson = jsonElement.getAsJsonObject().get("list");
                        Type tradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                        if (isLoad){
                            List<TradeRecordModel> moreTradeRecords = (List<TradeRecordModel>) gson.fromJson(tradeRecordJson,tradeRecordListType);
                            tradeRecords.addAll(moreTradeRecords);
                        }else {
                            tradeRecords = (List<TradeRecordModel>) gson.fromJson(tradeRecordJson,tradeRecordListType);
                        }
                        tradeRecordAdapter.updatetradeList(tradeRecords);
                        tradeRecordAdapter.notifyDataSetChanged();
                        tradeRecordListView.onRefreshComplete();



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
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        tradeRecordListQueue.add(tradeRecordRequest);
    }
}
