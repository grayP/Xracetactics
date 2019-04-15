package au.com.codeflagz.xracetactics.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import au.com.codeflagz.xracetactics.datalayer.repository.HandicapRepository;
import au.com.codeflagz.xracetactics.datalayer.repository.RaceRepository;
import au.com.codeflagz.xracetactics.model.Handicap;
import au.com.codeflagz.xracetactics.model.Race;

public class HandicapViewModel extends AndroidViewModel {
    private HandicapRepository repository;
    private RaceRepository raceRepository;
    private LiveData<List<Handicap>> allHandicaps;
    private LiveData<List<Race>> allRaces;
    private int numDisplay;


    public HandicapViewModel(@NonNull Application application) {
        super(application);
        repository = new HandicapRepository(application);
        raceRepository = new RaceRepository(application);
        allHandicaps=repository.getHandicaps();
        allRaces= raceRepository.getRaces();
    }

    public void insertRace(Race race){raceRepository.insert(race);}
    public void updateRace (Race race){raceRepository.update(race);}
    public void deleteRace( Race race) {raceRepository.delete(race);}
    public void deleteAllRaces() { raceRepository.deleteAll();}
    public Race getRace(int id) { return raceRepository.getRace(id);}
    public LiveData<List<Race>> getAllRaces() { return allRaces;}


    public void insert (Handicap handicap) {
        repository.insert(handicap);
    }
    public void update(Handicap handicap){
        repository.update(handicap);
    }
    public void delete(Handicap handicap){ repository.delete(handicap); }
    public void deleteAll(){ repository.deleteAll(); }
    public Handicap getHandicap(int id){
        return repository.getHandicap(id);
    }
    public LiveData<List<Handicap>> getAllHandicaps(){
        return allHandicaps;
    }
    public LiveData<List<Handicap>> getHandicapsForRace(int raceNum) { return repository.getHandicapsForRace(raceNum);}

    public int getNumDisplay() {
        return numDisplay;
    }

    public void setNumDisplay(int numDisplay) {
        this.numDisplay = numDisplay;
    }
}
