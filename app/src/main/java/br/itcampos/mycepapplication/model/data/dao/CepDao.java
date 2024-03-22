package br.itcampos.mycepapplication.model.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import br.itcampos.mycepapplication.model.data.entity.CepEntity;

@Dao
public interface CepDao {

    @Query("SELECT * FROM cep_table WHERE cep = :cep")
    LiveData<CepEntity> getCepDetails(String cep);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CepEntity cep);
}
