package com.example.tourismmanagement.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.Customer.UpdateCustomer;
import com.example.tourismmanagement.Model.BookingModel;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<BookingModel> bookingModels;
    ArrayList<BookingModel> bookingModelArrayList;
    ArrayList<TourModel> tourModels;
    ArrayList<CustomerModel> customerModels;
    public BookingAdapter(Context context, ArrayList<BookingModel> bookingModels) {
        this.context = context;
        this.bookingModels = bookingModels;
        bookingModelArrayList = new ArrayList<>(bookingModels);
        tourModels = new ArrayList<TourModel>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_booking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final BookingModel booking = bookingModels.get(position);
        DBTours dbTours  = new DBTours(holder.itemView.getContext());
        tourModels = dbTours.getTourByCode(booking.getTour_id());
        holder.txt_bk_tour.setText(tourModels.get(0).getTour_name());

        DBCustomer dbCustomer = new DBCustomer(holder.itemView.getContext());
        customerModels = dbCustomer.getDataByCode(booking.getCustomer_id());
        holder.txt_bk_customer.setText(customerModels.get(0).getC_fullname());
        holder.txt_bk_dayStart.setText(booking.getBk_dayStart());
        if (booking.getBk_status().equals("0")){
            holder.txt_bk_status.setText(booking.getBk_status());
            holder.txt_bk_status.setTextColor(Color.YELLOW);
        } else {
            if (booking.getBk_status().equals("1")){
                holder.txt_bk_status.setText(booking.getBk_status());
                holder.txt_bk_status.setTextColor(Color.GREEN);
            } else {
                holder.txt_bk_status.setText(booking.getBk_status());
                holder.txt_bk_status.setTextColor(Color.RED);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent((Activity)context, UpdateCustomer.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("bk_id", booking.getBk_id());
//                intent.putExtras(bundle);
//                ((Activity) context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_bk_tour, txt_bk_customer, txt_bk_dayStart, txt_bk_status;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_bk_tour = itemView.findViewById(R.id.tvRow_bk_tour);
            txt_bk_customer = itemView.findViewById(R.id.tvRow_bk_customer);
            txt_bk_dayStart = itemView.findViewById(R.id.tvRow_bk_dayStart);
            txt_bk_status = itemView.findViewById(R.id.tvRow_bk_status);

        }


    }
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<BookingModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(bookingModelArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BookingModel item : bookingModels) {
                    if (item.getCustomer_id().toLowerCase().contains(filterPattern)) {
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
            bookingModels.clear();
            bookingModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
