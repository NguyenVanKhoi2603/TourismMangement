package com.example.tourismmanagement.InterFace.BookingTour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomerSearchAdapter;
import com.example.tourismmanagement.Adapter.TourSearchAdapter;
import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class FormBookingTour extends AppCompatActivity {

    public  ListView listViewCustomer, listViewTour;
    Dialog dialogCustomer, dialogTour;
    CustomerModel customerModel;
    TourModel tourModel;
    DBCustomer dbCustomer;
    DBTours dbTours;
    TextInputEditText txt_search_customer, txt_search_tour;
    TextView textViewCustomer, textViewTour;
    ImageView imgViewCustomer, imageViewTour;
    ImageButton iBtn_chooseCustomer, iBtn_close_dialog, imageButtonClose_dialog_tour, iBtn_chooseTour;
    public CustomerSearchAdapter customerAdapter;
    public TourSearchAdapter tourSearchAdapter;
    ArrayList<CustomerModel> data_customer  = new ArrayList<>();
    ArrayList<TourModel> data_tour  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_booking_tour);
        dialogCustomer = new Dialog(this);
        dialogTour = new Dialog(this);
        setControl();
        setEvent();
    }
    private void setEvent() {
        try {
            dbCustomer = new DBCustomer(getApplicationContext());
            data_customer = dbCustomer.getListCustomer();
            dbTours = new DBTours(getApplicationContext());
            data_tour = dbTours.getListTour();
        } catch (Exception ex){
        }

        iBtn_chooseCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopupCustomer(view);
            }
        });

        iBtn_chooseTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopupTour(view);
            }
        });
    }

    private void setControl() {
        imgViewCustomer = findViewById(R.id.img_bk_customer);
        textViewCustomer = findViewById(R.id.tv_bk_customer);
        iBtn_chooseCustomer = findViewById(R.id.btn_bk_chooseCustomer);
        textViewTour = findViewById(R.id.tv_bk_tourname);
        imageViewTour = findViewById(R.id.img_bk_tour);
        iBtn_chooseTour = findViewById(R.id.btn_bk_chooseTour);
    }
    public void ShowPopupTour(View v) {
        dialogTour.setContentView(R.layout.search_tour);
        listViewTour = dialogTour.findViewById(R.id.listview_searchTour);
        txt_search_tour = dialogTour.findViewById(R.id.search_tour_txt);
        imageButtonClose_dialog_tour = dialogTour.findViewById(R.id.iBtn_close_search_tour);
        txt_search_tour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tourSearchAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0){
                    tourSearchAdapter.getFilter().filter(editable.toString());
                }
            }
        });
        imageButtonClose_dialog_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTour.cancel();
            }
        });
        try {
            tourSearchAdapter = new TourSearchAdapter(getApplicationContext(), R.layout.tour_itemlist_row, data_tour);
            listViewTour.setAdapter(tourSearchAdapter);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
        listViewTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tourModel  = new TourModel();
                tourModel  = data_tour.get(i);
                textViewTour.setText(tourModel.getTour_name());

                byte[] customer_img = tourModel.getImg_destination();
                Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
                imageViewTour.setImageBitmap(bitmap);
                view.invalidate();
            }
        });
        dialogTour.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTour.show();
    }
    public void ShowPopupCustomer(View v) {
        dialogCustomer.setContentView(R.layout.search_customer);
        listViewCustomer = dialogCustomer.findViewById(R.id.listview_searchcustomer);
        txt_search_customer = dialogCustomer.findViewById(R.id.search_customer_txt);
        iBtn_close_dialog = dialogCustomer.findViewById(R.id.iBtn_close_search);
        txt_search_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                customerAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0){
                    customerAdapter.getFilter().filter(editable.toString());
                }
            }
        });
        iBtn_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCustomer.cancel();
            }
        });
        try {
            customerAdapter = new CustomerSearchAdapter(getApplicationContext(), R.layout.customer_spinner_row, data_customer);
            listViewCustomer.setAdapter(customerAdapter);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
        listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customerModel  = new CustomerModel();
                customerModel  = data_customer.get(i);
                textViewCustomer.setText(customerModel.getC_fullname());

                byte[] customer_img = customerModel.getImgavatar();
                Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
                imgViewCustomer.setImageBitmap(bitmap);
                view.setBackgroundColor(R.color.colorText);
                view.invalidate();
            }
        });
        dialogCustomer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCustomer.show();
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}