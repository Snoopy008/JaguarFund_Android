package com.example.macavilang.fragment;


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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macavilang.activity.ProductDetailActivity;
import com.example.macavilang.adapter.ProductListAdapter;
import com.example.macavilang.jaguarfund_android.R;
import com.example.macavilang.model.ProductModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {


    private View rootView;
    private int currentPage =1;
    private int totalPage;
    private SearchView productSearchView;
    private PullToRefreshListView productListView;
    private List<ProductModel> products;
    private ProductListAdapter productListAdapter;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_product,container,false);
        productSearchView = (SearchView)rootView.findViewById(R.id.productSearchView);
        productListView = (PullToRefreshListView)rootView.findViewById(R.id.productListView);
        productListView.setMode(PullToRefreshBase.Mode.BOTH);
        productListAdapter = new ProductListAdapter(getContext());
        productListView.setAdapter(productListAdapter);
        getProductListData("",currentPage,false);



        //ListView点击行
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductModel productModel=(ProductModel)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(),ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("productModel",productModel);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        //SearchView点击搜索
        productSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            // 当点击搜索按钮时触发该方法
            public boolean onQueryTextSubmit(String s) {
                currentPage = 1;
                getProductListData(s,currentPage,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        productListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (productListView.isShownHeader()){
                    currentPage = 1;
                    if (productSearchView.toString().equals(null))
                    {
                        getProductListData("",currentPage,false);
                    }else {
                        getProductListData(productSearchView.getQuery().toString(),currentPage,false);
                    }
                }

                //上拉加载
                if (productListView.isShownFooter()) {
                    if (currentPage < totalPage) {
                        ++currentPage;
                        if (productSearchView.toString().equals(null))
                        {
                            getProductListData("",currentPage,true);
                        }else {
                            getProductListData(productSearchView.getQuery().toString(),currentPage,true);
                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "没有更多数据",
                                Toast.LENGTH_SHORT).show();
                        productListView.onRefreshComplete();
                    }
                }
            }
        });
        return rootView;

    }


    public void getProductListData(String searchWords, int page, final Boolean isLoad){
        RequestQueue productListQueue = Volley.newRequestQueue(getContext());
        String productUrl = getResources().getString(R.string.baseURL) + "api/fund/products";
        Uri.Builder productBuildUrl = Uri.parse(productUrl).buildUpon();
        productBuildUrl.appendQueryParameter("keyWords",searchWords);
        productBuildUrl.appendQueryParameter("page", String.valueOf(page));
        productBuildUrl.appendQueryParameter("pageSize","10");
        productBuildUrl.appendQueryParameter("sort","-fundAmountCurrent");
        String productTotalUrl = productBuildUrl.build().toString();
        StringRequest productListRequest = new StringRequest(Request.Method.GET, productTotalUrl,
                new Response.Listener<String>() {
                    Gson gson = new Gson();
                    @Override
                    public void onResponse(String response) {
                        Log.e("productList",response);
                        JsonParser jsonParser = new JsonParser();
                        JsonElement jsonElement = jsonParser.parse(response);
                        JsonElement productListJson = jsonElement.getAsJsonObject().get("list");
                        JsonElement totalPageJson = jsonElement.getAsJsonObject().get("totalPage");
                        totalPage = totalPageJson.getAsInt();
                        Type productListType = new TypeToken<List<ProductModel>>(){}.getType();
                        if (isLoad){
                            List<ProductModel> moreProducts = (List<ProductModel>) gson.fromJson(productListJson,productListType);
                            products.addAll(moreProducts);
                        }else {
                            products = (List<ProductModel>) gson.fromJson(productListJson,productListType);
                        }

                        productListAdapter.updateProductList(products);
                        productListAdapter.notifyDataSetChanged();
                        productListView.onRefreshComplete();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("productListError",error.getMessage(),error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<String, String>();
                SharedPreferences preferences = getActivity().getSharedPreferences(getResources().getString(R.string.loginSharedPreferences), Context.MODE_PRIVATE);
                String urlToken = preferences.getString("token",null);
                header.put("X-Auth-Token",urlToken);
                return header;
            }
        };
        productListQueue.add(productListRequest);
    }

}
