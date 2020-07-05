package com.example.tourismmanagement.Model;

public class ProvinceModel {
    String p_code, p_name, p_regions;

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_regions() {
        return p_regions;
    }

    public void setP_regions(String p_regions) {
        this.p_regions = p_regions;
    }

    @Override
    public String toString() {
        return "ProvinceModel{" +
                "p_code='" + p_code + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_regions='" + p_regions + '\'' +
                '}';
    }
}
