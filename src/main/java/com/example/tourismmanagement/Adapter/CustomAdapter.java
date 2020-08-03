package com.example.tourismmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.InterFace.Province.UpdateProvinceActivity;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<ProvinceModel> provinceModels;

    public CustomAdapter(Context context, ArrayList<ProvinceModel> provinceModels) {
        this.context = context;
        this.provinceModels = provinceModels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_province, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ProvinceModel province = provinceModels.get(position);
        holder.txt_p_code.setText(province.getP_code());
        holder.txt_p_name.setText(province.getP_name());
        holder.txt_p_religion.setText(province.getP_regions());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), txt_p_name.getText() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent((Activity)context, UpdateProvinceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("p_code", province.getP_code());
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DBProvince dbProvince = new DBProvince(context);
                dbProvince.deleteProvince(province.getP_code());
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return provinceModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_p_code, txt_p_name, txt_p_religion;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            txt_p_code = itemView.findViewById(R.id.textViewRow_p_code);
            txt_p_name = itemView.findViewById(R.id.textViewRow_p_name);
            txt_p_religion = itemView.findViewById(R.id.tvicon);
        }


    }
}
