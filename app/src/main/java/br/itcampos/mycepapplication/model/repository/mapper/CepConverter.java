package br.itcampos.mycepapplication.model.repository.mapper;

import br.itcampos.mycepapplication.model.ApiCepResponse;
import br.itcampos.mycepapplication.model.AwesomeApiCepResponse;
import br.itcampos.mycepapplication.model.ViaCepResponse;

public class CepConverter {

    public static ViaCepResponse convertToViaCep(ApiCepResponse apiCepResponse) {
        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep(apiCepResponse != null ? apiCepResponse.getCep() : "");
        viaCepResponse.setAddress(apiCepResponse != null ? apiCepResponse.getAddress() : "");
        viaCepResponse.setNeighborhood(apiCepResponse != null ? apiCepResponse.getNeighborhood() : "");
        viaCepResponse.setCity(apiCepResponse != null ? apiCepResponse.getCity() : "");
        viaCepResponse.setState(apiCepResponse != null ? apiCepResponse.getState() : "");
        return viaCepResponse;
    }

    public static ViaCepResponse convertToViaCep(AwesomeApiCepResponse awesomeApiCepResponse) {
        ViaCepResponse viaCepResponse = new ViaCepResponse();
        viaCepResponse.setCep(awesomeApiCepResponse != null ? awesomeApiCepResponse.getCep() : "");
        viaCepResponse.setAddress(awesomeApiCepResponse != null ? awesomeApiCepResponse.getAddress() : "");
        viaCepResponse.setComplement(awesomeApiCepResponse != null ? awesomeApiCepResponse.getComplement() : "");
        viaCepResponse.setNeighborhood(awesomeApiCepResponse != null ? awesomeApiCepResponse.getNeighborhood() : "");
        viaCepResponse.setCity(awesomeApiCepResponse != null ? awesomeApiCepResponse.getCity() : "");
        viaCepResponse.setState(awesomeApiCepResponse != null ? awesomeApiCepResponse.getState() : "");
        return viaCepResponse;
    }
}
