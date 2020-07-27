package com.example.tourismmanagement.InterFace.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.InterFace.Province.ProvinceActivity;
import com.example.tourismmanagement.InterFace.Province.UpdateProvinceActivity;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateCustomer extends AppCompatActivity {
    Button btn_c_Update, btn_c_delete, btn_c_cancel;
    ImageView imageView_c_profile;
    ImageButton btn_c_chooseDate;
    TextInputEditText txt_c_BirthOfDay, txt_c_code, txt_c_name, txt_c_gmail, txt_c_phoneNumber, txt_c_address;
    TextInputLayout txtIL_c_code, txtIL_c_BirthOfDay, txtIL_c_name, txtIL_c_gmail, txtIL_c_phoneNumber, txtIL_c_address;
    Calendar calendar;
    RadioButton rabMale, rabFemale, rabCustom;
    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    DBCustomer dbCustomer;
    int day = 0;
    int month = 0;
    int year = 0;
    ArrayList<CustomerModel> data_customer = new ArrayList<>();
    String code;
    CustomerModel customerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.info_customer);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbCustomer = new DBCustomer(this);
        getData(dbCustomer);

        btn_c_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_c_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePresent();
                datePickerDialog = new DatePickerDialog(UpdateCustomer.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int iYear, int iMonth, int iDay) {
                txt_c_BirthOfDay.setText(iDay + "/" + (iMonth + 1) + "/" + iYear);
            }
        };

        btn_c_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerModel = new CustomerModel();
                customerModel.setC_code(txt_c_code.getText().toString());
                customerModel.setC_fullname(txt_c_name.getText().toString());
                customerModel.setC_address(txt_c_address.getText().toString());
                customerModel.setC_numberphone(txt_c_phoneNumber.getText().toString());
                customerModel.setC_gmail(txt_c_gmail.getText().toString());
                customerModel.setC_dayofbirth(txt_c_BirthOfDay.getText().toString());
                if (rabMale.isChecked() == true) {
                    customerModel.setC_sex("0");
                } else {
                    if (rabFemale.isChecked() == true) {
                        customerModel.setC_sex("1");
                    } else {
                        customerModel.setC_sex("2");
                    }
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCustomer.this);
                builder.setTitle("Update " + txt_c_name.getText().toString() + " ?");
                builder.setMessage("Are you sure you want to Update " + customerModel.getC_code() + " ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbCustomer.updateCustomer(customerModel);
                        Log.d("updatecustomer", customerModel.toString());
                        Intent intent = new Intent(UpdateCustomer.this, CustomerActivity.class);
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

        btn_c_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCustomer.this);
                builder.setTitle("Delete " + txt_c_name.getText().toString() + " ?");
                builder.setMessage("Are you sure you want to delete " + txt_c_name.getText().toString() + " ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbCustomer.deleteCustomer(code);

                        Intent intent = new Intent(UpdateCustomer.this, CustomerActivity.class);
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

    }
    private void getData(DBCustomer dbCustomer) {
        try {
            code = getIntent().getExtras().getString("c_code");
            data_customer = dbCustomer.getDataByCode(code);
            txt_c_code.setText(data_customer.get(0).getC_code());
            txt_c_name.setText(data_customer.get(0).getC_fullname());
            txt_c_gmail.setText(data_customer.get(0).getC_gmail());
            txt_c_address.setText(data_customer.get(0).getC_address());
            txt_c_phoneNumber.setText(data_customer.get(0).getC_numberphone());
            txt_c_BirthOfDay.setText(data_customer.get(0).getC_dayofbirth());
            String sex = data_customer.get(0).getC_sex();
            if (sex.equals("0")) {
                rabMale.setChecked(true);
                imageView_c_profile.setImageResource(R.drawable.man);
            } else {
                if (sex.equals("1")) {
                    rabFemale.setChecked(true);
                    imageView_c_profile.setImageResource(R.drawable.woman);
                } else {
                    rabCustom.setChecked(true);
                    imageView_c_profile.setImageResource(R.drawable.usa);
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "loi" + code, Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkEmpty() {
        if (txt_c_code.getText().length() != 0 && txt_c_name.getText().length() != 0 && txt_c_BirthOfDay.getText().length() != 0
                && txt_c_phoneNumber.getText().length() != 0 && txt_c_address.getText().length() != 0 && txt_c_gmail.getText().length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void setErrorValueEmpty() {
        if (txt_c_code.getText().length() == 0) {
            txtIL_c_code.setError(R.string.set_error_edittext_empty + "");
        }
        if (txt_c_name.getText().length() == 0) {
            txtIL_c_name.setError(R.string.set_error_edittext_empty + "");
        }
        if (txt_c_BirthOfDay.getText().length() == 0) {
            txtIL_c_BirthOfDay.setError(R.string.set_error_edittext_empty + "");
        }
        if (txt_c_gmail.getText().length() == 0) {
            txtIL_c_gmail.setError(R.string.set_error_edittext_empty + "");
        }
        if (txt_c_phoneNumber.getText().length() == 0) {
            txtIL_c_phoneNumber.setError(R.string.set_error_edittext_empty + "");
        }
        if (txt_c_address.getText().length() == 0) {
            txtIL_c_address.setError(R.string.set_error_edittext_empty + "");
        }
    }

    private void getTimePresent() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }


    private void setControl() {
        btn_c_delete = findViewById(R.id.btn_c_delete);
        btn_c_Update = findViewById(R.id.btn_c_update);
        btn_c_chooseDate = findViewById(R.id.btn_c_chooseDay_update);
        txt_c_code = findViewById(R.id.edt_c_code_update);
        txt_c_name = findViewById(R.id.edt_c_name_update);
        txt_c_gmail = findViewById(R.id.edt_c_gmail_update);
        txt_c_phoneNumber = findViewById(R.id.edt_c_numberPhone_update);
        txt_c_address = findViewById(R.id.edt_c_address_update);
        txt_c_BirthOfDay = findViewById(R.id.edt_c_birthOfDay_update);

        txtIL_c_code = findViewById(R.id.edtLT_c_code_update);
        txtIL_c_name = findViewById(R.id.edtLT_c_name_update);
        txtIL_c_gmail = findViewById(R.id.edtLT_c_gmail_update);
        txtIL_c_address = findViewById(R.id.edtLT_c_address_update);
        txtIL_c_BirthOfDay = findViewById(R.id.edtLT_c_birthOfDay_update);
        txtIL_c_phoneNumber = findViewById(R.id.edtLT_c_numberPhone_update);

        rabCustom = findViewById(R.id.rab_c_custom_update);
        rabFemale = findViewById(R.id.rab_c_female_update);
        rabMale = findViewById(R.id.rab_c_male_update);

        imageView_c_profile = findViewById(R.id.img_c_profile);

        btn_c_cancel = findViewById(R.id.btn_c_cancel_update);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}