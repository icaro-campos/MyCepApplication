package br.itcampos.mycepapplication.model.repository.service;

import br.itcampos.mycepapplication.model.ApiCepResponse;
import br.itcampos.mycepapplication.model.AwesomeApiCepResponse;
import br.itcampos.mycepapplication.model.ViaCepResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("ws/{cep}/json/")
    Call<ViaCepResponse> getCepDetailsFromViaCep(@Path("cep") String cep);

    @GET("file/apicep/{code}.json")
    Call<ApiCepResponse> getCepDetailsFromApiCep(@Path("code") String cep);

    @GET("json/{cep}")
    Call<AwesomeApiCepResponse> getCepDetailsFromAwesomeApi(@Path("cep") String cep);
}
