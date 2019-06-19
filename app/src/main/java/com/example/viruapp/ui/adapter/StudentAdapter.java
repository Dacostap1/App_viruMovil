package com.example.viruapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viruapp.Model.Promotion;
import com.example.viruapp.Model.Student;
import com.example.viruapp.R;
import com.example.viruapp.ui.activity.ModulActivity;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Student> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ViewHolder(TextView tv) {
            super(tv);
            textView = tv;
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
        // Creamos una nueva vista
        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_view, parent, false);

        return new ViewHolder(tv);
    }

    // Este método reemplaza el contenido de cada view,
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - obtenemos un elemento del dataset según su posición
        final Student student = mDataSet.get(position);

        holder.textView.setText(student.getName());
        holder.textView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModulActivity.class);
            intent.putExtra("student_id", student.getId());
            context.startActivity(intent);
        });
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}