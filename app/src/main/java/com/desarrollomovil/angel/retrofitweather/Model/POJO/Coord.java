package com.desarrollomovil.angel.retrofitweather.Model.POJO;

/**
 * Created by angel on 31/01/2017.
 */
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        public class Coord {

            @SerializedName("lon")
            @Expose
            public Double lon;
            @SerializedName("lat")
            @Expose
            public Double lat;

            public Double getLon() {
                return lon;
            }

            public Double getLat() {
                return lat;
            }
        }
