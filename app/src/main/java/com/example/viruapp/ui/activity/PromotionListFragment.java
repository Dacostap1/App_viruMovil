package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.viruapp.Model.Promotion;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;
import com.example.viruapp.ui.adapter.PromotionAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PromotionListFragment extends Fragment implements Callback<ArrayList<Promotion>> {

    private PromotionAdapter mAdapter;
    RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;

    public PromotionListFragment() {
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
        // Inflate the layout for this fragment
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        View vista = inflater.inflate(R.layout.fragment_promotion_list, container, false);
        recyclerView = vista.findViewById(R.id.reclicler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new PromotionAdapter(getContext());
        recyclerView.setAdapter(mAdapter);


        Call<ArrayList<Promotion>> call = AppViruApiAdapter.getApiService().getPromotions(token);
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
    public void onResponse(Call<ArrayList<Promotion>> call, Response<ArrayList<Promotion>> response) {
        if(response.isSuccessful()){
            ArrayList<Promotion> promotions = response.body();
            Log.d("RT", "tamaño de array => " + promotions.size());
            mAdapter.setDataSet(promotions);

        }
        else{
            Toast.makeText(getContext(), "token is expiried", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Promotion>> call, Throwable t) {
        Toast.makeText(getContext(), "No hay conexion a internet", Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
