package com.dacosta.viruapp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dacosta.viruapp.Model.Student;
import com.dacosta.viruapp.R;
import com.dacosta.viruapp.ui.activity.HomeActivity;
import com.dacosta.viruapp.ui.activity.fragments.ModulListFragment;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Student> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_student;
        public ImageView img_student;
        public CardView card_student;
        public LinearLayout container_student;

        public ViewHolder(View v) {
            super(v);
            container_student = v.findViewById(R.id.container_student);
            txt_student = v.findViewById(R.id.txt_student);
            img_student = v.findViewById(R.id.img_student);
            card_student = v.findViewById(R.id.card_student);
        }
    }

    public StudentAdapter(Context context) {
        this.context = context;
        mDataSet = new ArrayList<>(); //Inicializamos un ArrayList vacio para que no falle al no encontrar elementos al inicio
    }

    public void setDataSet(ArrayList<Student> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged(); //Para indicar que hay cambios
    }

    // El layout manager invoca este método
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_view, parent, false);

        return new ViewHolder(v);
    }

    // Este método reemplaza el contenido de cada view,
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        final Student student = mDataSet.get(position);

        holder.container_student.setAnimation(AnimationUtils.loadAnimation(context, R.anim.student_transition_animation ));

        holder.txt_student.setText(student.getName());
        if(student.getGenero().equals("male")){
            holder.img_student.setImageResource(R.drawable.avatar_male);
        }else{
            holder.img_student.setImageResource(R.drawable.avatar_female);
        }
        holder.card_student.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("student_id", student.getId());
            bundle.putString("student_name", student.getName());
            ModulListFragment fragment = new ModulListFragment();
            fragment.setArguments(bundle);
            FragmentManager manager = ((HomeActivity)context).getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();

            ft.replace(R.id.content_home, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}