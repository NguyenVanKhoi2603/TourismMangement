package com.example.tourismmanagement.Model;

import java.util.Arrays;

public class TourModel {
    String tour_id, tour_name, tour_destination, tour_time, tour_vehicle, tour_departure, tour_info;
    int tour_price;
    byte[] img_destination;

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getTour_destination() {
        return tour_destination;
    }

    public void setTour_destination(String tour_destination) {
        this.tour_destination = tour_destination;
    }

    public String getTour_time() {
        return tour_time;
    }

    public void setTour_time(String tour_time) {
        this.tour_time = tour_time;
    }

    public String getTour_vehicle() {
        return tour_vehicle;
    }

    public void setTour_vehicle(String tour_vehicle) {
        this.tour_vehicle = tour_vehicle;
    }

    public String getTour_departure() {
        return tour_departure;
    }

    public void setTour_departure(String tour_departure) {
        this.tour_departure = tour_departure;
    }

    public String getTour_info() {
        return tour_info;
    }

    public void setTour_info(String tour_info) {
        this.tour_info = tour_info;
    }

    public int getTour_price() {
        return tour_price;
    }

    public void setTour_price(int tour_price) {
        this.tour_price = tour_price;
    }

    public byte[] getImg_destination() {
        return img_destination;
    }

    public void setImg_destination(byte[] img_destination) {
        this.img_destination = img_destination;
    }

    @Override
    public String toString() {
        return "TourModel{" +
                "tour_id='" + tour_id + '\'' +
                ", tour_name='" + tour_name + '\'' +
                ", tour_destination='" + tour_destination + '\'' +
                ", tour_time='" + tour_time + '\'' +
                ", tour_vehicle='" + tour_vehicle + '\'' +
                ", tour_departure='" + tour_departure + '\'' +
                ", tour_info='" + tour_info + '\'' +
                ", tour_price=" + tour_price +
                ", img_destination=" + Arrays.toString(img_destination) +
                '}';
    }
}
