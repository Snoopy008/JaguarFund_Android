package com.example.macavilang.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.macavilang.fragment.ProductDetail_inverstCustomersFragment;
import com.example.macavilang.fragment.ProductDetail_netValueHistoryFragment;
import com.example.macavilang.fragment.ProductDetail_productInfo_Fragment;
import com.example.macavilang.fragment.ProductDetail_tradeRecordsFragment;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView productDetail_productInfo;
    private TextView productDetail_netValueHistory;
    private TextView productDetail_inverstCustomers;
    private TextView productDetail_tradeRecords;

    private Fragment ProductDetail_productInfo_Fragment;
    private Fragment ProductDetail_netValueHistoryFragment;
    private Fragment ProductDetail_inverstCustomersFragment;
    private Fragment ProductDetail_tradeRecordsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        TextView titleText = (TextView)findViewById(R.id.title_text);
        titleText.setText("产品详情");
        ImageButton backBtn = (ImageButton)findViewById(R.id.backButton);

        backBtn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                    onBackPressed();
            }
        });


        initView();
        initEvent();
        initFragment(0);
    }

    public void initView(){
        productDetail_productInfo = (TextView) findViewById(R.id.prductDetail_productInfo);
        productDetail_netValueHistory = (TextView) findViewById(R.id.prductDetail_netValueHistory);
        productDetail_inverstCustomers = (TextView) findViewById(R.id.prductDetail_investCustomers);
        productDetail_tradeRecords = (TextView) findViewById(R.id.prductDetail_tradeRecords);
    }


    private void initEvent(){
        productDetail_productInfo.setOnClickListener(this);
        productDetail_netValueHistory.setOnClickListener(this);
        productDetail_inverstCustomers.setOnClickListener(this);
        productDetail_tradeRecords.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        restartButton();

        switch (view.getId()){
            case R.id.prductDetail_productInfo:
                productDetail_productInfo.setTextColor(0xfff46464);
                initFragment(0);
                break;


            case R.id.prductDetail_netValueHistory:
                productDetail_netValueHistory.setTextColor(0xfff46464);
                initFragment(1);
                break;


            case R.id.prductDetail_investCustomers:
                productDetail_inverstCustomers.setTextColor(0xfff46464);
                initFragment(2);
                break;


            case R.id.prductDetail_tradeRecords:
                productDetail_tradeRecords.setTextColor(0xfff46464);
                initFragment(3);
                break;

            default:
                break;
        }

    }

    private void initFragment(int index){
        Intent intent = this.getIntent();
        ProductModel productModel = (ProductModel)intent.getSerializableExtra("productModel");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                if (ProductDetail_productInfo_Fragment == null){
                    ProductDetail_productInfo_Fragment = new ProductDetail_productInfo_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("productModel",productModel);
                    ProductDetail_productInfo_Fragment.setArguments(bundle);
                    transaction.add(R.id.prductDetail_content,ProductDetail_productInfo_Fragment);
                }else {
                    transaction.show(ProductDetail_productInfo_Fragment);
                }
                break;

            case 1:
                if (ProductDetail_netValueHistoryFragment == null){
                    ProductDetail_netValueHistoryFragment = new ProductDetail_netValueHistoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("productId",productModel.getId());
                    ProductDetail_netValueHistoryFragment.setArguments(bundle);
                    transaction.add(R.id.prductDetail_content,ProductDetail_netValueHistoryFragment);
                }else {
                    transaction.show(ProductDetail_netValueHistoryFragment);
                }
                break;

            case 2:
                if (ProductDetail_inverstCustomersFragment == null){
                    ProductDetail_inverstCustomersFragment = new ProductDetail_inverstCustomersFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("productId",productModel.getId());
                    ProductDetail_inverstCustomersFragment.setArguments(bundle);
                    transaction.add(R.id.prductDetail_content,ProductDetail_inverstCustomersFragment);
                }else {
                    transaction.show(ProductDetail_inverstCustomersFragment);
                }
                break;

            case 3:
                if (ProductDetail_tradeRecordsFragment == null){
                    ProductDetail_tradeRecordsFragment = new ProductDetail_tradeRecordsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("productId",productModel.getId());
                    ProductDetail_tradeRecordsFragment.setArguments(bundle);
                    transaction.add(R.id.prductDetail_content,ProductDetail_tradeRecordsFragment);
                }else {
                    transaction.show(ProductDetail_tradeRecordsFragment);
                }
                break;

            default:
                break;
        }

        transaction.commit();
    }


    private void hideFragment (FragmentTransaction transaction){
        if (ProductDetail_productInfo_Fragment != null){
            transaction.hide(ProductDetail_productInfo_Fragment);
        }
        if (ProductDetail_netValueHistoryFragment != null){
            transaction.hide(ProductDetail_netValueHistoryFragment);
        }
        if (ProductDetail_inverstCustomersFragment != null){
            transaction.hide(ProductDetail_inverstCustomersFragment);
        }
        if (ProductDetail_tradeRecordsFragment != null){
            transaction.hide(ProductDetail_tradeRecordsFragment);
        }

    }

    private void restartButton(){

        productDetail_productInfo.setTextColor(0xff000000);
        productDetail_netValueHistory.setTextColor(0xff000000);
        productDetail_inverstCustomers.setTextColor(0xff000000);
        productDetail_tradeRecords.setTextColor(0xff000000);
    }

}
