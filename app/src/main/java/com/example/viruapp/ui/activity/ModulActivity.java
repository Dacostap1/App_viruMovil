package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;
import com.example.viruapp.ui.adapter.ModulAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModulActivity extends AppCompatActivity implements Callback<ArrayList<Modul>> {

    private ModulAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modul);

        RecyclerView recyclerView = findViewById(R.id.reclicler_view);
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", "");
        int student_id = getIntent().getIntExtra("student_id", 0);

        mAdapter = new ModulAdapter(this);
        recyclerView.setAdapter(mAdapter);

        Call<ArrayList<Modul>> call = AppViruApiAdapter.getApiService().getModulbyStudent(token, student_id);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ArrayList<Modul>> call, Response<ArrayList<Modul>> response) {
        if (response.isSuccessful()){
            ArrayList<Modul> modul = response.body();
            mAdapter.setDataSet(modul);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Modul>> call, Throwable t) {

    }
}
