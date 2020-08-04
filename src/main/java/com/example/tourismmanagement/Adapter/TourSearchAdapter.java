package com.example.tourismmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TourSearchAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<TourModel> tourModels;
    ArrayList<TourModel> tourModelArrayList;
    int resource;
    public TourSearchAdapter(Context context, int resource , ArrayList<TourModel> tourModels) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.tourModels = tourModels;
        tourModelArrayList = new ArrayList<>(tourModels);
    }

    @Override
    public int getCount() {
        return tourModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(resource, null);
        ImageView img = view.findViewById(R.id.img_t_rowS);
        TextView textViewNameCustomer = view.findViewById(R.id.name_t_rowS);

        TourModel tourModel = tourModels.get(position);
        byte[] customer_img = tourModel.getImg_destination();
        Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
        img.setImageBitmap(bitmap);
        textViewNameCustomer.setText(tourModel.getTour_name());
        return view;

    }

    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<TourModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tourModelArrayList);
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
