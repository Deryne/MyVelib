package fr.epf.min1.deryne.myvelib.Labbegette

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.epf.min1.deryne.myvelib.StationVelib
import fr.epf.min1.deryne.myvelib.favoris


@Database(entities = [StationVelib::class], version = 1)
abstract class StationDatabase : RoomDatabase() {
    abstract fun stationvelibDao(): StationVelibDao

    companion object {
        fun createDatabase(context: Context): StationDatabase {
            return Room.databaseBuilder(
                context,
                StationDatabase::class.java,
                "StationVelib"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

@Database(entities = [favoris::class], version = 1)
abstract class FavorisDataBase : RoomDatabase() {
    abstract fun stationvelibDaoFav(): StationVelibDaoFav

    companion object {
        fun createDatabase(context: Context): FavorisDataBase {
            return Room.databaseBuilder(
                context,
                FavorisDataBase::class.java,
                "favoris"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

