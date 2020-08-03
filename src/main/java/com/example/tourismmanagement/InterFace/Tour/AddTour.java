package com.example.tourismmanagement.InterFace.Tour;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tourismmanagement.DataBase.DBDestination;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.Customer.AddCustomerActivity;
import com.example.tourismmanagement.InterFace.Customer.CustomerActivity;
import com.example.tourismmanagement.InterFace.Customer.UpdateCustomer;
import com.example.tourismmanagement.InterFace.Destination.AddDestination;
import com.example.tourismmanagement.InterFace.Destination.Destination;
import com.example.tourismmanagement.InterFace.NumberPickerDialog;
import com.example.tourismmanagement.Model.DestinationModel;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.Model.TourModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddTour extends AppCompatActivity {
    Button btn_t_add, btn_t_cancel, btn_t_delete;
    TextInputEditText IET_t_id, IET_t_name, IET_t_time, IET_t_price, IET_t_departure, IET_t_info;
    TextInputLayout IL_t_id, IL_t_name, IL_t_time, IL_t_price, IL_t_departure, IL_t_info;
    Spinner sp_destination;
    CheckBox cbTrain, cbBicycle, cbBike, cbBus, cbPlane, cbBoat;
    String vehicle = "";
    String code = "";
    ArrayList<String> data_spinner = new ArrayList<>();
    ArrayList<DestinationModel> data_destination = new ArrayList<>();
    ArrayList<TourModel> data_tour = new ArrayList<>();
    TourModel tourModel;
    DBTours dbTours;
    DBDestination dbDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tour);
        setControl();
        setEvent();
    }

    private void setEvent() {
        getDataSpinner();
        getDataByCode();
        if (!code.equals("")) {
            try {
                Log.d("tour", ": " + data_tour.get(0).toString());
                IET_t_id.setText(data_tour.get(0).getTour_id());
                IET_t_name.setText(data_tour.get(0).getTour_name());
                IET_t_time.setText(data_tour.get(0).getTour_time() + data_tour.get(0).getTour_price());
                IET_t_price.setText(data_tour.get(0).getTour_price() + "");
                IET_t_departure.setText(data_tour.get(0).getTour_departure());
                IET_t_info.setText(data_tour.get(0).getTour_info());

                dbDestination = new DBDestination(getApplicationContext());
                data_destination = dbDestination.getDesByCode(data_tour.get(0).getTour_destination());
                int index = 0;
                index = getIndex(sp_destination, data_destination.get(0).getDes_name());
                sp_destination.setSelection(index);

                String str = data_tour.get(0).getTour_vehicle();
                String[] strVehicle = str.split("\\_");
                for (int i = 0; i < strVehicle.length; i++) {
                    if (strVehicle[i].equals("Bicycle")) {
                        cbBicycle.setChecked(true);
                    }
                    if (strVehicle[i].equals("Bike")) {
                        cbBike.setChecked(true);
                    }
                    if (strVehicle[i].equals("Boat")) {
                        cbBoat.setChecked(true);
                    }
                    if (strVehicle[i].equals("Bus")) {
                        cbBus.setChecked(true);
                    }
                    if (strVehicle[i].equals("Plane")) {
                        cbPlane.setChecked(true);
                    }
                    if (strVehicle[i].equals("Train")) {
                        cbTrain.setChecked(true);
                    }
                }
            } catch (Exception ex) {

            }
        }
        btn_t_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!code.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddTour.this);
                    builder.setTitle("Delete " + IET_t_name.getText().toString() + " ?");
                    builder.setMessage("Are you sure you want to delete " + IET_t_name.getText().toString() + " ?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dbTours = new DBTours(getApplicationContext());
                            dbTours.deleteDestination(code);
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddTour.this, ToursActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();

                }
            }
        });


        btn_t_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TourModel tourModel = new TourModel();
                if (IET_t_id.getText().length() > 0 && IET_t_name.getText().length() > 0 && IET_t_time.getText().length() > 0
                        && IET_t_price.getText().length() > 0 && IET_t_departure.getText().length() > 0 && IET_t_info.getText().length() > 0) {
                    tourModel.setTour_id(IET_t_id.getText().toString());
                    tourModel.setTour_name(IET_t_name.getText().toString());
                    tourModel.setTour_time(IET_t_time.getText().toString());
                    tourModel.setTour_price(Integer.parseInt(IET_t_price.getText().toString()));
                    tourModel.setTour_departure(IET_t_departure.getText().toString());
                    tourModel.setTour_info(IET_t_info.getText().toString());
                    if (cbBicycle.isChecked() == true) {
                        vehicle += "Bicycle_";
                    }
                    if (cbBike.isChecked() == true) {
                        vehicle += "Bike_";
                    }
                    if (cbBoat.isChecked() == true) {
                        vehicle += "Boat_";
                    }
                    if (cbBus.isChecked() == true) {
                        vehicle += "Bus_";
                    }
                    if (cbPlane.isChecked() == true) {
                        vehicle += "Plane_";
                    }
                    if (cbTrain.isChecked() == true) {
                        vehicle += "Train_";
                    }
                    tourModel.setTour_vehicle(vehicle);
                    try {
                        data_destination = dbDestination.getDesByName(sp_destination.getSelectedItem().toString());
                        tourModel.setTour_destination(data_destination.get(0).getDes_code());
                        dbTours = new DBTours(getApplicationContext());
                        if (!code.equals("")){
                            dbTours.updateTour(tourModel);
                            Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                        } else{
                            dbTours.addTour(tourModel);
                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Add failed!", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddTour.this, ToursActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private void getDataByCode() {
        try {
            code = getIntent().getExtras().getString("tour_id");
            dbTours = new DBTours(getApplicationContext());
            Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
            data_tour = dbTours.getTourByCode(code);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Add new", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataSpinner() {
        dbDestination = new DBDestination(getApplicationContext());
        try {
            data_destination = dbDestination.getListDestination();
            for (DestinationModel item : data_destination) {
                data_spinner.add(item.getDes_name());
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data_spinner);
            sp_destination.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl() {
        btn_t_add = findViewById(R.id.btn_add_tour);
        btn_t_cancel = findViewById(R.id.btn_c_cancel);
        btn_t_delete = findViewById(R.id.btn_delete_tour);
        IET_t_id = findViewById(R.id.edt_t_code);
        IET_t_name = findViewById(R.id.edt_t_name);
        IET_t_time = findViewById(R.id.edt_t_time);
        IET_t_price = findViewById(R.id.edt_t_price);
        IET_t_departure = findViewById(R.id.edt_t_departure);
        IET_t_info = findViewById(R.id.edt_t_info);

        IL_t_id = findViewById(R.id.edtIL_t_code);
        IL_t_name = findViewById(R.id.edtIL_t_name);
        IL_t_time = findViewById(R.id.edtIL_t_time);
        IL_t_price = findViewById(R.id.edtIL_t_price);
        IL_t_departure = findViewById(R.id.edtIL_t_departure);
        IL_t_info = findViewById(R.id.edtIL_t_info);
        sp_destination = findViewById(R.id.sp_t_destination);
        cbBicycle = findViewById(R.id.chkBicycle);
        cbBike = findViewById(R.id.chkBike);
        cbBoat = findViewById(R.id.chkBoat);
        cbBus = findViewById(R.id.chkBus);
        cbPlane = findViewById(R.id.chkAirPlane);
        cbTrain = findViewById(R.id.chkTrain);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}