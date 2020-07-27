package com.example.tourismmanagement.InterFace.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddCustomerActivity extends AppCompatActivity {
    Button btn_c_AddCustomer, btn_c_cancel;
    ImageButton button_c_choose;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.addcustomer);
        setControl();
        setEvent();

    }

    private void setEvent() {
        btn_c_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getTimePresent();
        txt_c_BirthOfDay.setText(day + "/" + (month + 1) + "/" + year);
        btn_c_AddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmpty() == true) {
                    CustomerModel customerModel = new CustomerModel();
                    dbCustomer = new DBCustomer(getApplicationContext());
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
                    dbCustomer.addCustomer(customerModel);
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCustomerActivity.this, CustomerActivity.class);
                    startActivity(intent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCustomerActivity.this);
                    builder.setTitle(R.string.notification + "");
                    builder.setMessage(R.string.set_error_edittext_empty + "");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                    setErrorValueEmpty();
                }
            }
        });


        button_c_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimePresent();
                datePickerDialog = new DatePickerDialog(AddCustomerActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
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
        button_c_choose = findViewById(R.id.button_c_chooseDay);
        btn_c_AddCustomer = findViewById(R.id.btn_add_customer);
        txt_c_BirthOfDay = findViewById(R.id.editText_c_birthOfDay);
        txt_c_code = findViewById(R.id.editText_c_code);
        txt_c_name = findViewById(R.id.editText_c_name);
        txt_c_gmail = findViewById(R.id.editText_c_gmail);
        txt_c_address = findViewById(R.id.editText_c_address);
        txt_c_phoneNumber = findViewById(R.id.editText_c_numberPhone);
        rabMale = findViewById(R.id.radioButton_c_male);
        rabFemale = findViewById(R.id.radioButton_c_female);
        rabCustom = findViewById(R.id.radioButton_c_custom);
        txtIL_c_code = findViewById(R.id.layoutEditText_c_code);
        txtIL_c_name = findViewById(R.id.layoutEditText_c_name);
        txtIL_c_gmail = findViewById(R.id.layoutEditText_c_gmail);
        txtIL_c_BirthOfDay = findViewById(R.id.layoutEditText_c_birthOfDay);
        txtIL_c_phoneNumber = findViewById(R.id.layoutEditText_c_nnumberPhone);
        txtIL_c_address = findViewById(R.id.layoutEditText_c_address);
        btn_c_cancel = findViewById(R.id.btn_c_cancel);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}