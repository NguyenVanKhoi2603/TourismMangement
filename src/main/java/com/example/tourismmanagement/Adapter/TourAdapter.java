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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.InterFace.Destination.AddDestination;
import com.example.tourismmanagement.InterFace.Tour.AddTour;
import com.example.tourismmanagement.Model.DestinationModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<TourModel> tourModels;
    ArrayList<TourModel> tour_array;

    public TourAdapter(Context context, ArrayList<TourModel> tourModels) {
        this.context = context;
        this.tourModels = tourModels;
        tour_array = new ArrayList<>(tourModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_tour, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final TourModel tourModel = tourModels.get(position);
        try {
            int strPrice = tourModel.getTour_price();
            String price = String.format("%,d", strPrice);
            holder.txt_t_price.setText(price);
        } catch (Exception ex) {
            holder.txt_t_price.setText(tourModel.getTour_price() + "");
        }
        byte[] customer_img = tourModel.getImg_destination();
        Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
        holder.imageViewTour.setImageBitmap(bitmap);
        holder.txt_t_title.setText(tourModel.getTour_name());
        holder.txt_t_date.setText(tourModel.getTour_time());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity) context, AddTour.class);
                Bundle bundle = new Bundle();
                bundle.putString("tour_id", tourModel.getTour_id());
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tourModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_t_title, txt_t_date, txt_t_price;
        ImageView imageViewTour;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_t_title = itemView.findViewById(R.id.edtRow_t_title);
            txt_t_date = itemView.findViewById(R.id.edtRow_t_datetime);
            txt_t_price = itemView.findViewById(R.id.edtRow_t_price);
            imageViewTour = itemView.findViewById(R.id.imgRow_t);
        }
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TourModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tourModels);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (TourModel item : tourModels) {
                    if (item.getTour_name().toLowerCase().contains(filterPattern)) {
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
            tourModels.clear();
            tourModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
