package br.itcampos.mycepapplication.model.repository;

import static br.itcampos.mycepapplication.model.repository.mapper.CepConverter.convertToViaCep;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.itcampos.mycepapplication.model.ApiCepResponse;
import br.itcampos.mycepapplication.model.AwesomeApiCepResponse;
import br.itcampos.mycepapplication.model.ViaCepResponse;
import br.itcampos.mycepapplication.model.repository.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepRemoteRepository {
    private final ApiService viaCepApiService;
    private final ApiService apiCepApiService;
    private final ApiService awesomeApiService;

    public CepRemoteRepository() {
        Retrofit viaCepRetrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        viaCepApiService = viaCepRetrofit.create(ApiService.class);

        Retrofit apiCepRetrofit = new Retrofit.Builder()
                .baseUrl("https://cdn.apicep.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiCepApiService = apiCepRetrofit.create(ApiService.class);

        Retrofit awesomeApiRetrofit = new Retrofit.Builder()
                .baseUrl("https://cep.awesomeapi.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        awesomeApiService = awesomeApiRetrofit.create(ApiService.class);
    }

    public LiveData<ViaCepResponse> getCepDetailsFromViaCep(String cep) {
        MutableLiveData<ViaCepResponse> data = new MutableLiveData<>();
        MutableLiveData<Object> internalData = new MutableLiveData<>();

        viaCepApiService.getCepDetailsFromViaCep(cep).enqueue(new Callback<ViaCepResponse>() {
            @Override
            public void onResponse(@NonNull Call<ViaCepResponse> call, @NonNull Response<ViaCepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ViaCepResponse viaCep = response.body();
                    viaCep.setSource("ViaCep");
                    data.setValue(viaCep);
                } else {
                    getCepDetailsFromApiCep(cep, internalData);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ViaCepResponse> call, @NonNull Throwable t) {
                getCepDetailsFromApiCep(cep, internalData);
            }
        });

        internalData.observeForever(response -> {
            if (response instanceof ViaCepResponse) {
                data.setValue((ViaCepResponse) response);
            } else if (response == null) {
                getCepDetailsFromAwesomeApi(cep, internalData);
            }
        });
        return data;
    }

    private void getCepDetailsFromApiCep(String cep, MutableLiveData<Object> data) {
        apiCepApiService.getCepDetailsFromApiCep(cep).enqueue(new Callback<ApiCepResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiCepResponse> call, @NonNull Response<ApiCepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiCepResponse apiCepResponse = response.body();
                    ViaCepResponse viaCepResponse = convertToViaCep(apiCepResponse);
                    viaCepResponse.setSource("ApiCep");
                    data.setValue(viaCepResponse);
                } else {
                    getCepDetailsFromAwesomeApi(cep, data);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiCepResponse> call, @NonNull Throwable t) {
                getCepDetailsFromAwesomeApi(cep, data);
            }
        });
    }

    private void getCepDetailsFromAwesomeApi(String cep, MutableLiveData<Object> data) {
        awesomeApiService.getCepDetailsFromAwesomeApi(cep).enqueue(new Callback<AwesomeApiCepResponse>() {
            @Override
            public void onResponse(@NonNull Call<AwesomeApiCepResponse> call, @NonNull Response<AwesomeApiCepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AwesomeApiCepResponse awesomeApiCepResponse = response.body();
                    ViaCepResponse viaCepResponse = convertToViaCep(awesomeApiCepResponse);
                    viaCepResponse.setSource("AwesomeApi");
                    data.setValue(viaCepResponse);
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AwesomeApiCepResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
    }
}
