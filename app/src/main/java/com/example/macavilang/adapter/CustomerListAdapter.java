package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macavilang on 16/9/19.
 */
public class CustomerListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context ctx;
    private List<CustomerModel> customerList;
    private Boolean isPartViewRemove = false;

    public CustomerListAdapter(Context context, Boolean isRemovePartView){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        customerList = new ArrayList<CustomerModel>();
        isPartViewRemove = isRemovePartView;
    }


    public void updateCustomerList(List<CustomerModel> customers)
    {
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
        TextView customer_investProductNumber = (TextView) view1.findViewById(R.id.customer_investProductNumber);
        TextView customer_investProductNumberTitle = (TextView) view1.findViewById(R.id.customer_investProductNumberTitle);
        if (isPartViewRemove){
            customer_investProductNumber.setVisibility(View.GONE);
            customer_investProductNumberTitle.setVisibility(View.GONE);
        }else {

            customer_investProductNumber.setText(customerModel.getInvestProductCount());
        }

        TextView customer_customerName = (TextView)view1.findViewById(R.id.customer_customerName);
        customer_customerName.setText(customerModel.getClientName());

        TextView customer_investShare = (TextView) view1.findViewById(R.id.customer_investShare);
        customer_investShare.setText(customerModel.getInvestShareCurrent());

        TextView customer_investAmount = (TextView) view1.findViewById(R.id.customer_investAmount);
        customer_investAmount.setText(customerModel.getInvestShareAmountCurrent());
        return view1;
    }
}
