package com.example.tourismmanagement.Model;

public class BookingModel {

    String bk_id, customer_id, tour_id, bk_dayStart, bk_status, bk_note;

    public String getBk_id() {
        return bk_id;
    }

    public void setBk_id(String bk_id) {
        this.bk_id = bk_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }

    public String getBk_dayStart() {
        return bk_dayStart;
    }

    public void setBk_dayStart(String bk_dayStart) {
        this.bk_dayStart = bk_dayStart;
    }

    public String getBk_status() {
        return bk_status;
    }

    public void setBk_status(String bk_status) {
        this.bk_status = bk_status;
    }

    public String getBk_note() {
        return bk_note;
    }

    public void setBk_note(String bk_note) {
        this.bk_note = bk_note;
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "bk_id='" + bk_id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", tour_id='" + tour_id + '\'' +
                ", bk_dayStart='" + bk_dayStart + '\'' +
                ", bk_status='" + bk_status + '\'' +
                ", bk_note='" + bk_note + '\'' +
                '}';
    }
}
