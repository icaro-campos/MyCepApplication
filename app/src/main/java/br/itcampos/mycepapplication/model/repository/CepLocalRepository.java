package br.itcampos.mycepapplication.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.itcampos.mycepapplication.model.ViaCepResponse;
import br.itcampos.mycepapplication.model.data.dao.CepDao;
import br.itcampos.mycepapplication.model.data.database.CepDatabase;
import br.itcampos.mycepapplication.model.data.entity.CepEntity;

public class CepLocalRepository {
    private final CepDao cepDao;
    private final ExecutorService executorService;
    private final CepRemoteRepository remoteRepository;

    public CepLocalRepository(Application application) {
        CepDatabase database = CepDatabase.getDatabase(application);
        this.cepDao = database.cepDao();
        this.executorService = Executors.newSingleThreadExecutor();
        this.remoteRepository = new CepRemoteRepository();
    }

    public LiveData<CepEntity> getCepDetails(String cep) {
        return cepDao.getCepDetails(cep);
    }

    public void insertCepDetails(ViaCepResponse viaCepResponse) {
        executorService.execute(() -> {
            CepEntity cepEntity = new CepEntity();
            cepEntity.setCep(viaCepResponse.getCep());
            cepEntity.setAddress(viaCepResponse.getAddress());
            cepEntity.setComplement(viaCepResponse.getComplement());
            cepEntity.setNeighborhood(viaCepResponse.getNeighborhood());
            cepEntity.setCity(viaCepResponse.getCity());
            cepEntity.setState(viaCepResponse.getState());
            cepDao.insert(cepEntity);
        });
    }
}
