package com.example.tourismmanagement.InterFace.BookingTour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomerSearchAdapter;
import com.example.tourismmanagement.Adapter.TourSearchAdapter;
import com.example.tourismmanagement.DataBase.DBBooking;
import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.Customer.AddCustomerActivity;
import com.example.tourismmanagement.Model.BookingModel;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class FormBookingTour extends AppCompatActivity {

    public ListView listViewCustomer, listViewTour;
    Dialog dialogCustomer, dialogTour;
    CustomerModel customerModel, customerModel2;
    TourModel tourModel, tourModel2;
    DBCustomer dbCustomer;
    DBTours dbTours;
    DBBooking dbBooking;
    Spinner spinner_status;
    TextInputEditText txt_search_customer, txt_search_tour, txt_dayStart, txt_note;
    TextView textViewCustomer, textViewTour, textView_price;
    ImageView imgViewCustomer, imageViewTour;
    Button btn_save, btn_delete, btn_cancel;
    ImageButton iBtn_chooseCustomer, iBtn_close_dialog, imageButtonClose_dialog_tour, iBtn_chooseTour, btn_choose_date;
    public CustomerSearchAdapter customerAdapter;
    public TourSearchAdapter tourSearchAdapter;
    ArrayList<CustomerModel> data_customer = new ArrayList<>();
    ArrayList<CustomerModel> data_customer2 = new ArrayList<>();
    ArrayList<String> data_spinner = new ArrayList<>();
    ArrayList<TourModel> data_tour = new ArrayList<>();
    ArrayList<TourModel> data_tour2 = new ArrayList<>();
    ArrayList<BookingModel> data_booking = new ArrayList<>();
    String tour_id = "", customer_id = "";
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    Calendar calendar;
    int day = 0;
    int month = 0;
    int year = 0;
    String bookingId = "";
    Boolean update = false;
    String idUpdate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_booking_tour);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.booking_tour);
        dialogCustomer = new Dialog(this);
        dialogTour = new Dialog(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        if (update == true){
            btn_delete.setEnabled(true);
        } else {
            btn_delete.setEnabled(false);
        }
        initializeSp();
        getDataByCode();
        dbBooking = new DBBooking(this);
        if (update == true) {
            try {
                dbTours = new DBTours(this);
                //customerModel2 = new CustomerModel()
                data_tour2 = dbTours.getTourByCode(data_booking.get(0).getTour_id());
                textViewTour.setText(data_tour2.get(0).getTour_name());
                byte[] img_tour = data_tour2.get(0).getImg_destination();
                Bitmap bitmap = BitmapFactory.decodeByteArray(img_tour, 0, img_tour.length);
                imageViewTour.setImageBitmap(bitmap);
                int strPrice = data_tour2.get(0).getTour_price();
                String price = String.format("%,d", strPrice);
                textView_price.setText(price);
                dbCustomer = new DBCustomer(this);
                data_customer2 = dbCustomer.getDataByCode(data_booking.get(0).getCustomer_id());
                textViewCustomer.setText(data_customer2.get(0).getC_fullname());
                byte[] img_customer = data_customer2.get(0).getImgavatar();
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(img_customer, 0, img_customer.length);
                imgViewCustomer.setImageBitmap(bitmap2);
                customer_id = data_booking.get(0).getCustomer_id();
                tour_id = data_booking.get(0).getTour_id();
                txt_dayStart.setText(data_booking.get(0).getBk_dayStart());

            } catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
            txt_note.setText(data_booking.get(0).getBk_note());

            spinner_status.setSelection(Integer.parseInt(data_booking.get(0).getBk_status()));



        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewCustomer.length() != 0 && textViewTour.length() != 0 && txt_dayStart.length() != 0 && !customer_id.equals("") && !tour_id.equals("")) {
                    BookingModel booking = new BookingModel();
                    calendar = Calendar.getInstance();
                    if (update == false){
                        bookingId = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MILLISECOND)) + tour_id + customer_id;
                    } else {
                        bookingId =  idUpdate;
                    }
                    booking.setBk_id(bookingId);
                    booking.setCustomer_id(customer_id);
                    booking.setTour_id(tour_id);
                    booking.setBk_dayStart(txt_dayStart.getText().toString());
                    booking.setBk_note(txt_note.getText().toString());
                    booking.setBk_status(spinner_status.getSelectedItemPosition() + "");
                    try {
                        if (update == false){
                            dbBooking.addBooking(booking);
                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(FormBookingTour.this, BookingTour.class);
                            startActivity(intent);
                        } else
                        {
                            dbBooking.updateUpdateBooking(booking);
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(FormBookingTour.this, BookingTour.class);
                            startActivity(intent);
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbBooking = new DBBooking(getApplicationContext());
                    dbBooking.deleteBooking(idUpdate);
                    Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FormBookingTour.this, BookingTour.class);
                    startActivity(intent);
                } catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Error Delete!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormBookingTour.this, BookingTour.class);
                startActivity(intent);
            }
        });
        try {
            dbCustomer = new DBCustomer(getApplicationContext());
            data_customer = dbCustomer.getListCustomer();
            dbTours = new DBTours(getApplicationContext());
            data_tour = dbTours.getListTour();
        } catch (Exception ex) {
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
        btn_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePresent();
                datePickerDialog = new DatePickerDialog(FormBookingTour.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        getTimePresent();
        txt_dayStart.setText(day + "/" + (month + 1) + "/" + year);
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int iYear, int iMonth, int iDay) {
                txt_dayStart.setText(iDay + "/" + (iMonth + 1) + "/" + iYear);
            }
        };
    }

    private void getDataByCode() {
        try {
            bookingId = getIntent().getExtras().getString("booking_id");
            idUpdate = getIntent().getExtras().getString("booking_id");
            dbBooking = new DBBooking(getApplicationContext());
            update = true;
            Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
            data_booking = dbBooking.getDataByCode(bookingId);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Add new", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTimePresent() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    private void initializeSp() {
        data_spinner.add("Not started yet");
        data_spinner.add("Happenning");
        data_spinner.add("Finished");
        ArrayAdapter adapter_spinner = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, data_spinner);
        spinner_status.setAdapter(adapter_spinner);
    }

    private void setControl() {
        imgViewCustomer = findViewById(R.id.img_bk_customer);
        textViewCustomer = findViewById(R.id.tv_bk_customer);
        iBtn_chooseCustomer = findViewById(R.id.btn_bk_chooseCustomer);
        textViewTour = findViewById(R.id.tv_bk_tourname);
        imageViewTour = findViewById(R.id.img_bk_tour);
        iBtn_chooseTour = findViewById(R.id.btn_bk_chooseTour);
        btn_save = findViewById(R.id.btn_t_add);
        btn_delete = findViewById(R.id.btn_t_delete);
        btn_cancel = findViewById(R.id.btn_t_cancel);
        textView_price = findViewById(R.id.tv_bk_price);
        txt_dayStart = findViewById(R.id.edt_t_daystart);
        txt_note = findViewById(R.id.edt_t_note);
        spinner_status = findViewById(R.id.sp_status_tour);
        btn_choose_date = findViewById(R.id.button_t_chooseDay);
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
                if (editable.length() == 0) {
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
                tourModel = new TourModel();
                tourModel = data_tour.get(i);
                textViewTour.setText(tourModel.getTour_name());
                byte[] customer_img = tourModel.getImg_destination();
                Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
                imageViewTour.setImageBitmap(bitmap);
                view.invalidate();
                tour_id = tourModel.getTour_id();
                int strPrice = tourModel.getTour_price();
                String price = String.format("%,d", strPrice);
                textView_price.setText(price);
                dialogTour.cancel();
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
                if (editable.length() == 0) {
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
                customerModel = new CustomerModel();
                customerModel = data_customer.get(i);
                textViewCustomer.setText(customerModel.getC_fullname());

                byte[] customer_img = customerModel.getImgavatar();
                Bitmap bitmap = BitmapFactory.decodeByteArray(customer_img, 0, customer_img.length);
                imgViewCustomer.setImageBitmap(bitmap);
                view.setBackgroundColor(R.color.colorText);
                view.invalidate();
                customer_id = customerModel.getC_code();
                dialogCustomer.cancel();
            }
        });
        dialogCustomer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogCustomer.show();
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }
}