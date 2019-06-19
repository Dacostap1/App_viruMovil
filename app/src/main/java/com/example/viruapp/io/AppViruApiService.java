package com.example.viruapp.io;

import com.example.viruapp.Model.Promotion;
import com.example.viruapp.Model.Login;
import com.example.viruapp.Model.Student;
import com.example.viruapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppViruApiService {


    @POST("login")
    Call<User> login(@Body Login login);

    @GET("promotion")
    Call<ArrayList<Promotion>> getPromotions(@Header("Authorization") String authToken);

    @GET("student")
    Call<ArrayList<Student>> getStudents(@Header("Authorization") String authToken);

    @GET("student/{student}")
    Call<ArrayList<Student>> getStudentsbyPromotion(@Header("Authorization") String authToken, @Path("student") int promo_id);



}
