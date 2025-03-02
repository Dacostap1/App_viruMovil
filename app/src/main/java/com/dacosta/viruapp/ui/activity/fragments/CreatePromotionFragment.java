package com.dacosta.viruapp.ui.activity.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.dacosta.viruapp.Model.Promotion;
import com.dacosta.viruapp.R;
import com.dacosta.viruapp.io.AppViruApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePromotionFragment extends Fragment implements Callback<Promotion> {

    private OnFragmentInteractionListener mListener;
    private LottieAnimationView animationView;
    private EditText txt_name;
    private Button btn_registrar;

    public CreatePromotionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
       String token =  "Bearer " + preferences.getString("token", "");
        // Inflate the layout for this fragment
       View vista = inflater.inflate(R.layout.fragment_create_promotion, container, false);
       getActivity().setTitle("Crear Promoción");
       txt_name = vista.findViewById(R.id.edt_name);
       btn_registrar = vista.findViewById(R.id.btn_registrar);
       animationView = vista.findViewById(R.id.check_create);
       btn_registrar.setOnClickListener(v -> {
           Call<Promotion> call = AppViruApiAdapter.getApiService().createPromotion(token, txt_name.getText().toString());
           call.enqueue(this);
       });
       return vista;

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onResponse(Call<Promotion> call, Response<Promotion> response) {
        if(response.isSuccessful()){
            animationView.playAnimation();
            animationView.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    animationView.setProgress(0);
                }
            });
        }
    }

    @Override
    public void onFailure(Call<Promotion> call, Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
