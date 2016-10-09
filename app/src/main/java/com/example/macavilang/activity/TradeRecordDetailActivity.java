package com.example.macavilang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.adapter.AttachmentFileListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.AttachmentFileModel;
import com.example.macavilang.model.TradeRecordDetailModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeRecordDetailActivity extends AppCompatActivity {

    private ArrayList<Object> fileMainList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_record_detail);


        TextView titleText = (TextView)findViewById(R.id.title_text);
        titleText.setText("交易详情");
        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);

        backBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getTradeRecordDetailData();
        getAttachmentListData();

    }


    public void getAttachmentListData(){
        RequestQueue tradeRecordDetailFilesQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String tradeId = intent.getStringExtra("tradeRecordId");
        Log.e("1111111----",tradeId);
        String tradeRecordDetailFileURL = getResources().getString(R.string.baseURL) + "api/fund/trade/fileList/" + tradeId;
        StringRequest tradeRecordDetailFileRequest = new StringRequest(Request.Method.GET, tradeRecordDetailFileURL,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("TradeRecordDetailFile--",response);
//                        JsonParser jsonParser = new JsonParser();
//                        JsonElement jsonElement = jsonParser.parse(response);
                        Type tradeRecordDetailFileType = new TypeToken<List<AttachmentFileModel>>(){}.getType();
                        List<AttachmentFileModel> fileModels = (List<AttachmentFileModel>) gson.fromJson(response,tradeRecordDetailFileType);

                        fileMainList.add("附件");
                        fileMainList.addAll(fileModels);
                        ListView fileListView = (ListView)findViewById(R.id.tradeRecordDetailFileList);
                        AttachmentFileListAdapter fileListAdapter = new AttachmentFileListAdapter(TradeRecordDetailActivity.this,fileMainList);
                        fileListView.setAdapter(fileListAdapter);


//                        for (AttachmentFileModel file:fileModels) {
//                            downloadFileData(file);
//                        }
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
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        tradeRecordDetailFilesQueue.add(tradeRecordDetailFileRequest);
    }


    public void downloadFileData(AttachmentFileModel fileModel){
        RequestQueue downloadFileQueue = Volley.newRequestQueue(this);
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
        String urlToken = preferences.getString("token",null);
        String tradeRecordDetailFileURL = getResources().getString(R.string.baseURL) + fileModel.getAccessUrl() + "?token=" + urlToken;
        StringRequest downloadFileRequest = new StringRequest(Request.Method.GET, tradeRecordDetailFileURL,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("downloadFile",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("netValueError",error.getMessage(),error);
            }
        }){};
        downloadFileQueue.add(downloadFileRequest);
    }



    public void getTradeRecordDetailData(){
        RequestQueue tradeRecordDetailQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        String tradeId = intent.getStringExtra("tradeRecordId");
        String tradeRecordDetailURL = getResources().getString(R.string.baseURL) + "api/fund/trade/" + tradeId;
        StringRequest tradeRecordDetailRequest = new StringRequest(Request.Method.GET, tradeRecordDetailURL,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("TradeRecordDetail",response);
                        Type tradeRecordDetailType = new TypeToken<TradeRecordDetailModel>(){}.getType();
                        TradeRecordDetailModel tradeRecordModel = (TradeRecordDetailModel) gson.fromJson(response,tradeRecordDetailType);
                        setActivityTextViewText(tradeRecordModel);
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
                SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        tradeRecordDetailQueue.add(tradeRecordDetailRequest);

    }


    public void setActivityTextViewText(TradeRecordDetailModel model){
        TextView customerNameText = (TextView)findViewById(R.id.customerName);
        customerNameText.setText(model.getClientName());

        TextView customerTypeText = (TextView)findViewById(R.id.customerType);
        customerTypeText.setText(model.getCustomerType());

        TextView taCodeText = (TextView)findViewById(R.id.taCode);
        taCodeText.setText(model.getTacode());

        TextView telephoneText = (TextView)findViewById(R.id.telephone);
        telephoneText.setText(model.getClientMobile());

//        TextView identityTypeText = (TextView)findViewById(R.id.identityType);
//        identityTypeText.setText(model.getPidType());

        TextView identityNumberText = (TextView)findViewById(R.id.identityNumber);
        identityNumberText.setText(model.getPid());

        TextView addressText = (TextView)findViewById(R.id.address);
        addressText.setText(model.getClientAddress());

        TextView fundNameText = (TextView)findViewById(R.id.productName);
        fundNameText.setText(model.getProductName());

        TextView fundNumberText = (TextView)findViewById(R.id.fundNumber);
        fundNumberText.setText(model.getFundCode());

        TextView contractNumberText = (TextView)findViewById(R.id.contractNumber);
        contractNumberText.setText(model.getContractNo());

        TextView tradeTypeText = (TextView)findViewById(R.id.tradeType);
        tradeTypeText.setText(model.getTradeType());

        TextView tradeNetValueText = (TextView)findViewById(R.id.tradeNetValue);
        tradeNetValueText.setText(model.getUnitPrice());

        TextView currenNetValueText = (TextView)findViewById(R.id.currenNetValue);
        currenNetValueText.setText(model.getLatestNetValue());

        TextView tradeShareText = (TextView)findViewById(R.id.tradeShare);
        tradeShareText.setText(model.getTradeShare());

        TextView tradeAmountText = (TextView)findViewById(R.id.tradeAmount);
        tradeAmountText.setText(model.getTradeAmount());

        TextView bankAccountText = (TextView)findViewById(R.id.bankAccount);
        bankAccountText.setText(model.getBankAccount());

        TextView bankNameText = (TextView)findViewById(R.id.bankName);
        bankNameText.setText(model.getBankName());
    }
}
