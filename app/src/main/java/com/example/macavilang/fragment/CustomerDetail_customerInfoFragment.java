package com.example.macavilang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.CustomerModel;
import com.example.macavilang.model.ProductModel;

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
        View rootView  = inflater.inflate(R.layout.activity_customer_detail_customer_info,container,false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            CustomerModel customerModel = (CustomerModel)bundle.getSerializable("customerModel");

            TextView customerDetail_customerInfo_customerName = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_customerName);
            customerDetail_customerInfo_customerName.setText(customerModel.getName());

            TextView customerDetail_customerInfo_customerType = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_customerType);
            customerDetail_customerInfo_customerType.setText(customerModel.getCustomerType());

//            TextView customerDetail_customerInfo_taCode = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_taCode);
//            customerDetail_customerInfo_taCode.setText(customerModel.get());

            TextView customerDetail_customerInfo_telephone = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_telephone);
            customerDetail_customerInfo_telephone.setText(customerModel.getMobile());

//            TextView customerDetail_customerInfo_identityType = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_identityType);
//            customerDetail_customerInfo_identityType.setText(customerModel.ge());

//            TextView customerDetail_customerInfo_customerName = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_customerName);
//            customerDetail_customerInfo_customerName.setText(customerModel.getName());

            TextView customerDetail_customerInfo_address = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_address);
            customerDetail_customerInfo_address.setText(customerModel.getAddress());

            TextView customerDetail_customerInfo_inverstShare = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_inverstShare);
            customerDetail_customerInfo_inverstShare.setText(customerModel.getInvestShareCurrent());

            TextView customerDetail_customerInfo_inverstAmount = (TextView)rootView.findViewById(R.id.customerDetail_customerInfo_inverstAmount);
            customerDetail_customerInfo_inverstAmount.setText(customerModel.getInvestShareAmountCurrent());


        }

        return rootView;
    }

}
