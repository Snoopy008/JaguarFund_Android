package com.example.macavilang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.macavilang.activity.TradeRecordListActivity;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.NetValueModel;
import com.example.macavilang.model.RememberMessageModel;
import com.example.macavilang.model.TradeRecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macavilang on 16/9/13.
 */
public class HomeAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context ctx;
    private List allItemList;

    public void setAllItemList(List allItemList) {
        this.allItemList = allItemList;
    }


    public HomeAdapter(Context context, List groupItemList)
    {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.allItemList = groupItemList;
        this.ctx = context;
    }


    @Override
    public int getCount() {
        return allItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return allItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        Object item = allItemList.get(i);
        if(item instanceof String){
            view1 = mInflater.inflate(R.layout.layout_customer_trade_record_list_item_tag,null);
            TextView text = (TextView)view1.findViewById(R.id.groupName);
            text.setText((CharSequence) getItem(i));
            Button moreBtn = (Button)view1.findViewById(R.id.tradeMoreBtn);
            if (item.equals("最新交易"))
            {
                moreBtn.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ctx, TradeRecordListActivity.class);
                        ctx.startActivity(intent);
                    }
                });
            }else {
                moreBtn.setVisibility(View.GONE);
            }

        }else if(item instanceof TradeRecordModel){
            TradeRecordModel tradeRecordModel = (TradeRecordModel)item;
            view1 = mInflater.inflate(R.layout.layout_customer_trade_record_list_item,null);

            TextView tradeInfo = (TextView)view1.findViewById(R.id.tradeInfo);
            tradeInfo.setText(tradeRecordModel.getTradeRecordStr());

            TextView perNetValueText = (TextView) view1.findViewById(R.id.perNetValue);
            perNetValueText.setText("单位净值:" + tradeRecordModel.getUnitPriceView() + "元");

            TextView amountMoneyText = (TextView) view1.findViewById(R.id.amountMoney);
            amountMoneyText.setText("总金额:" + tradeRecordModel.getTradeAmount() + "元");

            TextView tradeDateText = (TextView) view1.findViewById(R.id.tradeDate);
            tradeDateText.setText(tradeRecordModel.getTradeDate());
        }else if (item instanceof NetValueModel){
            NetValueModel netValueModel = (NetValueModel)item;
            view1 = mInflater.inflate(R.layout.layout_net_value_rencent_data_list_item,null);

            TextView productNameText = (TextView) view1.findViewById(R.id.productName);
            productNameText.setText(netValueModel.getProductName());

            TextView perNetValueText = (TextView) view1.findViewById(R.id.prouctPerNetValue);
            perNetValueText.setText("单位净值:" + netValueModel.getLatestMarketPrice() + "元");

            TextView amountNetValueText = (TextView) view1.findViewById(R.id.productAmountNetValue);
            amountNetValueText.setText("累计净值:" + netValueModel.getLatestAccumulativeMarketPrice() + "元");

            TextView netValueDateText = (TextView) view1.findViewById(R.id.netValueDate);
            netValueDateText.setText(netValueModel.getLatestMarketDate());
        }else if(item instanceof RememberMessageModel){
            RememberMessageModel rememberMessageModel = (RememberMessageModel)item;
            view1 = mInflater.inflate(R.layout.layout_remember_message_list_item,null);
            TextView rememberMessageText = (TextView) view1.findViewById(R.id.rememberMessageText);
            rememberMessageText.setText(rememberMessageModel.getRememberMessageStr());
        }



        return view1;
    }
}










//方案二



//    class GroupModel {
//        private String title;
//        private List<?> list;
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public List<?> getList() {
//            return list;
//        }
//
//        public void setList(List<?> list) {
//            this.list = list;
//        }
//
//        public int getListSize(){
//            return list.size()+1;
//        }
//
//
//        public Object getValue(int index){
//            if(index==0){
//                return title;
//            }
//            return list.get(index-1);
//        }
//
//
//
//    }
//
//
//    private class HomeAdapter extends BaseAdapter{
//        private List<GroupModel> groups=new ArrayList<GroupModel>();
//
//        public void addGroup(GroupModel gl){
//            groups.add(gl);
//        }
//
//        @Override
//        public int getCount() {
//            int count=0;
//            for(int i=0;i<groups.size();i++){
//                count=count+groups.get(i).getListSize();
//            }
//            return count;
//        }
//
//        @Override
//        public Object getItem(int i) {
//
//            int count=0;
//            Integer key=0;
//            for(int j=0;j<groups.size();j++){
//                key=j;
//                Integer nowCount=count+groups.get(j).getListSize();
//                if(i<nowCount){
//                    break;
//
//                }
//                count=nowCount;
//            }
//
//            return groups.get(key).getValue(i-count);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            Object item=this.getItem(i);
//            View view1 = view;
//            if(item instanceof String){
//                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_customer_trade_record_list_item_tag,null);
//                TextView text = (TextView)view1.findViewById(R.id.groupName);
//                text.setText((CharSequence) getItem(i));
//            }else if(item instanceof TradeRecordModel){
//                TradeRecordModel tradeRecordModel = (TradeRecordModel)item;
//                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_customer_trade_record_list_item,null);
//
//                TextView tradeInfo = (TextView)view1.findViewById(R.id.tradeInfo);
//                tradeInfo.setText(tradeRecordModel.getTradeRecordStr());
//
//                TextView perNetValueText = (TextView) view1.findViewById(R.id.perNetValue);
//                perNetValueText.setText("单位净值:" + tradeRecordModel.getUnitPriceView() + "元");
//
//                TextView amountMoneyText = (TextView) view1.findViewById(R.id.amountMoney);
//                amountMoneyText.setText("总金额:" + tradeRecordModel.getTradeAmount() + "元");
//
//                TextView tradeDateText = (TextView) view1.findViewById(R.id.tradeDate);
//                tradeDateText.setText(tradeRecordModel.getTradeDate());
//            }
//
////            if (groupTitle.contains(getItem(i))){
////                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_customer_trade_record_list_item_tag,null);
////                TextView text = (TextView)view1.findViewById(R.id.groupName);
////                text.setText((CharSequence) getItem(i));
////            }else {
////                TradeRecordModel tradeRecordModel = (TradeRecordModel)mainList.get(i);
////                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_customer_trade_record_list_item,null);
////
////                TextView tradeInfo = (TextView)view1.findViewById(R.id.tradeInfo);
////                tradeInfo.setText(tradeRecordModel.getTradeRecordStr());
////
////                TextView perNetValueText = (TextView) view1.findViewById(R.id.perNetValue);
////                perNetValueText.setText("单位净值:" + tradeRecordModel.getUnitPriceView() + "元");
////
////                TextView amountMoneyText = (TextView) view1.findViewById(R.id.amountMoney);
////                amountMoneyText.setText("总金额:" + tradeRecordModel.getTradeAmount() + "元");
////
////                TextView tradeDateText = (TextView) view1.findViewById(R.id.tradeDate);
////                tradeDateText.setText(tradeRecordModel.getTradeDate());
////
////            }
//
//            return view1;
//        }
//    }
