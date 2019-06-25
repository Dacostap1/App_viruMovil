package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viruapp.Model.Login;
import com.example.viruapp.Model.User;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<User> {

    private Button btn_login;
    private EditText txt_user, txt_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        txt_user = findViewById(R.id.txt_user);
        txt_password = findViewById(R.id.txt_password);

    }


    public void Login(View view){
        String user = txt_user.getText().toString();
        String password = txt_password.getText().toString();

        Login login = new Login(user, password);
        Call<User> call = AppViruApiAdapter.getApiService().login(login);
        call.enqueue(this); //el callback

    }


    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful()){
            Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
            String token = response.body().getToken();

            SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", token);
            editor.commit();

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        }
        else{
            Toast.makeText(LoginActivity.this, "user incorrecto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }
}
