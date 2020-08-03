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

import com.example.tourismmanagement.InterFace.Customer.UpdateCustomer;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList<CustomerModel> customerModels;
    ArrayList<CustomerModel> customerModelsFull;
    public CustomerAdapter(Context context, ArrayList<CustomerModel> customerModels) {
        this.context = context;
        this.customerModels = customerModels;
        customerModelsFull = new ArrayList<>(customerModels);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_customer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final CustomerModel customer = customerModels.get(position);
        holder.txt_c_code.setText(customer.getC_code());
        holder.txt_c_phone.setText(customer.getC_numberphone());
        holder.txt_c_name.setText(customer.getC_fullname());
        holder.txt_c_address.setText(customer.getC_address());
        byte[] customer_img = customer.getImgavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
        holder.img_c.setImageBitmap(bitmap);
//        if (customer.getC_sex().equals("0")){
//            holder.img_c.setImageResource(R.drawable.man);
//        } else {
//            if (customer.getC_sex().equals("1")){
//                holder.img_c.setImageResource(R.drawable.woman);
//            } else {
//                if (customer.getC_sex().equals("2")){
//                    holder.img_c.setImageResource(R.drawable.sex);
//                } else {
//                    holder.img_c.setImageResource(R.drawable.sex);
//                }
//            }
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity)context, UpdateCustomer.class);
                Bundle bundle = new Bundle();
                bundle.putString("c_code", customer.getC_code());
                intent.putExtras(bundle);
                ((Activity) context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return customerModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_c_code, txt_c_name, txt_c_address, txt_c_phone;
        ImageView img_c;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_c_code = itemView.findViewById(R.id.textViewRow_c_code);
            txt_c_name = itemView.findViewById(R.id.textViewRow_c_name);
            txt_c_phone = itemView.findViewById(R.id.textViewRow_c_numberPhone);
            txt_c_address = itemView.findViewById(R.id.textViewRow_c_Address);
            img_c = itemView.findViewById(R.id.imageViewRow_c);
        }


    }
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
