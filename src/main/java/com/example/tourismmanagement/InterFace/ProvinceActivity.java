package com.example.tourismmanagement.InterFace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomAdapter;
import com.example.tourismmanagement.DataBase.DBHelper;
import com.example.tourismmanagement.DataBase.DBProvince;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        setControl();
        setEvent();
    }

    private void setEvent() {

        recyclerView_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
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
}