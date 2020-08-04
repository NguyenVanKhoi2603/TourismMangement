package com.example.tourismmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.DataBase.DBDestination;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.Customer.UpdateCustomer;
import com.example.tourismmanagement.InterFace.Destination.AddDestination;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.DestinationModel;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<DestinationModel> destinationModels;
    ArrayList<DestinationModel> destination_array;
    ArrayList<ProvinceModel> provinceModels;
    public DestinationAdapter(Context context, ArrayList<DestinationModel> destinationModels) {
        this.context = context;
        this.destinationModels = destinationModels;
        destination_array = new ArrayList<>(destinationModels);

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_destination, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final DestinationModel destination = destinationModels.get(position);
        holder.txt_d_name.setText(destination.getDes_name());
        DBProvince dbProvince  = new DBProvince(holder.itemView.getContext());
        provinceModels = dbProvince.getDataByCode(destination.getDes_province());
        holder.txt_d_province.setText(provinceModels.get(0).getP_name());
        String s = destination.getDes_description();
        String kq = "";
        if (s.length() > 80){
            kq = s.substring(0, 80) + "...";
        } else {
            kq = s;
        }
        holder.txt_d_description.setText(kq);

        byte[] destination_img = destination.getDes_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(destination_img, 0, destination_img.length);
        holder.img_destination.setImageBitmap(bitmap);
//        holder.img_destination.setImageResource(R.drawable.vinhhalong);
//        if (destination.getDes_image().equals("null")){
//            holder.img_destination.setImageResource(R.drawable.addimage);
//        } else {
//            try {
//                String url = "R.drawable."+destination.getDes_image();
//                holder.img_destination.setImageResource(Integer.parseInt(url));
//            } catch (Exception ex){
//
//            }
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity)context, AddDestination.class);
                Bundle bundle = new Bundle();
                bundle.putString("d_code", destination.getDes_code());
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(view.getContext(), txt_p_name.getText() + "", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent((Activity)context, UpdateProvinceActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("p_code", province.getP_code());
//                intent.putExtras(bundle);
//                ((Activity) context).startActivity(intent);
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                DBProvince dbProvince = new DBProvince(context);
//                dbProvince.deleteProvince(province.getP_code());
//                return false;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return destinationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_d_name, txt_d_description, txt_d_province;
        ImageView img_destination;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_d_name = itemView.findViewById(R.id.textViewRow_d_name);
            txt_d_description = itemView.findViewById(R.id.textViewRow_d_descriptipn);
            txt_d_province = itemView.findViewById(R.id.textViewRow_d_province);
            img_destination = itemView.findViewById(R.id.imageViewRow_destination);
        }
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DestinationModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(destinationModels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DestinationModel item : destinationModels) {
                    if (item.getDes_name().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            destinationModels.clear();
            destinationModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
