package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerInverstProductModel;

import java.util.List;

/**
 * Created by macavilang on 16/9/21.
 */
public class CustomerInverstProductListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context ctx;
    private List<CustomerInverstProductModel> customerInverstProducts;

    public CustomerInverstProductListAdapter(Context context, List<CustomerInverstProductModel> customerInverstProducts){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.customerInverstProducts = customerInverstProducts;
    }
    @Override
    public int getCount() {
        return customerInverstProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return customerInverstProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        CustomerInverstProductModel customerInverstProductModel = customerInverstProducts.get(i);
        view1 = mInflater.inflate(R.layout.layout_customer_inverst_product_list_item,null);

        TextView customerDetail_customerInverst_inverstAmount = (TextView)view1.findViewById(R.id.customerDetail_customerInverst_inverstAmount);
        customerDetail_customerInverst_inverstAmount.setText(customerInverstProductModel.getInvestAmount());

        TextView customerDetail_customerInverst_productName = (TextView) view1.findViewById(R.id.customerDetail_customerInverst_productName);
        customerDetail_customerInverst_productName.setText(customerInverstProductModel.getFundName());

        TextView customerDetail_customerInverst_inverstShare = (TextView) view1.findViewById(R.id.customerDetail_customerInverst_inverstShare);
        customerDetail_customerInverst_inverstShare.setText(customerInverstProductModel.getNetValue());

        TextView customerDetail_customerInverst_totalNetValue = (TextView) view1.findViewById(R.id.customerDetail_customerInverst_totalNetValue);
        customerDetail_customerInverst_totalNetValue.setText(customerInverstProductModel.getTotalNetValue());

        return view1;
    }
}
