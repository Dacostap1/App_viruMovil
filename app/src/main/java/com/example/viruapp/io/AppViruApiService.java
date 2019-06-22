package com.example.viruapp.io;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.Model.Promotion;
import com.example.viruapp.Model.Login;
import com.example.viruapp.Model.Student;
import com.example.viruapp.Model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppViruApiService {


    @POST("login")
    Call<User> login(@Body Login login);

    @FormUrlEncoded
    @POST("promotion")
    Call<Promotion> createPromotion(
        @Header("Authorization") String authToken,
        @Field("name") String name,
        @Field("user_id") int user
    );

    @GET("promotion")
    Call<ArrayList<Promotion>> getPromotions(@Header("Authorization") String authToken);

    @GET("student")
    Call<ArrayList<Student>> getStudents(@Header("Authorization") String authToken);

    @GET("student/{promo}")
    Call<ArrayList<Student>> getStudentsbyPromotion(@Header("Authorization") String authToken, @Path("promo") int promo_id);

    @GET("modul/{student}")
    Call<ArrayList<Modul>> getModulbyStudent(@Header("Authorization") String authToken, @Path("student") int student_id);



}
