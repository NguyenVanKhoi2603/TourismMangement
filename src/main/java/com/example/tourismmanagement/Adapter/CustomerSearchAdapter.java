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
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchAdapter extends ArrayAdapter {
    private Context context;
    private Activity activity;
    private ArrayList<CustomerModel> customerModels;
    ArrayList<CustomerModel> customerModelsFull;
    int resource;
    public CustomerSearchAdapter(Context context,int resource , ArrayList<CustomerModel> customerModels) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.customerModels = customerModels;
        customerModelsFull = new ArrayList<>(customerModels);

    }

    @Override
    public int getCount() {
        return customerModels.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(resource, null);
        ImageView img = view.findViewById(R.id.img_c_rowS);
        TextView textViewNameCustomer = view.findViewById(R.id.name_c_rowS);

        CustomerModel customer = customerModels.get(position);
        byte[] customer_img = customer.getImgavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
        img.setImageBitmap(bitmap);
        textViewNameCustomer.setText(customer.getC_fullname());
        return view;

    }

    //    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        View view = LayoutInflater.from(context).inflate(R.layout.customer_spinner_row, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        final CustomerModel customer = customerModels.get(position);
//        holder.txt_c_name.setText(customer.getC_fullname());
//        byte[] customer_img = customer.getImgavatar();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
//        holder.img_c.setImageBitmap(bitmap);
////        if (customer.getC_sex().equals("0")){
////            holder.img_c.setImageResource(R.drawable.man);
////        } else {
////            if (customer.getC_sex().equals("1")){
////                holder.img_c.setImageResource(R.drawable.woman);
////            } else {
////                if (customer.getC_sex().equals("2")){
////                    holder.img_c.setImageResource(R.drawable.sex);
////                } else {
////                    holder.img_c.setImageResource(R.drawable.sex);
////                }
////            }
////        }
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return customerModels.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView txt_c_name;
//        ImageView img_c;
//        public MyViewHolder(@NonNull final View itemView) {
//            super(itemView);
//
//            txt_c_name = itemView.findViewById(R.id.name_c_rowS);
//            img_c = itemView.findViewById(R.id.img_c_rowS);
//        }
//
//
//    }
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CustomerModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(customerModelsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CustomerModel item : customerModels) {
                    if (item.getC_fullname().toLowerCase().contains(filterPattern)) {
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
            customerModels.clear();
            customerModels.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
