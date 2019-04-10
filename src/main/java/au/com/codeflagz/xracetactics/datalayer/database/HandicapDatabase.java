package au.com.codeflagz.xracetactics.datalayer.database;

import android.content.Context;
import android.os.AsyncTask;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import au.com.codeflagz.xracetactics.datalayer.dao.HandicapDao;
import au.com.codeflagz.xracetactics.datalayer.dao.RaceDao;
import au.com.codeflagz.xracetactics.model.Handicap;
import au.com.codeflagz.xracetactics.model.Race;

@Database(entities = {Handicap.class, Race.class}, version = 4)
public abstract class HandicapDatabase extends RoomDatabase {
    public static HandicapDatabase instance;
    public abstract HandicapDao handicapDao();
    public abstract RaceDao raceDao();

    public static synchronized HandicapDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HandicapDatabase.class, "Handicap_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callBack)
                    .build();
        }
        return instance;

    }
    private static RoomDatabase.Callback callBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void,Void>{
    private HandicapDao handicapDao;
    private RaceDao raceDao;

        private PopulateDbAsyncTask(HandicapDatabase db){
            handicapDao=db.handicapDao();
            raceDao=db.raceDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            handicapDao.insert(new Handicap("Bluebottle", 1,1.1));
            handicapDao.insert(new Handicap("Kayimai", 1,1.076));
            handicapDao.insert(new Handicap("Phoenix", 1,1.03));

            raceDao.insert(new Race(1,1.01, 11,15 ));

           return null;
        }
    }

}
