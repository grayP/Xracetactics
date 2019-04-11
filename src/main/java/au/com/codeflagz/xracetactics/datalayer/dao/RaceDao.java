package au.com.codeflagz.xracetactics.datalayer.dao;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import au.com.codeflagz.xracetactics.model.Race;

@Dao
public interface RaceDao {

    @Insert
    void insert(Race race);

    @Update
    void update(Race race);

    @Delete
    void delete(Race race);

   @Query("Delete from race")
    void deleteAll();

    @Query("SELECT * FROM race ORDER By race")
    LiveData<List<Race>> getAllRaces();

    @Query("Select * from race WHERE Id=:id")
    Race getRace(int id);



}
