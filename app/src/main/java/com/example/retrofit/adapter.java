package com.example.retrofit;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.myView>  {

    List<model> data;

    public adapter(List<model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview_design , parent , false);
        return new myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
             holder.name.setText(data.get(position).getName());
             holder.city.setText(data.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView name , city;
        public myView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewNameId);
            city = itemView.findViewById(R.id.textViewCityId);

        }
    }


}
