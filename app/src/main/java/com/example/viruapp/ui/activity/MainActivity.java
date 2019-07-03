package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.viruapp.Model.Login;
import com.example.viruapp.Model.User;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements Callback<User> {
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        user = preferences.getString("user", "");
        pass = preferences.getString("pass", "");


        if(user.isEmpty()&&pass.isEmpty()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);
        }else{
            Login(user,pass);
        }

    }

    public void Login(String user, String pass){
        Login login = new Login(user, pass);
        Call<User> call = AppViruApiAdapter.getApiService().login(login);
        call.enqueue(this); //el callback

    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()){
           // Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
            String token = response.body().getToken();

            SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
}
