package com.example.tourismmanagement.InterFace.Destination;

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

import com.example.tourismmanagement.Adapter.CustomerAdapter;
import com.example.tourismmanagement.Adapter.DestinationAdapter;
import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.DataBase.DBDestination;
import com.example.tourismmanagement.InterFace.Customer.CustomerActivity;
import com.example.tourismmanagement.InterFace.MainActivity;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Destination extends AppCompatActivity {

    FloatingActionButton floatbtn_add;
    RecyclerView recyclerView;
    DBDestination dbDestination;
    ArrayList<Destination> data_destination = new ArrayList<>();
    DestinationAdapter destinationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.destination);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbDestination = new DBDestination(Destination.this);
        loadData();
        floatbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Destination.this, AddDestination.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        try {
            destinationAdapter = new DestinationAdapter(Destination.this,  dbDestination.getListDestination());
            recyclerView.setAdapter(destinationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setControl() {
        floatbtn_add = findViewById(R.id.floatingActionBtn_Destination);
        recyclerView = findViewById(R.id.recycleViewDestination);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(Destination.this, MainActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}