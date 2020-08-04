package com.example.tourismmanagement.InterFace.BookingTour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.BookingAdapter;
import com.example.tourismmanagement.Adapter.TourAdapter;
import com.example.tourismmanagement.DataBase.DBBooking;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.MainActivity;
import com.example.tourismmanagement.InterFace.Tour.AddTour;
import com.example.tourismmanagement.InterFace.Tour.ToursActivity;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookingTour extends AppCompatActivity {

    FloatingActionButton floatingActionBtn_add_booking;
    RecyclerView recyclerView;
    DBBooking dbBooking;
    BookingAdapter bookingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_tour);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.booking_tour);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbBooking = new DBBooking(this);
        loadData();
        floatingActionBtn_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingTour.this, FormBookingTour.class);
                startActivity(intent);
            }
        });
    }
    private void loadData() {
        try {
            bookingAdapter = new BookingAdapter(BookingTour.this,  dbBooking.getListBooking());
            recyclerView.setAdapter(bookingAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(BookingTour.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl() {
        floatingActionBtn_add_booking = findViewById(R.id.floatingActionBtn_add_booking);
        recyclerView = findViewById(R.id.recyclerView_booking);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(BookingTour.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}