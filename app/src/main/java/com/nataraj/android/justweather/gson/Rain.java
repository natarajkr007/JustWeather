package com.nataraj.android.justweather.gson;

import com.google.gson.annotations.SerializedName;

public class Rain {

    @SerializedName("3h")
    private double vol3h;

    public double getVol3h() {
        return vol3h;
    }
}
