package com.example.tourismmanagement.InterFace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourismmanagement.InterFace.BookingTour.BookingTour;
import com.example.tourismmanagement.InterFace.Customer.CustomerActivity;
import com.example.tourismmanagement.InterFace.Destination.Destination;
import com.example.tourismmanagement.InterFace.Province.ProvinceActivity;
import com.example.tourismmanagement.InterFace.Tour.ToursActivity;
import com.example.tourismmanagement.R;

public class MainActivity extends AppCompatActivity {
    CardView cardViewbtn_tour, cardViewbtn_customer, cardViewbtn_province, cardViewbtn_tourismAtt, cardViewbtn_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {


        cardViewbtn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  CustomerActivity.class);
                startActivity(intent);
            }
        });
        cardViewbtn_tourismAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  Destination.class);
                startActivity(intent);
            }
        });
        cardViewbtn_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,  ToursActivity.class);
                startActivity(intent);
            }
        });
        cardViewbtn_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProvinceActivity.class);
                startActivity(intent);
            }
        });
        cardViewbtn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookingTour.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        cardViewbtn_customer = findViewById(R.id.cardView_customer);
        cardViewbtn_province = findViewById(R.id.cardView_province);
        cardViewbtn_tour = findViewById(R.id.cardView_tour);
        cardViewbtn_tourismAtt = findViewById(R.id.cardView_tourism_attr);
        cardViewbtn_booking = findViewById(R.id.cardView_booking);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.floating_content_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.f_delete:
                Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.f_update:
                Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.notification);
                builder.setMessage(R.string.do_you_want_to_exit);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}