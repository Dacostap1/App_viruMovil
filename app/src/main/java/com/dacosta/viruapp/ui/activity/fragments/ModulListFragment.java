package com.dacosta.viruapp.ui.activity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dacosta.viruapp.Model.Modul;
import com.dacosta.viruapp.R;
import com.dacosta.viruapp.io.AppViruApiAdapter;
import com.dacosta.viruapp.ui.adapter.ModulAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModulListFragment extends Fragment implements Callback<ArrayList<Modul>> {

    private ModulAdapter mAdapter;
    ViewPager viewPager;
    private OnFragmentInteractionListener mListener;
    private int student_id;
    private String student_name;

    public ModulListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student_id = getArguments().getInt("student_id");
            student_name = getArguments().getString("student_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_modul_list, container, false);
        getActivity().setTitle(student_name);

        viewPager = vista.findViewById(R.id.viewPager);

        mAdapter = new ModulAdapter(getContext());
        viewPager.setAdapter(mAdapter);
        viewPager.setPadding(190, 0, 60, 0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Call<ArrayList<Modul>> call = AppViruApiAdapter.getApiService().getModulbyStudent(token, student_id);
        call.enqueue(this);

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
    public void onResponse(Call<ArrayList<Modul>> call, Response<ArrayList<Modul>> response) {
        if (response.isSuccessful()){
            ArrayList<Modul> modul = response.body();
            if(modul.isEmpty()){
                Toast.makeText(getContext(), "No tiene módulos", Toast.LENGTH_SHORT).show();
            }
            mAdapter.setDataSet(modul);
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Modul>> call, Throwable t) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
