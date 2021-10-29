package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.RecyclerViewHolder>{
    Context parentContext;
    ArrayList<String> list;

    public RecyclerAdaptor(Context context, ArrayList<String> list) {
        parentContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentContext).inflate(R.layout.holder_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.textView.setText("Text "+position);
        holder.textView.setText("Button "+position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        Button button;
        TextView textView;
        //declared widjets here and do the findviewbyid stuff
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.id_holder_textView);
            button = itemView.findViewById(R.id.id_holder_button);
        }
    }


}
