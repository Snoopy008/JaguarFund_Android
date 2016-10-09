package com.example.macavilang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.AttachmentFileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macavilang on 16/10/8.
 */

public class AttachmentFileListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context ctx;
    private List<Object> fileModels;


    public AttachmentFileListAdapter(Context context, List<Object> files){
        super();
        this.mInflater = LayoutInflater.from(context);
        this.ctx = context;
        this.fileModels = files;
    }

    @Override
    public int getCount() {
        return fileModels.size();
    }

    @Override
    public Object getItem(int position) {
        return fileModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = convertView;
        Object item = fileModels.get(position);
        if (item instanceof String){
            view1 = mInflater.inflate(R.layout.layout_customer_trade_record_list_item_tag,null);
            TextView text = (TextView)view1.findViewById(R.id.groupName);
            text.setText("附件");
            Button moreBtn = (Button)view1.findViewById(R.id.tradeMoreBtn);
            moreBtn.setVisibility(View.GONE);
        }else if(item instanceof AttachmentFileModel){
            Log.e("--------------","________________");
            AttachmentFileModel fileModel = (AttachmentFileModel)item;
            view1 = mInflater.inflate(R.layout.layout_my_list_item,null);
            TextView text = (TextView)view1.findViewById(R.id.textLab);
            text.setText(fileModel.getFileName());
            text.setTextColor(0xff2f4edc);
        }
        return view1;
    }
}
