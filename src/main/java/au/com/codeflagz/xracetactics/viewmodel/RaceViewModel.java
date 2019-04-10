package au.com.codeflagz.xracetactics.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import au.com.codeflagz.xracetactics.datalayer.repository.RaceRepository;
import au.com.codeflagz.xracetactics.model.Race;

public class RaceViewModel extends AndroidViewModel {
    private RaceRepository repository;
    private LiveData<List<Race>> allRaces;

    public RaceViewModel(@NonNull Application application) {
        super(application);
        repository = new RaceRepository(application);
        allRaces=repository.getRaces();
    }

    public void insert (Race race) {
        repository.insert(race);
    }

    public void update(Race race){
        repository.update(race);
    }
    public void delete(Race race){
        repository.delete(race);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public Race getRace(int id){
        return repository.getRace(id);
    }
    public LiveData<List<Race>> getAllRaces(){
        return allRaces;
    }

}
