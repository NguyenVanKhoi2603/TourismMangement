package com.example.tourismmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<ProvinceModel> provinceModels;

    public CustomAdapter(Context context, ArrayList<ProvinceModel> provinceModels) {
        this.context = context;
        this.provinceModels = provinceModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.row_province, parent, false);

        View view = LayoutInflater.from(context).inflate(R.layout.row_province, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProvinceModel province = provinceModels.get(position);
        holder.txt_p_code.setText(province.getP_code());
        holder.txt_p_name.setText(province.getP_name());
        holder.txt_p_religion.setText(province.getP_regions());

    }

    @Override
    public int getItemCount() {
        return provinceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_p_code, txt_p_name, txt_p_religion;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_p_code = itemView.findViewById(R.id.textViewRow_p_code);
            txt_p_name = itemView.findViewById(R.id.textViewRow_p_name);
            txt_p_religion = itemView.findViewById(R.id.textViewRow_p_religion);

        }
    }
}
