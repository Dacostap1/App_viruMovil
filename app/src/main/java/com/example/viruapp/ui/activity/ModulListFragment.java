package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;
import com.example.viruapp.ui.adapter.ModulAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModulListFragment extends Fragment implements Callback<ArrayList<Modul>> {

    private ModulAdapter mAdapter;
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private int student_id;

    public ModulListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student_id = getArguments().getInt("student_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_modul_list, container, false);
        recyclerView = vista.findViewById(R.id.reclicler_view);
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ModulAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

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
