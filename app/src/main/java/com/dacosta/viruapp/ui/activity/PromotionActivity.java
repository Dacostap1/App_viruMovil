package com.dacosta.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dacosta.viruapp.Model.Promotion;
import com.dacosta.viruapp.R;
import com.dacosta.viruapp.io.AppViruApiAdapter;
import com.dacosta.viruapp.ui.adapter.PromotionAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionActivity extends AppCompatActivity implements Callback<ArrayList<Promotion>> {

    private PromotionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        RecyclerView recyclerView = findViewById(R.id.reclicler_view);
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PromotionAdapter(this);
        recyclerView.setAdapter(mAdapter);


        Call<ArrayList<Promotion>> call = AppViruApiAdapter.getApiService().getPromotions(token);
        call.enqueue(this);
    }

        @Override
        public void onResponse(Call<ArrayList<Promotion>> call, Response<ArrayList<Promotion>> response) {
            if(response.isSuccessful()){
                ArrayList<Promotion> promotions = response.body();
                mAdapter.setDataSet(promotions);

            }
            else{
                Toast.makeText(this, "token is expiried", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Promotion>> call, Throwable t) {
                //Si no hay conexion a internet
            Toast.makeText(this, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
        }
    }

