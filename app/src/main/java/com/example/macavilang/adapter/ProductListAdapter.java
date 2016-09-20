package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;
import com.example.macavilang.model.TradeRecordModel;

import java.util.List;

/**
 * Created by macavilang on 16/9/19.
 */
public class ProductListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context ctx;
    private List<ProductModel> productList;

    public ProductListAdapter(Context context, List<ProductModel> products){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.productList = products;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        ProductModel productModel = productList.get(i);
        view1 = mInflater.inflate(R.layout.layout_product_list_item,null);

        TextView product_perNetValueText = (TextView)view1.findViewById(R.id.product_perNetValueText);
        product_perNetValueText.setText(productModel.getLatestNetValueView());

        TextView product_productName = (TextView) view1.findViewById(R.id.product_productName);
        product_productName.setText(productModel.getFundName());

        TextView product_investPeople = (TextView) view1.findViewById(R.id.product_investPeople);
        product_investPeople.setText(productModel.getFundCurrentOwnerNumber());

        TextView product_investShare = (TextView) view1.findViewById(R.id.product_investShare);
        product_investShare.setText(productModel.getFundCurrentShare());

        TextView product_investAmount = (TextView) view1.findViewById(R.id.product_investAmount);
        product_investAmount.setText(productModel.getFundTotalAmount());
        return view1;
    }
}
