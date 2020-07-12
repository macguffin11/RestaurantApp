package com.example.restaurantapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.apis.ApiInterface;
import com.example.restaurantapp.models.Search;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRepository {
    private static final String BASE_URL = "https://developers.zomato.com/api/v2.1/";

    private ApiInterface apiInterface;
    private MutableLiveData<Search> searchMutableLiveData;

    public SearchRepository() {
        searchMutableLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(interceptor).build();

        apiInterface = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);

    }

    public void search(String keyword, String apiKey) {
        Call<Search> listCall = apiInterface.search(keyword, apiKey);
        Log.d("TAG", "search: "+(listCall == null));
        listCall.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            searchMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        searchMutableLiveData.setValue(null);
                        Log.d("onFailure", "onFailure: null");
                        Log.d("onFailure", "onFailure: "+t.getMessage());
                    }
                });
    }

    public MutableLiveData<Search> getSearchMutableLiveData() {
        return searchMutableLiveData;
    }
}
