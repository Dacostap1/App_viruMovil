package com.example.viruapp.ui.activity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viruapp.Model.Foto;
import com.example.viruapp.R;
import com.example.viruapp.ui.adapter.GaleriaAdapter;

import java.util.ArrayList;


public class GaleriaFragment extends Fragment {

    private ViewPager viewPager;
    private GaleriaAdapter adapter;
    private ArrayList<Foto> fotos;

    private OnFragmentInteractionListener mListener;

    public GaleriaFragment() {
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
        View vista = inflater.inflate(R.layout.fragment_galeria, container, false);
        viewPager = vista.findViewById(R.id.galeriaPager);
        getActivity().setTitle("Galería");

        fotos = new ArrayList<>();
        fotos.add(new Foto(R.drawable.foto_1, "Procesamiento de Lácteos"));
        fotos.add(new Foto(R.drawable.foto_2, "Exhibición de bebidas industriales"));
        fotos.add(new Foto(R.drawable.foto_3, "Capacitación de Docentes"));
        fotos.add(new Foto(R.drawable.foto_4, "Resultados de Panificación"));

        adapter = new GaleriaAdapter(getContext());
        adapter.setFotos(fotos);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
