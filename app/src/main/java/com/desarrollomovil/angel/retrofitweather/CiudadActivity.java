package com.desarrollomovil.angel.retrofitweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollomovil.angel.retrofitweather.Model.POJO.Weather;
import com.desarrollomovil.angel.retrofitweather.Model.WeatherModel;
import com.desarrollomovil.angel.retrofitweather.Presenter.WeatherPresenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CiudadActivity extends AppCompatActivity implements WeatherPresenter.WeatherPresenterListener{

    WeatherPresenter weatherPresenter;
    private TextView ciudad;
    private TextView temp;
    private TextView humedad;
    private TextView viento;
    private ImageView icono;
    private String ciudadPais;
    private Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);

        ciudad = (TextView)findViewById(R.id.tvCiudad);
        temp = (TextView)findViewById(R.id.tvTemp);
        humedad = (TextView)findViewById(R.id.tvHumedad);
        viento = (TextView)findViewById(R.id.tvViento);
        icono = (ImageView)findViewById(R.id.icono);

        weatherPresenter = new WeatherPresenter(this);

        //TODO ciudad,país pulsada en la lista
        Bundle b = getIntent().getExtras();
        ciudadPais = b.getString("ciudadPais");

        //TODO Petición a la API con la ciudad,país
        weatherPresenter.getWeather(ciudadPais);
    }

    @Override
    public void weatherReady(WeatherModel weather) {
        // aquí obtenemos las actualizaciones gracias a WeatherPresenter y actualizamos el interfaz

        List<Weather> lista= weather.getWeather();
        final String idIcon = lista.get(0).getIcon().toString();//TODO obtenemos el idIcon del icono de la respuesta

        //TODO el proceso de descarga del icon se realizara con un executorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //TODO recibiremos un future con el bitmap
        Future future = executorService.submit(new Callable(){
            public Bitmap call() throws Exception {
                bmp = descargarImagen(idIcon);
                return bmp;
            }
        });

        try {
            //TODO mostramos imagen descargada
            icono.setImageBitmap((Bitmap)future.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //TODO actualizaremos los elementos de la mainActivity
        ciudad.setText(weather.getName().toString());
        temp.setText("MAX "+weather.getMain().getTempMax().toString()+"ºC / MIN "+weather.getMain().getTempMin().toString()+"ºC");
        humedad.setText("HUMEDAD: "+weather.getMain().getHumidity()+"%");
        viento.setText("VIENTO: "+weather.getWind().getSpeed()+"km/h, "+toTextualDescription(weather.getWind().getDeg()));

        //TODO Comprobamos si el executorService
        if(! executorService.isTerminated()){
            executorService.shutdownNow();//TODO forzamos su cierre
        }
    }

    //TODO procesaremos los mensajes de error
    @Override
    public void weatherError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //TODO descargaremos la imagen correspodiente del icon... devolveremos un bitmap
    public Bitmap descargarImagen(String idIcon){

        String urlImagen = "http://openweathermap.org/img/w/" + idIcon + ".png";
        URL urlObject = null;
        try {
            urlObject = new URL(urlImagen);
            InputStream input = urlObject.openStream();     //TODO 2ª conexión, descargar la imagen
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int n;
            byte[] buf = new byte[512];
            while ((n = input.read(buf)) > 0) {
                bos.write(buf, 0, n);
            }
            input.close();//TODO Cerramos flujo de datos
            bos.close();

            byte[] byteImg = bos.toByteArray();
            bmp = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }

    //TODO Método que determina la dirección del viento
    public String toTextualDescription(Double degree){//TODO método sacado de http://stackoverflow.com/questions/36475255/i-have-wind-direction-data-coming-from-openweathermap-api-and-the-data-is-repre
        if (degree>337.5){
            return "Norte";
        }
        if (degree>292.5){
            return "Noroeste";
        }
        if(degree>247.5){
            return "Oeste";
        }
        if(degree>202.5){
            return "Sudoeste";
        }

        if(degree>157.5){
            return "Sur";
        }
        if(degree>122.5){
            return "Sudeste";
        }
        if(degree>67.5){
            return "Este";
        }
        if(degree>22.5){return "Nordeste";}

        return "Norte";
    }

    //TODO recargamos la activity
    public void reloadActivity(View v){
        finish();
        startActivity(getIntent());
    }
}
