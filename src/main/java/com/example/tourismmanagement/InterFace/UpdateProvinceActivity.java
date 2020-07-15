package com.example.tourismmanagement.InterFace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class UpdateProvinceActivity extends AppCompatActivity {
    EditText txtP_code, txtP_name;
    Spinner spP_Religion;
    ProvinceModel provinceModel;
    Button btnUpdateProvince, btnDeleteProvince;
    ArrayList<String> data_religion = new ArrayList<>();
    ArrayList<ProvinceModel> data_province = new ArrayList<>();
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_province);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.update_province);
        setControl();
        setEvent();
    }

    //
    private void setEvent() {
        final DBProvince dbProvince = new DBProvince(this);

        initializeSpinner();
        getData(dbProvince);
        btnDeleteProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProvinceActivity.this);
                builder.setTitle("Delete " + txtP_name.getText().toString() + " ?");
                builder.setMessage("Are you sure you want to delete " + txtP_name.getText().toString() + " ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbProvince.deleteProvince(code);

                        Intent intent = new Intent(UpdateProvinceActivity.this, ProvinceActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
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
        });

        btnUpdateProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                provinceModel = new ProvinceModel();
                try {

                    provinceModel.setP_code(code);
                    provinceModel.setP_name(txtP_name.getText().toString());
                    if (spP_Religion.getSelectedItem().equals("Miền bắc")) {
                        provinceModel.setP_regions("Miền bắc");
                    } else {
                        if (spP_Religion.getSelectedItem().equals("Miền Trung")) {
                            provinceModel.setP_regions("Miền Trung");
                        } else {
                            provinceModel.setP_regions("Miền Nam");
                        }
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "loi getdata model", Toast.LENGTH_SHORT).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProvinceActivity.this);
                builder.setTitle("Update " + txtP_name.getText().toString() + " ?");
                builder.setMessage("Are you sure you want to Update " + provinceModel.getP_code() + " ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbProvince.updateProvince(provinceModel);
                        Log.d("province", provinceModel.toString());
                        Intent intent = new Intent(UpdateProvinceActivity.this, ProvinceActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
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
        });

    }

    private void getData(DBProvince dbProvince) {
        try {
            code = getIntent().getExtras().getString("p_code");
            data_province = dbProvince.getDataByCode(code);
            txtP_code.setText(data_province.get(0).getP_code());
            txtP_name.setText(data_province.get(0).getP_name());
            if (data_province.get(0).getP_regions().equals("Miền bắc")) {
                spP_Religion.setSelection(0);
            }
            if (data_province.get(0).getP_regions().equals("Miền Trung")) {
                spP_Religion.setSelection(1);
            }
            if (data_province.get(0).getP_regions().equals("Miền Nam")) {
                spP_Religion.setSelection(2);
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "loi" + code, Toast.LENGTH_SHORT).show();
        }
    }

//    public ProvinceModel getdataToModel() {
//        provinceModel = new ProvinceModel();
//        try {
//
//            provinceModel.setP_code(data_province.get(0).getP_code());
//            provinceModel.setP_name(txtP_name.getText().toString());
//            if (spP_Religion.getSelectedItem().equals("Miền bắc")) {
//                provinceModel.setP_regions("Miền bắc");
//            } else {
//                if (spP_Religion.getSelectedItem().equals("Miền Trung")) {
//                    provinceModel.setP_regions("Miền Trung");
//                } else {
//                    provinceModel.setP_regions("Miền Nam");
//                }
//            }
//        }catch (Exception ex){
//            Toast.makeText(getApplicationContext(), "loi getdata model" , Toast.LENGTH_SHORT).show();
//        }
//        return provinceModel;
//
//    }

    private void initializeSpinner() {
        data_religion.add("Miền bắc");
        data_religion.add("Miền Trung");
        data_religion.add("Miền Nam");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data_religion);
        spP_Religion.setAdapter(adapter);
    }

    private void setControl() {
        txtP_code = findViewById(R.id.editText_p_code_update);
        txtP_name = findViewById(R.id.editText_p_name_update);
        spP_Religion = findViewById(R.id.spinner_p_religion_update);
        btnDeleteProvince = findViewById(R.id.btn_delete_province);
        btnUpdateProvince = findViewById(R.id.btn_update_province);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}