package com.example.tourismmanagement.InterFace.Destination;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.tourismmanagement.DataBase.DBDestination;
import com.example.tourismmanagement.DataBase.DBProvince;
import com.example.tourismmanagement.InterFace.Customer.AddCustomerActivity;
import com.example.tourismmanagement.InterFace.Customer.CustomerActivity;
import com.example.tourismmanagement.InterFace.Province.ProvinceActivity;
import com.example.tourismmanagement.InterFace.Province.UpdateProvinceActivity;
import com.example.tourismmanagement.InterFace.Tour.AddTour;
import com.example.tourismmanagement.Model.DestinationModel;
import com.example.tourismmanagement.Model.ProvinceModel;
import com.example.tourismmanagement.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddDestination extends AppCompatActivity {
    ImageView imageV_destination;
    Button btn_d_add, btn_d_cancel, btn_d_delete;
    TextInputEditText IET_d_code, IET_d_name, IET_d_address, IET_d_description;
    TextInputLayout IL_d_code, IL_d_name, IL_d_address, IL_d_description;
    Spinner sp_province;
    String code = "";
    ArrayList<String> data_spinner = new ArrayList<>();
    ArrayList<ProvinceModel> data_province = new ArrayList<>();
    ArrayList<DestinationModel> data_destination = new ArrayList<>();
    DBProvince dbProvince;
    DestinationModel destinationModel;
    DBDestination dbDestination;
    final int REQUEST_CODE_GALLERY = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setControl();
        setEvent();
        if (!code.equals("")){
            actionBar.setTitle(R.string.update);
        } else {
            actionBar.setTitle(R.string.desAdd);
        }

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void setEvent() {

        imageV_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddDestination.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        getDataByCode();
        getDataSpinner();
        if (!code.equals("")) {
            try {
                IET_d_code.setText(data_destination.get(0).getDes_code());
                IET_d_name.setText(data_destination.get(0).getDes_name());
                IET_d_address.setText(data_destination.get(0).getDes_address());
                IET_d_description.setText(data_destination.get(0).getDes_description());
                byte[] destination_img = data_destination.get(0).getDes_image();
                Bitmap bitmap = BitmapFactory.decodeByteArray(destination_img, 0, destination_img.length);
                imageV_destination.setImageBitmap(bitmap);
                int i = 0;
                i = getIndex(sp_province, data_destination.get(0).getDes_province());
                sp_province.setSelection(i);
            } catch (Exception ex){

            }
        }
        btn_d_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_d_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IET_d_code.getText().length() > 0 && IET_d_name.getText().length() > 0 && IET_d_address.getText().length() > 0 && IET_d_description.getText().length() > 0) {
                    DestinationModel destinationModel = new DestinationModel();
                    destinationModel.setDes_code(IET_d_code.getText().toString());
                    destinationModel.setDes_name(IET_d_name.getText().toString());
                    destinationModel.setDes_address(IET_d_address.getText().toString());
                    destinationModel.setDes_description(IET_d_description.getText().toString());
                    dbProvince = new DBProvince(getApplicationContext());
                    try {
                        data_province = dbProvince.getDataByName(sp_province.getSelectedItem().toString());
                        destinationModel.setDes_province(data_province.get(0).getP_code());
                        destinationModel.setDes_image(imageViewToByte(imageV_destination));
                        dbDestination = new DBDestination(getApplicationContext());
                        if (!code.equals("")){
                            dbDestination.updateDestination(destinationModel);
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            dbDestination.addDestination(destinationModel);
                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex){
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }



                    Intent intent = new Intent(AddDestination.this, Destination.class);
                    startActivity(intent);
                }
            }
        });

        btn_d_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!code.equals("")){

                }
            }
        });
    }

    private void getDataByCode() {
        try {
            code = getIntent().getExtras().getString("d_code");
            dbDestination = new DBDestination(getApplicationContext());
            Toast.makeText(getApplicationContext(), "code"+ code, Toast.LENGTH_SHORT).show();
            data_destination = dbDestination.getDesByCode(code);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Add new", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataSpinner() {
        dbProvince = new DBProvince(getApplicationContext());
        try {
            data_province = dbProvince.getList();
            for (ProvinceModel item : data_province) {
                data_spinner.add(item.getP_name());
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data_spinner);
            sp_province.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
                imageV_destination.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setControl() {
        imageV_destination = findViewById(R.id.img_d_avatar);
        btn_d_add = findViewById(R.id.btn_d_add);
        btn_d_delete = findViewById(R.id.btn_d_delete);
        btn_d_cancel = findViewById(R.id.btn_d_cancel);
        IET_d_name = findViewById(R.id.edt_d_name);
        IET_d_code = findViewById(R.id.edt_d_code);
        IET_d_address = findViewById(R.id.edt_d_address);
        IET_d_description = findViewById(R.id.edt_d_description);
        IL_d_code = findViewById(R.id.edtIL_d_code);
        sp_province = findViewById(R.id.sp_d_province);
        IL_d_name = findViewById(R.id.edtIL_d_name);
        IL_d_address = findViewById(R.id.edtIL_d_address);
        IL_d_description = findViewById(R.id.edtIL_d_description);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}