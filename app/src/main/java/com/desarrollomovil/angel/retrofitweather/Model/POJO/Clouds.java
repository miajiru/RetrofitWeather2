package com.desarrollomovil.angel.retrofitweather.Model.POJO;

/**
 * Created by angel on 31/01/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    @Expose
    public Integer all;

    public Integer getAll() {
        return all;
    }
}
