package com.example.viruapp.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.viruapp.Model.Student;
import com.example.viruapp.R;
import com.example.viruapp.io.AppViruApiAdapter;

import com.example.viruapp.ui.adapter.StudentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListFragment extends Fragment implements Callback<ArrayList<Student>> {

    private StudentAdapter mAdapter;
    private int promo_id;

    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public StudentListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            promo_id = getArguments().getInt("promo_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token =  "Bearer " + preferences.getString("token", "");

        View vista = inflater.inflate(R.layout.fragment_student_list, container, false);

        recyclerView = vista.findViewById(R.id.reclicler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true); //el tamaño será el mismo para todos
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new StudentAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        Call<ArrayList<Student>> call = AppViruApiAdapter.getApiService().getStudentsbyPromotion(token, promo_id);
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
    public void onResponse(Call<ArrayList<Student>> call, Response<ArrayList<Student>> response) {
        if(response.isSuccessful()){
            ArrayList<Student> students = response.body();
            Log.d("RT", "tamaño de array => " + students.size());
            mAdapter.setDataSet(students);

        }else{
            Toast.makeText(getContext(), "token es fake", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ArrayList<Student>> call, Throwable t) {
        Toast.makeText(getContext(), "No hay conexion a internet", Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
