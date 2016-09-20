package com.example.macavilang.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.macavilang.fragment.CustomerFragment;
import com.example.macavilang.fragment.HomeFragment;
import com.example.macavilang.fragment.MyFragment;
import com.example.macavilang.fragment.ProductFragment;
import com.example.macavilang.jaguarfund_android.R;


public class MainPageActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout ll_home;
    private LinearLayout ll_product;
    private LinearLayout ll_customer;
    private LinearLayout ll_my;


    private ImageView iv_home;
    private ImageView iv_product;
    private ImageView iv_customer;
    private ImageView iv_my;

    private TextView tv_home;
    private TextView tv_product;
    private TextView tv_customer;
    private TextView tv_my;

    private Fragment homeFragment;
    private Fragment productFragment;
    private Fragment customerFragment;
    private Fragment myFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        initView();

        initEvent();

        initFragment(0);
    }



    private void initFragment(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                if (homeFragment == null){
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.vp_content,homeFragment);
                }else {
                    transaction.show(homeFragment);
                }
                break;

            case 1:
                if (productFragment == null){
                    productFragment = new ProductFragment();
                    transaction.add(R.id.vp_content,productFragment);
                }else {
                    transaction.show(productFragment);
                }
                break;

            case 2:
                if (customerFragment == null){
                    customerFragment = new CustomerFragment();
                    transaction.add(R.id.vp_content,customerFragment);
                }else {
                    transaction.show(customerFragment);
                }
                break;

            case 3:
                if (myFragment == null){
                    myFragment = new MyFragment();
                    transaction.add(R.id.vp_content,myFragment);
                }else {
                    transaction.show(myFragment);
                }
                break;

            default:
                break;
        }

        transaction.commit();
    }


    private void hideFragment (FragmentTransaction transaction){
        if (homeFragment != null){
            transaction.hide(homeFragment);
        }
        if (productFragment != null){
            transaction.hide(productFragment);
        }
        if (customerFragment != null){
            transaction.hide(customerFragment);
        }
        if (myFragment != null){
            transaction.hide(myFragment);
        }

    }

    private void initEvent(){
        ll_home.setOnClickListener(this);
        ll_product.setOnClickListener(this);
        ll_customer.setOnClickListener(this);
        ll_my.setOnClickListener(this);
    }

    private void initView(){
        this.ll_home = (LinearLayout) findViewById(R.id.ll_home);
        this.ll_product = (LinearLayout) findViewById(R.id.ll_product);
        this.ll_customer = (LinearLayout) findViewById(R.id.ll_customer);
        this.ll_my = (LinearLayout) findViewById(R.id.ll_my);

        this.iv_home = (ImageView) findViewById(R.id.iv_home);
        this.iv_product = (ImageView) findViewById(R.id.iv_product);
        this.iv_customer = (ImageView) findViewById(R.id.iv_customer);
        this.iv_my = (ImageView) findViewById(R.id.iv_my);

        this.tv_home = (TextView) findViewById(R.id.tv_home);
        this.tv_product = (TextView) findViewById(R.id.tv_product);
        this.tv_customer = (TextView) findViewById(R.id.tv_customer);
        this.tv_my = (TextView) findViewById(R.id.tv_my);
    }

    @Override
    public void onClick(View view) {
        restartButton();

        switch (view.getId()){
            case R.id.ll_home:
                iv_home.setImageResource(R.drawable.tab_1_on);
                tv_home.setTextColor(0xffef3348);
                initFragment(0);
                break;


            case R.id.ll_product:
                iv_product.setImageResource(R.drawable.tab_2_on);
                tv_product.setTextColor(0xffef3348);
                initFragment(1);
                break;


            case R.id.ll_customer:
                iv_customer.setImageResource(R.drawable.tab_3_on);
                tv_customer.setTextColor(0xffef3348);
                initFragment(2);
                break;


            case R.id.ll_my:
                iv_my.setImageResource(R.drawable.tab_4_on);
                tv_my.setTextColor(0xffef3348);
                initFragment(3);
                break;

            default:
                break;
        }
    }





    private void restartButton(){
        iv_home.setImageResource(R.drawable.tab_1_off);
        iv_product.setImageResource(R.drawable.tab_2_off);
        iv_customer.setImageResource(R.drawable.tab_3_off);
        iv_my.setImageResource(R.drawable.tab_4_off);

        tv_home.setTextColor(0xffb9b8b8);
        tv_product.setTextColor(0xffb9b8b8);
        tv_customer.setTextColor(0xffb9b8b8);
        tv_my.setTextColor(0xffb9b8b8);
    }
}
