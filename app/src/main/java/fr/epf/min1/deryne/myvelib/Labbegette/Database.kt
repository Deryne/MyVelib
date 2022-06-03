package fr.epf.min1.deryne.myvelib.Labbegette

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.epf.min1.deryne.myvelib.StationVelib

class Database {


    @Database(entities = [StationVelib::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun stationvelibDao(): Dao.StationVelibDao
    }
}