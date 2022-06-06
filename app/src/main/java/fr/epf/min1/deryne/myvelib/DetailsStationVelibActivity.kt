package fr.epf.min1.deryne.myvelib;
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import fr.epf.min1.deryne.myvelib.Labbegette.Database


private const val TAG = "DetailsStationVelibActivity"

class DetailsStationVelibActivity: AppCompatActivity() {

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_station_velib)
        val stationId = intent.getLongExtra("station_id", 0)
        val d = Log.d(TAG, "onCreate: $stationId")
        val stationSelectionee = listStations.filter { station -> station.station_id == stationId }[0]


        val tvNameStation = findViewById<TextView>(R.id.title)
        val tvIdStation = findViewById<TextView>(R.id.snippet)
        val tvCapacity = findViewById<TextView>(R.id.capacity)
        val tvNbrVelosDispo = findViewById<TextView>(R.id.nbrVelosDispo)
        val tvNbrDockDispo = findViewById<TextView>(R.id.nbrDockDispo)
//        val db = databaseBuilder(
//            applicationContext,
//            Database.AppDatabase::class.java, "database-name"
//        ).allowMainThreadQueries().build()
//
//        val velibDao = db.stationvelibDao()
     //   val stationSelectionee=velibDao.loadById(idStationSel)

        tvNameStation.text=stationSelectionee.name
        tvIdStation.text= stationSelectionee.station_id.toString()
        tvCapacity.text=stationSelectionee.capacity.toString()
        tvNbrVelosDispo.text=stationSelectionee.nbrVelosDispo.toString()
        tvNbrDockDispo.text=stationSelectionee.nbrDockDispo.toString()

    }
}