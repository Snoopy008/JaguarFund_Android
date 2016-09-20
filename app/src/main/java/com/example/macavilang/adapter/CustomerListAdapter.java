package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;
import com.example.macavilang.model.ProductModel;
import com.example.macavilang.model.TradeRecordModel;

import java.util.List;

/**
 * Created by macavilang on 16/9/19.
 */
public class CustomerListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context ctx;
    private List<CustomerModel> customerList;

    public CustomerListAdapter(Context context, List<CustomerModel> customers){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.customerList = customers;
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int i) {
        return customerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        CustomerModel customerModel = customerList.get(i);
        view1 = mInflater.inflate(R.layout.layout_customer_list_item,null);

        TextView customer_customerName = (TextView)view1.findViewById(R.id.customer_customerName);
        customer_customerName.setText(customerModel.getName());

        TextView customer_investProductNumber = (TextView) view1.findViewById(R.id.customer_investProductNumber);
        customer_investProductNumber.setText(customerModel.getInvestProductCount());

        TextView customer_investShare = (TextView) view1.findViewById(R.id.customer_investShare);
        customer_investShare.setText(customerModel.getInvestShareTotal());

        TextView customer_investAmount = (TextView) view1.findViewById(R.id.customer_investAmount);
        customer_investAmount.setText(customerModel.getInvestShareAmountTotal());
        return view1;
    }
}