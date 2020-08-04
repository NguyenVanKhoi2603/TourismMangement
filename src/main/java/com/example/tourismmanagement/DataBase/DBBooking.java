package com.example.tourismmanagement.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tourismmanagement.Model.BookingModel;

import java.util.ArrayList;

public class DBBooking {
    DBHelper dbHelper;

    public DBBooking(Context context) {
        dbHelper = new DBHelper(context);
    }
    public void addBooking(BookingModel bookingModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", bookingModel.getBk_id());
        values.put("customer_id", bookingModel.getCustomer_id());
        values.put("tour_id", bookingModel.getTour_id());
        values.put("book_start_day", bookingModel.getBk_dayStart());
        values.put("booking_status", bookingModel.getBk_status());
        values.put("book_note", bookingModel.getBk_note());
        db.insert("Booking", null, values);
    }

    public ArrayList<BookingModel> getListBooking() {
        ArrayList<BookingModel> data = new ArrayList<>();
        String sql = "select * from Booking";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        do {
            BookingModel bookingModel = new BookingModel();
            bookingModel.setBk_id(cursor.getString(0));
            bookingModel.setCustomer_id(cursor.getString(1));
            bookingModel.setTour_id(cursor.getString(2));
            bookingModel.setBk_dayStart(cursor.getString(3));
            bookingModel.setBk_status(cursor.getString(4));
            bookingModel.setBk_note(cursor.getString(5));
            data.add(bookingModel);
        }
        while (cursor.moveToNext());
        return data;
    }

    public ArrayList<BookingModel> getDataByCode(String code) {
        ArrayList<BookingModel> data = new ArrayList<>();
        String sql = "Select * from Booking where book_id = '" + code + "'";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        try {
            cursor.moveToFirst();
            do {
                BookingModel bookingModel = new BookingModel();
                bookingModel.setBk_id(cursor.getString(0));
                bookingModel.setCustomer_id(cursor.getString(1));
                bookingModel.setTour_id(cursor.getString(2));
                bookingModel.setBk_dayStart(cursor.getString(3));
                bookingModel.setBk_status(cursor.getString(4));
                bookingModel.setBk_note(cursor.getString(5));
                data.add(bookingModel);
            }
            while (cursor.moveToNext());
        } catch (Exception ex) {
        }
        return data;
    }

    public void deleteBooking(String c_Code) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete("Booking", "book_id = '" + c_Code + "'", null);
        db.close();
    }

    public void updateUpdateBooking(BookingModel bookingModel) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", bookingModel.getBk_id());
        values.put("customer_id", bookingModel.getCustomer_id());
        values.put("tour_id", bookingModel.getTour_id());
        values.put("book_start_day", bookingModel.getBk_dayStart());
        values.put("booking_status", bookingModel.getBk_status());
        values.put("book_note", bookingModel.getBk_note());
        db.update("Booking", values, "book_id ='" + bookingModel.getBk_id() + "'", null);
        db.close();
    }


}
