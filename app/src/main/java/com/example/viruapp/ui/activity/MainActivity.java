package com.example.viruapp.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.viruapp.Model.Login;
import com.example.viruapp.Model.User;

import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements Callback<User> {

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
    }


    public void Login(View view){
        Login login = new Login("blgoear@hotmail.com", "indal");
        Call<User> call = AppViruApiAdapter.getApiService().login(login);
        call.enqueue(this); //el callback

    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()){
            Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
            String token = response.body().getToken();

            SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();

            Intent intent = new Intent(this, PromotionActivity.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(MainActivity.this, "user incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
}
