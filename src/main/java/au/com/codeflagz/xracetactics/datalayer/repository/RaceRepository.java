package au.com.codeflagz.xracetactics.datalayer.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import au.com.codeflagz.xracetactics.datalayer.dao.RaceDao;
import au.com.codeflagz.xracetactics.datalayer.database.HandicapDatabase;
import au.com.codeflagz.xracetactics.model.Race;

public class RaceRepository {
    private RaceDao raceDao;
    private LiveData<List<Race>> allRaces;

    public RaceRepository(Application application) {
        HandicapDatabase raceDatabase = HandicapDatabase.getInstance(application);
        raceDao = raceDatabase.raceDao();
        allRaces=raceDao.getAllRaces();
    }



    public void insert(Race race) {
        new insertRaceAsyncTask(raceDao).execute(race);
    }
    public void update(Race race) {
        new updateRaceAsyncTask(raceDao).execute(race);
    }
    public void delete(Race race) {
        new deleteRaceAsyncTask(raceDao).execute(race);
    }
    public void deleteAll(){
        new deleteAllRaceAsyncTask(raceDao).execute();
    }
    public Race getRace(int id){
       return raceDao.getRace(id);
    }
    public LiveData<List<Race>> getRaces(){
        return allRaces;
    }

    private static class insertRaceAsyncTask extends AsyncTask<Race, Void, Void> {
        private RaceDao raceDao;

        private insertRaceAsyncTask(RaceDao racedao) {
            this.raceDao = racedao;
        }

        @Override
        protected Void doInBackground(Race... races) {
            raceDao.insert(races[0]);
            return null;
        }
    }

    private static class updateRaceAsyncTask extends AsyncTask<Race, Void, Void> {
        private RaceDao raceDao;

        private updateRaceAsyncTask(RaceDao racedao) {
            this.raceDao = racedao;
        }

        @Override
        protected Void doInBackground(Race... races) {
            raceDao.update(races[0]);
            return null;
        }
    }
    private static class deleteRaceAsyncTask extends AsyncTask<Race, Void, Void> {
        private RaceDao raceDao;

        private deleteRaceAsyncTask(RaceDao racedao) {
            this.raceDao = racedao;
        }

        @Override
        protected Void doInBackground(Race... races) {
            raceDao.delete(races[0]);
            return null;
        }
    }
    private static class deleteAllRaceAsyncTask extends AsyncTask<Void, Void, Void> {
        private RaceDao raceDao;

        private deleteAllRaceAsyncTask(RaceDao racedao) {
            this.raceDao = racedao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            raceDao.deleteAll();
            return null;
        }
    }


}
