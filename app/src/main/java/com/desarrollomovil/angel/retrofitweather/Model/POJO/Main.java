package com.desarrollomovil.angel.retrofitweather.Model.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by angel on 31/01/2017.
 */

    public class Main {

    @SerializedName("temp")
    @Expose
    public Float temp;
    @SerializedName("pressure")
    @Expose
    public Integer pressure;
    @SerializedName("humidity")
    @Expose
    public Integer humidity;
    @SerializedName("temp_min")
    @Expose
    public Float tempMin;
    @SerializedName("temp_max")
    @Expose
    public Float tempMax;

    public Float getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }
}