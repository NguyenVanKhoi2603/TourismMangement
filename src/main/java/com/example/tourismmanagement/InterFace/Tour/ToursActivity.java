package com.example.tourismmanagement.InterFace.Tour;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismmanagement.Adapter.DestinationAdapter;
import com.example.tourismmanagement.Adapter.TourAdapter;
import com.example.tourismmanagement.DataBase.DBDestination;
import com.example.tourismmanagement.DataBase.DBTours;
import com.example.tourismmanagement.InterFace.Destination.Destination;
import com.example.tourismmanagement.InterFace.MainActivity;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ToursActivity extends AppCompatActivity {

    FloatingActionButton floatingActionBtn_add_tour;
    RecyclerView recyclerView;
    DBTours dbTours;
    TourAdapter tourAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.tourList);

        setControl();
        setEvent();
    }

    private void setEvent() {
        dbTours = new DBTours(ToursActivity.this);
        loadData();
        floatingActionBtn_add_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToursActivity.this, AddTour.class);
                startActivity(intent);
            }
        });

    }
    private void loadData() {
        try {
            tourAdapter = new TourAdapter(ToursActivity.this,  dbTours.getListTour());
            recyclerView.setAdapter(tourAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(ToursActivity.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setControl() {
        floatingActionBtn_add_tour = findViewById(R.id.floatingActionBtn_add_tour);
        recyclerView = findViewById(R.id.recyclerView_tour);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ToursActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}