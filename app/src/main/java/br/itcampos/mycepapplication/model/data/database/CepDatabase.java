package br.itcampos.mycepapplication.model.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.itcampos.mycepapplication.model.data.dao.CepDao;
import br.itcampos.mycepapplication.model.data.entity.CepEntity;

@Database(entities = {CepEntity.class}, version = 1, exportSchema = false)
public abstract class CepDatabase extends RoomDatabase {
    public abstract CepDao cepDao();

    private static volatile CepDatabase INSTANCE;

    public static CepDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CepDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CepDatabase.class, "cep_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
