package au.com.codeflagz.xracetactics.datalayer.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import au.com.codeflagz.xracetactics.model.Handicap;

@Dao
public interface HandicapDao {

    @Insert
    void insert(Handicap handicap);

    @Update
    void update(Handicap handicap);

    @Delete
    void delete(Handicap handicap);

   @Query("Delete from handicap")
    void deleteAll();

    @Query("SELECT * FROM handicap ORDER By rating, boat")
    LiveData<List<Handicap>> getAllHandicaps();

    @Query("Select * from handicap WHERE Id=:id")
    Handicap getHandicap(int id);

    @Query("Select * from handicap Where race=:raceNum")
    LiveData<List<Handicap>> gethandicapsForRace(int raceNum);
}
