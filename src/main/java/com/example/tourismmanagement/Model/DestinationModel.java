package com.example.tourismmanagement.Model;

import java.util.Arrays;

public class DestinationModel {
    String des_code, des_name, des_address, des_province, des_description;
    byte[] des_image;

    public String getDes_code() {
        return des_code;
    }

    public void setDes_code(String des_code) {
        this.des_code = des_code;
    }

    public String getDes_name() {
        return des_name;
    }

    public void setDes_name(String des_name) {
        this.des_name = des_name;
    }

    public String getDes_address() {
        return des_address;
    }

    public void setDes_address(String des_address) {
        this.des_address = des_address;
    }

    public String getDes_province() {
        return des_province;
    }

    public void setDes_province(String des_province) {
        this.des_province = des_province;
    }

    public String getDes_description() {
        return des_description;
    }

    public void setDes_description(String des_description) {
        this.des_description = des_description;
    }

    public byte[] getDes_image() {
        return des_image;
    }

    public void setDes_image(byte[] des_image) {
        this.des_image = des_image;
    }

    @Override
    public String toString() {
        return "DestinationModel{" +
                "des_code='" + des_code + '\'' +
                ", des_name='" + des_name + '\'' +
                ", des_address='" + des_address + '\'' +
                ", des_province='" + des_province + '\'' +
                ", des_description='" + des_description + '\'' +
                ", des_image=" + Arrays.toString(des_image) +
                '}';
    }
}
