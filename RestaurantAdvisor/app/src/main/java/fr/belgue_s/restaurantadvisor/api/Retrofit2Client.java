package fr.belgue_s.restaurantadvisor.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {

    private static Retrofit api = null;

    public static Retrofit getRetrofit2Client() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.43/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return api;
    }
}