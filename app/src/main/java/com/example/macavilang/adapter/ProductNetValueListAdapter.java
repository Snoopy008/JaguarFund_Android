package com.example.macavilang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;
import com.example.macavilang.model.ProductNetValueHistoryModel;

import java.util.List;

/**
 * Created by macavilang on 16/9/22.
 */
public class ProductNetValueListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context ctx;
    private List<ProductNetValueHistoryModel> productNetValueHistorys;

    public ProductNetValueListAdapter(Context context, List<ProductNetValueHistoryModel> netValueHistory){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.productNetValueHistorys = netValueHistory;
    }
    @Override
    public int getCount() {
        return productNetValueHistorys.size();
    }

    @Override
    public Object getItem(int i) {
        return productNetValueHistorys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        ProductNetValueHistoryModel productNetValueHistoryModel = productNetValueHistorys.get(i);
        view1 = mInflater.inflate(R.layout.layout_product_netvalue_list_item,null);

        TextView productNetValue_netValueDate = (TextView)view1.findViewById(R.id.productNetValue_netValueDate);
        productNetValue_netValueDate.setText(productNetValueHistoryModel.getMarketDate());

        TextView productNetValue_perNetValue = (TextView) view1.findViewById(R.id.productNetValue_perNetValue);
        productNetValue_perNetValue.setText(productNetValueHistoryModel.getMarketPriceView());

        TextView productNetValue_totalNetValue = (TextView) view1.findViewById(R.id.productNetValue_totalNetValue);
        productNetValue_totalNetValue.setText(productNetValueHistoryModel.getAccumulativeMarketPriceView());

        return view1;
    }
}
