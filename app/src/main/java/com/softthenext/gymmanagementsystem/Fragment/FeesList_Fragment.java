package com.softthenext.gymmanagementsystem.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softthenext.gymmanagementsystem.Adapter.FeesAdapter;
import com.softthenext.gymmanagementsystem.Model.FeesModel;
import com.softthenext.gymmanagementsystem.R;
import com.softthenext.gymmanagementsystem.Retrofit.ApiInterface;
import com.softthenext.gymmanagementsystem.Url.AppUrl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AABHALI on 04-01-2018.
 */

public class FeesList_Fragment extends Fragment {
    List<FeesModel> feesModels;
    RecyclerView.LayoutManager layoutManager;
    FeesAdapter feesAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feeslist,container,false);

        recyclerView = v.findViewById(R.id.feeslist_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<List<FeesModel>> call = apiInterface.getFeesDetails();

        call.enqueue(new Callback<List<FeesModel>>() {
            @Override
            public void onResponse(Call<List<FeesModel>> call, Response<List<FeesModel>> response) {

                pd.dismiss();
                feesModels=response.body();
                feesAdapter = new FeesAdapter(feesModels);
                recyclerView.setAdapter(feesAdapter);
                Log.d("success","Successfully Fetched Data");

            }

            @Override
            public void onFailure(Call<List<FeesModel>> call, Throwable t) {
                pd.dismiss();
                Log.d("Error","Error in Fetching Data");

            }
        });


        return v;
    }
}
