package com.example.viruapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viruapp.Model.Foto;
import com.example.viruapp.R;

import java.util.ArrayList;


public class GaleriaAdapter extends PagerAdapter {

    private ArrayList<Foto> fotos;
    private LayoutInflater layoutInflater;
    private Context context;

    public GaleriaAdapter(Context context) {
        this.fotos = new ArrayList<>();
        this.context = context;
    }

    public void setFotos(ArrayList<Foto> fotos){
        this.fotos = fotos;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View vista = layoutInflater.inflate(R.layout.item_galeria_view, container, false);
        final Foto foto = fotos.get(position);

        ImageView img;
        TextView desc_img;

        img = vista.findViewById(R.id.foto);
        desc_img = vista.findViewById(R.id.desc_img);

        img.setImageResource(foto.getImage());
        desc_img.setText(foto.getTitle());

        container.addView(vista,0);

        return vista;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
