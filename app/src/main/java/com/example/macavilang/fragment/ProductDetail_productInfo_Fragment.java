package com.example.macavilang.fragment;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.activity.TradeRecordDetailActivity;
import com.example.macavilang.adapter.AttachmentFileListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.AttachmentFileModel;
import com.example.macavilang.model.ProductModel;
import com.example.macavilang.utils.ListViewForScrollView;
import com.example.macavilang.utils.OpenFiles;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetail_productInfo_Fragment extends Fragment {


    private ArrayList<Object> fileMainList= new ArrayList<>();
    private ListViewForScrollView fileListView;
    private AttachmentFileListAdapter fileListAdapter;
    private View rootView;
    private ProductModel productModel = new ProductModel();
    public ProductDetail_productInfo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_product_detail_product_info,container,false);
        Bundle bundle = getArguments();
        if(bundle!=null){
            productModel = (ProductModel)bundle.getSerializable("productModel");

            TextView productDetail_productInfo_fundName = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundName);
            productDetail_productInfo_fundName.setText(productModel.getProductShortName());

            TextView productDetail_productInfo_fundNumber = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundNumber);
            productDetail_productInfo_fundNumber.setText(productModel.getFundCode());

            TextView productDetail_productInfo_productState = (TextView)rootView.findViewById(R.id.productDetail_productInfo_productState);
            productDetail_productInfo_productState.setText(productModel.getStatus());

            TextView productDetail_productInfo_fundManager = (TextView)rootView.findViewById(R.id.productDetail_productInfo_fundManager);
            productDetail_productInfo_fundManager.setText(productModel.getManager());

            TextView productDetail_productInfo_foundTime = (TextView)rootView.findViewById(R.id.productDetail_productInfo_foundTime);
            productDetail_productInfo_foundTime.setText(productModel.getReleaseDate());

            TextView productDetail_productInfo_perNetValue = (TextView)rootView.findViewById(R.id.productDetail_productInfo_perNetValue);
            productDetail_productInfo_perNetValue.setText(productModel.getLatestNetValue());

            TextView productDetail_productInfo_totalNetValue = (TextView)rootView.findViewById(R.id.productDetail_productInfo_totalNetValue);
            productDetail_productInfo_totalNetValue.setText(productModel.getLatestAccumulativeNetValue());

            TextView productDetail_productInfo_inverstPeople = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstPeople);
            productDetail_productInfo_inverstPeople.setText(productModel.getFundCurrentOwnerNumber());

            TextView productDetail_productInfo_inverstShare = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstShare);
            productDetail_productInfo_inverstShare.setText(productModel.getFundCurrentShare());

            TextView productDetail_productInfo_inverstMoney = (TextView)rootView.findViewById(R.id.productDetail_productInfo_inverstMoney);
            productDetail_productInfo_inverstMoney.setText(productModel.getFundAmountCurrent());

        }


        getAttachmentListData();


        fileListAdapter = new AttachmentFileListAdapter(getContext());
        fileListView = (ListViewForScrollView)rootView.findViewById(R.id.productInfoFileList);
        fileListView.setAdapter(fileListAdapter);


        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttachmentFileModel fileModel = (AttachmentFileModel)fileMainList.get(position);
                OpenFiles.openFileAndReview(fileModel,getContext());
            }
        });

        return rootView;
    }


    public void getAttachmentListData(){
        RequestQueue tradeRecordDetailFilesQueue = Volley.newRequestQueue(getContext());
        String productId = productModel.getId();
        String tradeRecordDetailFileURL = getResources().getString(R.string.baseURL) + "api/fund/products/fileList/" + productId;
        StringRequest tradeRecordDetailFileRequest = new StringRequest(Request.Method.GET, tradeRecordDetailFileURL,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("productDetailFile--",response);
                        Type tradeRecordDetailFileType = new TypeToken<List<AttachmentFileModel>>(){}.getType();
                        List<AttachmentFileModel> fileModels = (List<AttachmentFileModel>) gson.fromJson(response,tradeRecordDetailFileType);

                        fileMainList.add("附件");
                        fileMainList.addAll(fileModels);
                        fileListAdapter.updatefileList(fileMainList);
                        fileListAdapter.notifyDataSetChanged();
                        ScrollView sv = (ScrollView)rootView.findViewById(R.id.productInfoFileScrollView);
                        sv.smoothScrollTo(0, 0);
                        for (AttachmentFileModel file:fileModels) {
                            downloadFileData(file);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("netValueError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                SharedPreferences preferences = getContext().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        tradeRecordDetailFilesQueue.add(tradeRecordDetailFileRequest);
    }

    public void downloadFileData(final AttachmentFileModel fileModel){
        if (OpenFiles.fileIsExists(fileModel)){

        }else {
            RequestQueue downloadFileQueue = Volley.newRequestQueue(getContext());
            SharedPreferences preferences = getContext().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
            String urlToken = preferences.getString("token",null);
            String tradeRecordDetailFileURL = getResources().getString(R.string.baseURL) + fileModel.getAccessUrl() + "?token=" + urlToken;
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(tradeRecordDetailFileURL));
            //指定下载路径和下载文件名
            request.setDestinationInExternalPublicDir("/download/Jaguar_Android/", fileModel.getFileName());
            //获取下载管理器
            DownloadManager downloadManager= (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            //将下载任务加入下载队列，否则不会进行下载
            downloadManager.enqueue(request);
        }


    }

}
