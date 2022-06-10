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

        @Query("SELECT * FROM stationvelib WHERE station_id = (:stationId)")
        suspend fun loadById(stationId:Long): StationVelib

        /*@Query("SELECT * FROM stationvelib WHERE fav='true'")
        fun loadAllFav(): List<StationVelib>

        @Insert
        fun insertStation(stations:List<StationVelib>)*/

        @Insert
        suspend fun insertStation(station: StationVelib)



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
