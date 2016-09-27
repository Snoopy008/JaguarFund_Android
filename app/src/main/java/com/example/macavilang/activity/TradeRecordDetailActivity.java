package com.example.macavilang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.NetValueModel;
import com.example.macavilang.model.TradeRecordDetailModel;
import com.example.macavilang.model.TradeRecordModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeRecordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_record_detail);

        getTradeRecordDetailData();

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
        telephoneText.setText(model.getMobile());

        TextView identityTypeText = (TextView)findViewById(R.id.identityType);
        identityTypeText.setText(model.getPidType());

        TextView identityNumberText = (TextView)findViewById(R.id.identityNumber);
        identityNumberText.setText(model.getPid());

        TextView addressText = (TextView)findViewById(R.id.address);
        addressText.setText(model.getAddress());

        TextView fundNameText = (TextView)findViewById(R.id.productName);
        fundNameText.setText(model.getFundName());

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
