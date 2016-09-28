package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.TradeRecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macavilang on 16/9/19.
 */
public class TradeRecordAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context ctx;
    private List<TradeRecordModel> tradeList;

    public TradeRecordAdapter(Context context){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        tradeList = new ArrayList<TradeRecordModel>();
    }


    public void updatetradeList(List<TradeRecordModel> trades){
        this.tradeList = trades;
    }




    @Override
    public int getCount() {
        return tradeList.size();

    }

    @Override
    public Object getItem(int i) {
        return tradeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        TradeRecordModel tradeRecordModel = tradeList.get(i);
        view1 = mInflater.inflate(R.layout.layout_customer_trade_record_list_item,null);

        TextView tradeInfo = (TextView)view1.findViewById(R.id.tradeInfo);
        tradeInfo.setText(tradeRecordModel.getTradeRecordStr());

        TextView perNetValueText = (TextView) view1.findViewById(R.id.perNetValue);
        perNetValueText.setText("单位净值:" + tradeRecordModel.getUnitPriceView() + "元");

        TextView amountMoneyText = (TextView) view1.findViewById(R.id.amountMoney);
        amountMoneyText.setText("总金额:" + tradeRecordModel.getTradeAmount() + "元");

        TextView tradeDateText = (TextView) view1.findViewById(R.id.tradeDate);
        tradeDateText.setText(tradeRecordModel.getTradeDate());
        return view1;
    }
}
