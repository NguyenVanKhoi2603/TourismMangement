package com.example.tourismmanagement.InterFace.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tourismmanagement.Adapter.CustomerAdapter;
import com.example.tourismmanagement.DataBase.DBCustomer;
import com.example.tourismmanagement.Model.CustomerModel;
import com.example.tourismmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    FloatingActionButton floatbtn_add;
    RecyclerView recyclerView_customer;
    DBCustomer dbCustomer;
    ArrayList<CustomerModel> data_customer = new ArrayList<>();
    CustomerAdapter customerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dbCustomer = new DBCustomer(CustomerActivity.this);
        loadData();
        floatbtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        floatbtn_add = findViewById(R.id.floatingActionBtn_Customer);
        recyclerView_customer = findViewById(R.id.recycleViewCustomer);
    }

    private void loadData() {
        try {
            customerAdapter = new CustomerAdapter(CustomerActivity.this,  dbCustomer.getListCustomer());
            recyclerView_customer.setAdapter(customerAdapter);
            recyclerView_customer.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Chua co du lieu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_item, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //customerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}