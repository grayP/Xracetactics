package au.com.codeflagz.xracetactics.datalayer.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import au.com.codeflagz.xracetactics.datalayer.dao.HandicapDao;
import au.com.codeflagz.xracetactics.datalayer.database.HandicapDatabase;
import au.com.codeflagz.xracetactics.model.Handicap;

public class HandicapRepository {
    private HandicapDao handicapDao;
    private LiveData<List<Handicap>> allHandicaps;

    public HandicapRepository(Application application) {
        HandicapDatabase handicapDatabase = HandicapDatabase.getInstance(application);
        handicapDao = handicapDatabase.handicapDao();
        allHandicaps=handicapDao.getAllHandicaps();
    }



    public void insert(Handicap handicap) {
        new insertHandicapAsyncTask(handicapDao).execute(handicap);
    }
    public void update(Handicap handicap) {
        new updateHandicapAsyncTask(handicapDao).execute(handicap);
    }
    public void delete(Handicap handicap) {
        new deleteHandicapAsyncTask(handicapDao).execute(handicap);
    }
    public void deleteAll(){
        new deleteAllHandicapAsyncTask(handicapDao).execute();
    }
    public Handicap getHandicap(int id){
       return handicapDao.getHandicap(id);
    }
    public LiveData<List<Handicap>> getHandicaps(){
        return allHandicaps;
    }
    public LiveData<List<Handicap>> getHandicapsForRace(int raceNum){return handicapDao.gethandicapsForRace(raceNum);}

    private static class insertHandicapAsyncTask extends AsyncTask<Handicap, Void, Void> {
        private HandicapDao handicapDao;

        private insertHandicapAsyncTask(HandicapDao handicapdao) {
            this.handicapDao = handicapdao;
        }

        @Override
        protected Void doInBackground(Handicap... handicaps) {
            handicapDao.insert(handicaps[0]);
            return null;
        }
    }

    private static class updateHandicapAsyncTask extends AsyncTask<Handicap, Void, Void> {
        private HandicapDao handicapDao;

        private updateHandicapAsyncTask(HandicapDao handicapdao) {
            this.handicapDao = handicapdao;
        }

        @Override
        protected Void doInBackground(Handicap... handicaps) {
            handicapDao.update(handicaps[0]);
            return null;
        }
    }
    private static class deleteHandicapAsyncTask extends AsyncTask<Handicap, Void, Void> {
        private HandicapDao handicapDao;

        private deleteHandicapAsyncTask(HandicapDao handicapdao) {
            this.handicapDao = handicapdao;
        }

        @Override
        protected Void doInBackground(Handicap... handicaps) {
            handicapDao.delete(handicaps[0]);
            return null;
        }
    }
    private static class deleteAllHandicapAsyncTask extends AsyncTask<Void, Void, Void> {
        private HandicapDao handicapDao;

        private deleteAllHandicapAsyncTask(HandicapDao handicapdao) {
            this.handicapDao = handicapdao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            handicapDao.deleteAll();
            return null;
        }
    }


}
