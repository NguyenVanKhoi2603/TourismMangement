package com.example.tourismmanagement.InterFace.Province;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomAdapter;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.InterFace.Customer.CustomerActivity;
import com.example.tourismmanagement.InterFace.MainActivity;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProvinceActivity extends AppCompatActivity {
    FloatingActionButton floatbtn_add;
    RecyclerView recyclerView_province;
    DBProvince dbProvince;
    ArrayList<ProvinceModel> data_province = new ArrayList<>();
    CustomAdapter customAdapter;
    boolean lang = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.province);
        setControl();
        setEvent();
    }

    private void setEvent() {

        dbProvince = new DBProvince(ProvinceActivity.this);
        loadData();

        floatbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProvinceActivity.this, AddProvinceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        try {
            customAdapter = new CustomAdapter(ProvinceActivity.this, dbProvince.getList());
            recyclerView_province.setAdapter(customAdapter);
            recyclerView_province.setLayoutManager(new LinearLayoutManager(ProvinceActivity.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setControl() {
        floatbtn_add = findViewById(R.id.floatingActionBtn_add_province);
        recyclerView_province = findViewById(R.id.recyclerView_province);
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
        //getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(ProvinceActivity.this, MainActivity.class);
        startActivity(intent);
//        onBackPressed();
//        switch (item.getItemId()) {
//            case R.id.quocKy:
//
//                lang = !lang;
//                if (lang) {
//                    item.setIcon(R.drawable.vn);
//                    Toast.makeText(ProvinceActivity.this, "Việt Nam", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    item.setIcon(R.drawable.usa);
//                    Toast.makeText(ProvinceActivity.this, "USA", Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
        return super.onOptionsItemSelected(item);
    }
}