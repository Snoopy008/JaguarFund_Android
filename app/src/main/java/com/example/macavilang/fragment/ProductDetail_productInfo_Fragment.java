package com.example.macavilang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetail_productInfo_Fragment extends Fragment {


    public ProductDetail_productInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_product_detail_product_info,container,false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            ProductModel productModel = (ProductModel)bundle.getSerializable("productModel");

            TextView productDetail_productInfo_fundName = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundName);
            productDetail_productInfo_fundName.setText(productModel.getProductName());

            TextView productDetail_productInfo_fundNumber = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundNumber);
            productDetail_productInfo_fundNumber.setText(productModel.getFundCode());

            TextView productDetail_productInfo_productState = (TextView)rootView.findViewById(R.id.productDetail_productInfo_productState);
            productDetail_productInfo_productState.setText(productModel.getStatus());

            TextView productDetail_productInfo_fundManager = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundManager);
            productDetail_productInfo_fundManager.setText(productModel.getManager());

            TextView productDetail_productInfo_foundTime = (TextView)rootView.findViewById(R.id.productDetail_productInfo_foundTime);
            productDetail_productInfo_foundTime.setText(productModel.getReleaseDate());

            TextView productDetail_productInfo_perNetValue = (TextView)rootView.findViewById(R.id.productDetail_productInfo_perNetValue);
            productDetail_productInfo_perNetValue.setText(productModel.getLatestNetValueView());

            TextView productDetail_productInfo_totalNetValue = (TextView)rootView.findViewById(R.id.productDetail_productInfo_totalNetValue);
            productDetail_productInfo_totalNetValue.setText(productModel.getLatestAccumulativeNetValueView());

            TextView productDetail_productInfo_inverstPeople = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstPeople);
            productDetail_productInfo_inverstPeople.setText(productModel.getFundOwnerNumber());

            TextView productDetail_productInfo_inverstShare = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstShare);
            productDetail_productInfo_inverstShare.setText(productModel.getFundShare());

            TextView productDetail_productInfo_inverstMoney = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstMoney);
            productDetail_productInfo_inverstMoney.setText(productModel.getFundTotalAmount());

        }

        return rootView;
    }

}
