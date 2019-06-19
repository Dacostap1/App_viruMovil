package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.viruapp.Model.Student;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;
import com.example.viruapp.ui.adapter.StudentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentActivity extends AppCompatActivity implements Callback<ArrayList<Student>> {

    private StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        RecyclerView recyclerView = findViewById(R.id.reclicler_view);
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");
        int promo_id = getIntent().getIntExtra("promo_id", 0);

        mAdapter = new StudentAdapter(this);
        recyclerView.setAdapter(mAdapter);


        Call<ArrayList<Student>> call = AppViruApiAdapter.getApiService().getStudentsbyPromotion(token, promo_id);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ArrayList<Student>> call, Response<ArrayList<Student>> response) {
        if(response.isSuccessful()){
            ArrayList<Student> students = response.body();
            Log.d("RT", "tamaño de array => " + students.size());
            mAdapter.setDataSet(students);

        }else{
            Toast.makeText(this, "token es fake", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Student>> call, Throwable t) {
        Toast.makeText(this, "El token es inválido", Toast.LENGTH_SHORT).show();
    }
}
