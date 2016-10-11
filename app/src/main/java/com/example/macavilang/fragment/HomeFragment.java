package com.example.macavilang.fragment;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.activity.TradeRecordDetailActivity;
import com.example.macavilang.adapter.HomeAdapter;
import com.example.macavilang.model.BirthdayMessageModel;
import com.example.macavilang.model.NetValueModel;
import com.example.macavilang.model.OpenDayMessageModel;
import com.example.macavilang.model.RememberMessageModel;
import com.example.macavilang.model.TradeRecordModel;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.WarningPriceMessageModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private SharedPreferences preferences;
    private RequestQueue homeQueue;
    private String urlToken;
    private List<TradeRecordModel> tradeRecords = new ArrayList<>();
    private List<NetValueModel> netValues = new ArrayList<>();
    private ArrayList<Object> mainList= new ArrayList<>();
    private HomeAdapter home_adapter;
    private ListView homelistView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_home,container,false);
        homeQueue = Volley.newRequestQueue(getContext());
        homelistView = (ListView) rootView.findViewById(R.id.homeListView);


        preferences = getActivity().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
        urlToken = preferences.getString("token",null);
        home_adapter = new HomeAdapter(getContext(),mainList);
        homelistView.setAdapter(home_adapter);
        getRecentTradeRecordData();

        homelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Object item = adapterView.getItemAtPosition(i);
                if (item instanceof TradeRecordModel)
                {
                    TradeRecordModel tradeRecordModel = (TradeRecordModel)item;
                    Intent intent = new Intent(getContext(),TradeRecordDetailActivity.class);
                    intent.putExtra("tradeRecordId",tradeRecordModel.getId());
                    startActivity(intent);

                }
            }
        });
        return rootView;
    }


    public void getRememberMessageData(){
        String rememberMessageURL = getResources().getString(R.string.baseURL) + "api/home/remind";
        Uri.Builder rememberMessageBuildUrl = Uri.parse(rememberMessageURL).buildUpon();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        Date currentDate = new Date(System.currentTimeMillis());
        String yearStr = yearFormat.format(currentDate);
        String monthStr = monthFormat.format(currentDate);
        rememberMessageBuildUrl.appendQueryParameter("year",yearStr);
        rememberMessageBuildUrl.appendQueryParameter("month",monthStr);
        String rememberMessageTotalUrl = rememberMessageBuildUrl.build().toString();

        StringRequest rememberMessageRequest = new StringRequest(Request.Method.GET, rememberMessageTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("HomeRememberMessage",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement birthdayJson = jsonElement.getAsJsonObject().get("birthdayList");
                        Type birthdayListType = new TypeToken<List<BirthdayMessageModel>>(){}.getType();
                        List<BirthdayMessageModel> birthdays = (List<BirthdayMessageModel>) gson.fromJson(birthdayJson,birthdayListType);
                        mainList.add("事件提醒");
                        for (BirthdayMessageModel model:birthdays) {
                            if(isRangeOfThreeDays(model.getClientBirthday())){
                                RememberMessageModel messageModel = new RememberMessageModel();

                                messageModel.setRememberMessageStr(model.getBirthdayMessageStr());
                                mainList.add(messageModel);
                            }
                        }

                        JsonElement openDayJson = jsonElement.getAsJsonObject().get("openDayList");
                        Type openDayListType = new TypeToken<List<OpenDayMessageModel>>(){}.getType();
                        List<OpenDayMessageModel> openDays = (List<OpenDayMessageModel>) gson.fromJson(openDayJson,openDayListType);
                        for (OpenDayMessageModel model:openDays) {
                            if (isRangeOfThreeDays(model.getOpenDay()))
                            {
                                RememberMessageModel messageModel = new RememberMessageModel();
                                messageModel.setRememberMessageStr(model.getOpenDayMessageStr());
                                mainList.add(messageModel);
                            }
                        }

                        JsonElement warningPriceJson = jsonElement.getAsJsonObject().get("warningPriceList");
                        Type warningPriceListType = new TypeToken<List<WarningPriceMessageModel>>(){}.getType();
                        List<WarningPriceMessageModel> warningPrices = (List<WarningPriceMessageModel>) gson.fromJson(warningPriceJson,warningPriceListType);
                        for (WarningPriceMessageModel model:warningPrices) {
                            RememberMessageModel messageModel = new RememberMessageModel();
                            messageModel.setRememberMessageStr(model.getWarningPriceMessageStr());
                            mainList.add(messageModel);
                        }

                        home_adapter.setAllItemList(mainList);
                        home_adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("rememberMessageError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        homeQueue.add(rememberMessageRequest);
    }


    public void getNetValueData(){
        String netValueURL = getResources().getString(R.string.baseURL) + "api/home/recentNv";
        StringRequest netValueRequest = new StringRequest(Request.Method.GET, netValueURL,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
            @Override
            public void onResponse(String response) {
                Log.e("HomeNetValue",response);
                Type netValueListType = new TypeToken<List<NetValueModel>>(){}.getType();
                netValues = (List<NetValueModel>) gson.fromJson(response,netValueListType);
                mainList.add("净值");
                mainList.addAll(netValues);
                home_adapter.setAllItemList(mainList);
                home_adapter.notifyDataSetChanged();

                getRememberMessageData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("netValueError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        homeQueue.add(netValueRequest);

    }


    public void getRecentTradeRecordData(){
        String tradeUrl = getResources().getString(R.string.baseURL) + "api/fund/trades";
        Uri.Builder tradeBuildUrl = Uri.parse(tradeUrl).buildUpon();
        tradeBuildUrl.appendQueryParameter("keyWords","");
        tradeBuildUrl.appendQueryParameter("page","1");
        tradeBuildUrl.appendQueryParameter("pageSize","3");
        tradeBuildUrl.appendQueryParameter("sort","-tradeDate");
        String tradeTotalUrl = tradeBuildUrl.build().toString();
        StringRequest tradeRecordRequest = new StringRequest(Request.Method.GET, tradeTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
            @Override
            public void onResponse(String response) {

                Log.e("HomeTradeRecord",response);
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(response);
                JsonElement tradeRecordJson = jsonElement.getAsJsonObject().get("list");
                Type tradeRecordListType = new TypeToken<List<TradeRecordModel>>(){}.getType();
                tradeRecords = (List<TradeRecordModel>) gson.fromJson(tradeRecordJson,tradeRecordListType);
                mainList.add("最新交易");
                mainList.addAll(tradeRecords);
                home_adapter.setAllItemList(mainList);
                home_adapter.notifyDataSetChanged();

                getNetValueData();

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
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        homeQueue.add(tradeRecordRequest);
    }


    @TargetApi(Build.VERSION_CODES.N)
    public Boolean isRangeOfThreeDays(String dateStr){


        Date nowDate = new Date();
        long nowTime = nowDate.getTime();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date getDate = null;

        try {
            getDate = format.parse(dateStr);
            long getTime = getDate.getTime();
            long threeDayFromNowTime = nowTime + (1000 * 60 * 60 * 24)*3;
            if (getTime >= nowTime && getTime <= threeDayFromNowTime){
                return true;
            }else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }








}
