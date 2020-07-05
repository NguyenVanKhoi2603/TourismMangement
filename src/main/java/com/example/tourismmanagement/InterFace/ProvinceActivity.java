package com.example.tourismmanagement.InterFace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomAdapter;
import com.example.tourismmanagement.DataBase.DBHelper;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.Helper.HelperSwipe;
import com.example.tourismmanagement.Helper.MyButtonClickListener;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
        recyclerView_province.setHasFixedSize(true);

        HelperSwipe helperSwipe = new HelperSwipe(this, recyclerView_province, 200) {

            @Override
            public void intantiateMyButton(RecyclerView.ViewHolder viewHolder, List<HelperSwipe.MyButton> buffect) {
                buffect.add(new MyButton(ProvinceActivity.this, "delete", 70, R.drawable.ic_delete,
                        Color.parseColor("#FF3C30"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {
                        Toast.makeText(getApplicationContext(), "deleteclick", Toast.LENGTH_LONG).show();
                    }
                }));

                buffect.add(new MyButton(ProvinceActivity.this, "Update", 30, R.drawable.ic_edit,
                        Color.parseColor("#FF9502"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {

                        Toast.makeText(getApplicationContext(), "Update click", Toast.LENGTH_LONG).show();
                    }
                }));
            }
        };
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
        } catch (Exception ex) {
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