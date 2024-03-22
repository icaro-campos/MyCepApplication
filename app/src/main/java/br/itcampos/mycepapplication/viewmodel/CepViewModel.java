package br.itcampos.mycepapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import br.itcampos.mycepapplication.model.ViaCepResponse;
import br.itcampos.mycepapplication.model.data.database.CepDatabase;
import br.itcampos.mycepapplication.model.data.entity.CepEntity;
import br.itcampos.mycepapplication.model.repository.CepLocalRepository;
import br.itcampos.mycepapplication.model.repository.CepRemoteRepository;

public class CepViewModel extends AndroidViewModel {

    private final CepRemoteRepository remoteRepository;
    private final CepLocalRepository localRepository;

    public CepViewModel(@NonNull Application application) {
        super(application);
        remoteRepository = new CepRemoteRepository();
        localRepository = new CepLocalRepository(application);
    }

    public LiveData<ViaCepResponse> getCepDetailsFromRemote(String cep) {
        return remoteRepository.getCepDetailsFromViaCep(cep);
    }

    public LiveData<CepEntity> getCepDetailsFromLocal(String cep) {
        return localRepository.getCepDetails(cep);
    }

    public void insertCepDetails(ViaCepResponse cep) {
        localRepository.insertCepDetails(cep);
    }
}
