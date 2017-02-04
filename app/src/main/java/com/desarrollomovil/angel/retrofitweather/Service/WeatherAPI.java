package com.desarrollomovil.angel.retrofitweather.Service;

import com.desarrollomovil.angel.retrofitweather.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by angel on 31/01/2017.
 */

public interface WeatherAPI {

    final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    final String KEY = "341563c5762abeacbb6f9a81736610a3";//TODO mi API_KEY
    String unidades = "metric";//TODO añadiremos en la URL de la petición para que nos devuelva los datos en unidades metricas

    // TODO: Completar
    @GET("weather")
    Call<WeatherModel> getWeather(
            @Query("appid") String key,
            @Query("q") String ciudadPais,
            @Query("units") String unidades);

}
