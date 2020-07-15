package com.example.tourismmanagement.InterFace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomAdapter;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;

import java.util.ArrayList;

public class AddProvinceActivity extends AppCompatActivity {
    EditText txtP_code, txtP_name;
    Spinner spP_Religion;
    Button btnAddProvince;
    DBProvince dbProvince;
    ArrayList<String> data_religion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_province);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.addprovince);
        setControl();
        setEvent();
    }

    private void setEvent() {
        initializeSpinner();
        btnAddProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtP_code.length() != 0 && txtP_name.length() != 0) {
                    ProvinceModel province = new ProvinceModel();
                    DBProvince dbProvince = new DBProvince(getApplicationContext());
                    province.setP_code(txtP_code.getText().toString());
                    province.setP_name(txtP_name.getText().toString());
                    if (spP_Religion.getSelectedItem().equals("Miền bắc")) {
                        province.setP_regions("Miền bắc");
                    } else {
                        if (spP_Religion.getSelectedItem().equals("Miền Trung")) {
                            province.setP_regions("Miền Trung");
                        } else {
                            province.setP_regions("Miền Nam");
                        }
                    }
                    dbProvince.addProvince(province);
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddProvinceActivity.this, ProvinceActivity.class);
                    startActivity(intent);
                    //onRestart();



                } else {
                    if (txtP_name.length() == 0) {
                        txtP_name.setError(R.string.set_error_edittext_empty + "");
                    }
                    if (txtP_code.length() == 0) {
                        txtP_code.setError(R.string.set_error_edittext_empty + "");
                    }
                }

            }
        });


    }

    private void initializeSpinner() {
        data_religion.add("Miền bắc");
        data_religion.add("Miền Trung");
        data_religion.add("Miền Nam");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data_religion);
        spP_Religion.setAdapter(adapter);
    }

    private void setControl() {
        txtP_code = findViewById(R.id.editText_p_code);
        txtP_name = findViewById(R.id.editText_p_name);
        spP_Religion = findViewById(R.id.spinner_p_religion);
        btnAddProvince = findViewById(R.id.btn_add_province);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}