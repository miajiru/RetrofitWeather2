package com.desarrollomovil.angel.retrofitweather.Model.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by angel on 31/01/2017.
 */

public class Wind {

    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("deg")
    @Expose
    public Double deg;
    @SerializedName("gust")
    @Expose
    public Double gust;

    public Double getSpeed() {
        return speed;
    }

    public Double getDeg() {
        return deg;
    }

    public Double getGust() {
        return gust;
    }
}
