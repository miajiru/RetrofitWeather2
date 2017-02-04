package com.desarrollomovil.angel.retrofitweather.Presenter;

import android.graphics.Bitmap;

import com.desarrollomovil.angel.retrofitweather.Model.WeatherModel;
import com.desarrollomovil.angel.retrofitweather.Service.WeatherAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by angel on 30/01/2017.
 */

public class WeatherPresenter {

    private final WeatherPresenterListener mListener;
    Bitmap bmp = null;

    public interface WeatherPresenterListener{
        void weatherReady(WeatherModel weather);
        void weatherError(String message);
    }

    // TODO Llamamos a la Activity a traves del listener (WeatherPresenterListerer)
    public WeatherPresenter(WeatherPresenterListener listener){
        this.mListener = listener;
    }

    public void getWeather(String ciudadPais) {
        // Aquí llamamos al método de countryService, encolamos y implementamos el callback
        Gson gson = new GsonBuilder()
                        .create();
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(WeatherAPI.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

        WeatherAPI weatherApi = retrofit.create(WeatherAPI.class);
        // TODO: obtener weatherModel a través del weatherService
        Call<WeatherModel> call = weatherApi.getWeather(WeatherAPI.KEY, ciudadPais, WeatherAPI.unidades );
        call.enqueue(new Callback<WeatherModel>() {

            @Override
            public void onResponse(Call<WeatherModel> call, Response <WeatherModel> response) {
                if(response.isSuccessful()){//TODO Si todo es correcto enviamos el weatherModel con la respuesta a la Activity
                    WeatherModel respuesta = response.body();//TODO cuerpo de la respuesta
                    mListener.weatherReady(respuesta);

                }else {     //TODO Enviar a UI un mensaje especificando error, se mostrará en un Toast
                    mListener.weatherError(response.message());

                }
            }

            //TODO fallo al obtener el cuerpo de la respuesta, enviamos el mensaje a la activity, mensaje se mostrará en un Toast
            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                t.printStackTrace();
                mListener.weatherError(t.getMessage());

            }
        });
    }

}
