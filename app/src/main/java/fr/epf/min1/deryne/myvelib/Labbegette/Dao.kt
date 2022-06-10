package fr.epf.min1.deryne.myvelib.Labbegette

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.epf.min1.deryne.myvelib.StationVelib
import fr.epf.min1.deryne.myvelib.favoris


@Dao
    interface StationVelibDao {
        @Query("SELECT * FROM stationvelib")
        suspend fun getAll(): List<StationVelib>

        @Query("DELETE FROM stationvelib")
        suspend fun deleteAll()




        @Insert
        suspend fun insertStation(listStation: List<StationVelib>)



    }
    @Dao
    interface StationVelibDaoFav{
        @Query("SELECT * FROM favoris")
        suspend fun getAll(): List<favoris>

        @Insert
        suspend fun insertFav(favStation: favoris)

        @Delete
        suspend fun delete(favStation: favoris)


    }
