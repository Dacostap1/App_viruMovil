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
import android.widget.CheckBox;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.dacosta.viruapp.R;
import com.dacosta.viruapp.io.AppViruApiAdapter;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModulDetailFragment extends Fragment implements Callback<Void> {

    private EditText edt_solicitud, edt_memo, edt_informe;
    private LottieAnimationView animationView;
    private CheckBox check_recibo, check_proyecto, check_fsupervision;
    private Button btn_update;
    private int modul_id;
    private String modul_name, modul_solicitud, modul_memo,  modul_recibo, modul_informe, modul_proyecto, modul_fsupervision, modul_fevaluacion;
    private OnFragmentInteractionListener mListener;

    public ModulDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modul_id = getArguments().getInt("modul_id");
            modul_name = getArguments().getString("modul_name");
            modul_solicitud = getArguments().getString("modul_solicitud");
            modul_memo = getArguments().getString("modul_memo");
            modul_recibo = getArguments().getString("modul_recibo");
            modul_informe = getArguments().getString("modul_informe");
            modul_proyecto = getArguments().getString("modul_proyecto");
            modul_fsupervision = getArguments().getString("modul_fsupervision");
            modul_fevaluacion = getArguments().getString("modul_fevaluacion");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_modul_detail, container, false);
        getActivity().setTitle(modul_name);

        edt_solicitud = vista.findViewById(R.id.modul_solicitud);
        edt_memo = vista.findViewById(R.id.modul_memo);
        edt_informe = vista.findViewById(R.id.modul_informe);
        check_recibo = vista.findViewById(R.id.check_recibo);
        check_proyecto = vista.findViewById(R.id.check_proyecto);
        check_fsupervision = vista.findViewById(R.id.check_fsuper);
        btn_update = vista.findViewById(R.id.btn_update);
        animationView = vista.findViewById(R.id.animation_view);

        edt_solicitud.setText(modul_solicitud);
        edt_memo.setText(modul_memo);
        edt_informe.setText(modul_informe);


        if(modul_recibo.equals("1")){
            check_recibo.setChecked(true);
        }
        if(modul_proyecto.equals("1")){
            check_proyecto.setChecked(true);
        }
        if(modul_fsupervision.equals("1")){
            check_fsupervision.setChecked(true);
        }

        btn_update.setOnClickListener(v -> {
            Call<Void> call = AppViruApiAdapter.getApiService().updateModul(
                    token, modul_id ,modul_id, edt_solicitud.getText().toString(), edt_memo.getText().toString(), edt_informe.getText().toString(),
                    check_recibo.isChecked()? "1" : "0", check_proyecto.isChecked() ? "1" : "0", check_fsupervision.isChecked() ? "1" : "0"
            );
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
    public void onResponse(Call<Void> call, Response<Void> response) {
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
    public void onFailure(Call<Void> call, Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
