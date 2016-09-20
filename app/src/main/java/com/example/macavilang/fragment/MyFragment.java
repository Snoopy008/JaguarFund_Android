package com.example.macavilang.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    private List<String> list = null;
    private List<String> groupKey = new ArrayList<String>();
    private List<String> aList = new ArrayList<String>();
    private List<String> bList = new ArrayList<String>();
    private ListView listView;



    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my,container,false);
        SharedPreferences preferences = getActivity().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
        listView = (ListView) rootView.findViewById(R.id.myListView);
        TextView accountTV = (TextView) rootView.findViewById(R.id.accountName);
        accountTV.setText(preferences.getString("displayName",null));
        TextView userNameTV = (TextView) rootView.findViewById(R.id.userName);
        userNameTV.setText(preferences.getString("username",null));
        initData();
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(list.get(i));
            }
        });
        return rootView;

    }

    public void initData(){
        list = new ArrayList<String>();
        groupKey.add("A组");
        groupKey.add("B组");

        aList.add("修改密码");
        aList.add("清除缓存");

        list.add("A组");
        list.addAll(aList);

        bList.add("公司简介");
        bList.add("关于与帮助");

        list.add("B组");
        list.addAll(bList);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean isEnabled(int position) {
            if (groupKey.contains(getItem(position))){
                return false;
            }
            return super.isEnabled(position);
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = view;
            if (groupKey.contains(getItem(i))){
                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_my_list_item_tag,null);
                TextView text = (TextView)view1.findViewById(R.id.textLab);
                text.setText("   ");
            }else {
                view1 = LayoutInflater.from(getContext()).inflate(R.layout.layout_my_list_item,null);
                TextView text = (TextView)view1.findViewById(R.id.textLab);
                text.setText((CharSequence) getItem(i));
            }

            return view1;
        }



    }

}
