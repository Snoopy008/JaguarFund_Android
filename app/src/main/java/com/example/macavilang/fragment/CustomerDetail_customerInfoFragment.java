package com.example.macavilang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerDetail_customerInfoFragment extends Fragment {


    public CustomerDetail_customerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_customer_detail_customer_info,container,false);
    }

}
