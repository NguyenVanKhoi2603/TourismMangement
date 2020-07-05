package com.example.tourismmanagement.Model;

public class CustomerModel {
    String c_code, c_fullname, c_sex, c_numberphone, c_dayofbirth, c_gmail, c_address;

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getC_fullname() {
        return c_fullname;
    }

    public void setC_fullname(String c_fullname) {
        this.c_fullname = c_fullname;
    }

    public String getC_sex() {
        return c_sex;
    }

    public void setC_sex(String c_sex) {
        this.c_sex = c_sex;
    }

    public String getC_numberphone() {
        return c_numberphone;
    }

    public void setC_numberphone(String c_numberphone) {
        this.c_numberphone = c_numberphone;
    }

    public String getC_dayofbirth() {
        return c_dayofbirth;
    }

    public void setC_dayofbirth(String c_dayofbirth) {
        this.c_dayofbirth = c_dayofbirth;
    }

    public String getC_gmail() {
        return c_gmail;
    }

    public void setC_gmail(String c_gmail) {
        this.c_gmail = c_gmail;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "c_code='" + c_code + '\'' +
                ", c_fullname='" + c_fullname + '\'' +
                ", c_sex='" + c_sex + '\'' +
                ", c_numberphone='" + c_numberphone + '\'' +
                ", c_dayofbirth='" + c_dayofbirth + '\'' +
                ", c_gmail='" + c_gmail + '\'' +
                ", c_address='" + c_address + '\'' +
                '}';
    }
}

