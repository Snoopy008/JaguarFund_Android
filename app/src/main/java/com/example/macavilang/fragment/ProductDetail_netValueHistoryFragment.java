package com.example.macavilang.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.macavilang.adapter.ProductNetValueListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductNetValueHistoryModel;
import com.example.macavilang.model.ProductNetValueLineDataModel;
import com.example.macavilang.model.ProductNetValuePercentModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetail_netValueHistoryFragment extends Fragment {


    private View rootView;
    private LineChart perNetValueChart;
    public ProductDetail_netValueHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product_detail_netvalue_history,container,false);
        perNetValueChart = (LineChart)rootView.findViewById(R.id.perNetValueChart);
        getNetValuePercentData();
        getProductNetValueListData();
        getNetValueLineData();
        return rootView;
    }


    public void getNetValueLineData(){
        Bundle bundle = getArguments();
        if (bundle != null){
            String productId = bundle.getString("productId");
            RequestQueue productNetValueLineQueue = Volley.newRequestQueue(getContext());
            final String netValueLineUrl = getResources().getString(R.string.baseURL) + "api/home/timeline";
            Uri.Builder productNetValueLineBuildUrl = Uri.parse(netValueLineUrl).buildUpon();
            productNetValueLineBuildUrl.appendQueryParameter("productId",productId);
            String productNetValueLineTotalUrl = productNetValueLineBuildUrl.build().toString();
            StringRequest productNetValueLineRequest = new StringRequest(Request.Method.GET, productNetValueLineTotalUrl,
                    new Response.Listener<String>() {
                        Gson gson = new Gson();
                        @Override
                        public void onResponse(String response) {
                            Log.e("----------------",response);
                            JsonParser jsonParser = new JsonParser();
                            JsonElement jsonElement = jsonParser.parse(response);
                            JsonElement productNetValueLineJson = jsonElement.getAsJsonObject().get("list");
                            Type productNetValueLineType = new TypeToken<List<ProductNetValueLineDataModel>>(){}.getType();
                            final List<ProductNetValueLineDataModel> productNetValueLineDatas = (List<ProductNetValueLineDataModel>) gson.fromJson(productNetValueLineJson,productNetValueLineType);
                            ArrayList<String> xVals = new ArrayList<String>();
                            ArrayList<Entry> yVals = new ArrayList<Entry>();
                            for (int i = 0; i < productNetValueLineDatas.size();i++){
                                ProductNetValueLineDataModel model = productNetValueLineDatas.get(i);
                                xVals.add(model.getRowVal());
                                float  fc = Float.parseFloat(model.getColVal());
                                yVals.add(new Entry(fc,i));
                            }

                            LineDataSet set1 = new LineDataSet(yVals, "DataSet Line");

                            set1.setCubicIntensity(0.2f);
                            set1.setDrawCubic(true);
                            set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
                            set1.setDrawCircles(true);  //设置有圆点
                            set1.setCircleColor(Color.rgb(222, 171, 105));
                            set1.setLineWidth(2f);    //设置线的宽度
                            set1.setCircleSize(5f);   //设置小圆的大小
                            set1.setHighLightColor(Color.rgb(222, 171, 105));
                            set1.setColor(Color.rgb(247, 235, 217));    //设置曲线的颜色

                            // create a data object with the datasets
                            LineData data = new LineData(xVals, set1);

                            // set data
                            perNetValueChart.setData(data);
                            perNetValueChart.invalidate();


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
            productNetValueLineQueue.add(productNetValueLineRequest);
        }
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
