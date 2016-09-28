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

import com.example.macavilang.fragment.CustomerDetail_customerInfoFragment;
import com.example.macavilang.fragment.CustomerDetail_inverstProductsFragment;
import com.example.macavilang.fragment.CustomerDetail_tradeRecordsFragment;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;


public class CustomerDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView customerDetail_customerInfo;
    private TextView customerDetail_investProducts;
    private TextView customerDetail_tradeRecords;

    private Fragment CustomerDetail_customerInfoFragment;
    private Fragment CustomerDetail_inverstProductsFragment;
    private Fragment CustomerDetail_tradeRecordsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        TextView titleText = (TextView)findViewById(R.id.title_text);
        titleText.setText("客户详情");
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
        customerDetail_customerInfo = (TextView) findViewById(R.id.customerDetail_customerInfo);
        customerDetail_investProducts = (TextView) findViewById(R.id.customerDetail_investProducts);
        customerDetail_tradeRecords = (TextView) findViewById(R.id.customerDetail_tradeRecords);
    }


    private void initEvent(){
        customerDetail_customerInfo.setOnClickListener(this);
        customerDetail_investProducts.setOnClickListener(this);
        customerDetail_tradeRecords.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        restartButton();

        switch (view.getId()){
            case R.id.customerDetail_customerInfo:
                customerDetail_customerInfo.setTextColor(0xfff46464);
                initFragment(0);
                break;


            case R.id.customerDetail_investProducts:
                customerDetail_investProducts.setTextColor(0xfff46464);
                initFragment(1);
                break;


            case R.id.customerDetail_tradeRecords:
                customerDetail_tradeRecords.setTextColor(0xfff46464);
                initFragment(2);
                break;


            default:
                break;
        }

    }

    private void initFragment(int index){
        Intent intent = this.getIntent();
        CustomerModel customerModel = (CustomerModel) intent.getSerializableExtra("customerModel");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                if (CustomerDetail_customerInfoFragment == null){
                    CustomerDetail_customerInfoFragment = new CustomerDetail_customerInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("customerModel",customerModel);
                    CustomerDetail_customerInfoFragment.setArguments(bundle);
                    transaction.add(R.id.customerDetail_content,CustomerDetail_customerInfoFragment);
                }else {
                    transaction.show(CustomerDetail_customerInfoFragment);
                }
                break;

            case 1:
                if (CustomerDetail_inverstProductsFragment == null){
                    CustomerDetail_inverstProductsFragment = new CustomerDetail_inverstProductsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("customerId",customerModel.getId());
                    CustomerDetail_inverstProductsFragment.setArguments(bundle);
                    transaction.add(R.id.customerDetail_content,CustomerDetail_inverstProductsFragment);
                }else {
                    transaction.show(CustomerDetail_inverstProductsFragment);
                }
                break;

            case 2:
                if (CustomerDetail_tradeRecordsFragment == null){
                    CustomerDetail_tradeRecordsFragment = new CustomerDetail_tradeRecordsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("customerId",customerModel.getId());
                    CustomerDetail_tradeRecordsFragment.setArguments(bundle);
                    transaction.add(R.id.customerDetail_content,CustomerDetail_tradeRecordsFragment);
                }else {
                    transaction.show(CustomerDetail_tradeRecordsFragment);
                }
                break;


            default:
                break;
        }

        transaction.commit();
    }


    private void hideFragment (FragmentTransaction transaction){
        if (CustomerDetail_customerInfoFragment != null){
            transaction.hide(CustomerDetail_customerInfoFragment);
        }
        if (CustomerDetail_inverstProductsFragment != null){
            transaction.hide(CustomerDetail_inverstProductsFragment);
        }
        if (CustomerDetail_tradeRecordsFragment != null){
            transaction.hide(CustomerDetail_tradeRecordsFragment);
        }

    }

    private void restartButton(){

        customerDetail_customerInfo.setTextColor(0xff000000);
        customerDetail_investProducts.setTextColor(0xff000000);
        customerDetail_tradeRecords.setTextColor(0xff000000);
    }
}
