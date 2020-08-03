package com.example.tourismmanagement.InterFace.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddCustomerActivity extends AppCompatActivity {
    Button btn_c_AddCustomer, btn_c_cancel;
    ImageButton button_c_choose, btn_chooseImg, btn_TakeImg;
    ImageView imgavatar;
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
    final int REQUEST_CODE_GALLERY = 999;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
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

        txt_c_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 10){
                    int ic = R.drawable.check_false;
                    txt_c_phoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic, 0);
                }
                if (charSequence.length() == 11 || charSequence.length() == 10){
                    char checkTrue = 0;
                    if (Character.toString(charSequence.charAt(0)).equals("0")){
                        int ic = R.drawable.check_true;
                        txt_c_phoneNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, ic, 0);
                        txtIL_c_phoneNumber.setError("");
                    } else {
                        txtIL_c_phoneNumber.setError("start 0 and number phone equal 10 numbers!");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddCustomerActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btn_TakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
                    customerModel.setImgavatar(imageViewToByte(imgavatar));
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

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgavatar.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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
        imgavatar  = findViewById(R.id.img_c_profile_add);
        btn_chooseImg  = findViewById(R.id.btn_chooseImg_c);
        btn_TakeImg  = findViewById(R.id.btn_TakeImg_c);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}