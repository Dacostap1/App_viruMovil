package com.example.viruapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viruapp.Model.Modul;
import com.example.viruapp.R;

import java.util.ArrayList;

public class ModulAdapter extends PagerAdapter {

    private ArrayList<Modul> mDataSet;

    public ModulAdapter(Context context) {
        this.mDataSet = new ArrayList<>();
        this.context = context;
    }

    public void setDataSet(ArrayList<Modul> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    private LayoutInflater layoutInflater;
    private Context context;

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.modul_view, container, false);

        TextView nombre, solicitud;
        nombre = view.findViewById(R.id.txt_name);
        solicitud = view.findViewById(R.id.txt_solicitud);

        nombre.setText(mDataSet.get(position).getName());
        solicitud.setText(mDataSet.get(position).getSolicitud());

        nombre.setOnClickListener(v -> {
            Toast.makeText(context, "holi " + nombre.getText().toString(), Toast.LENGTH_SHORT).show();
        });

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object );
    }
}
