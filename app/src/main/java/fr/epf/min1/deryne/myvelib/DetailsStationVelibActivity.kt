package fr.epf.min1.deryne.myvelib;
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
        val fav_button = findViewById<ImageButton>(R.id.fav_button)







//        var stationStatus = intent.getBooleanExtra("stationStatus", false)
//        fav_button.setOnClickListener{
//            Log.d(TAG, "stationStatus : $stationStatus")
//            if (stationStatus == true){
//                stationStatus = false
//
//        }
//            else{
//                stationStatus = true
//                Log.d(TAG, "mise en fav: $stationStatus")
//        }
//            finish()
//        }

//        override fun onActivityResult(requestCode: Boolean, resultCode: Boolean, data: Intent?){
//            when(requestCode){
//
//
//            }
//        }


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

    fun setupButton(fab: FloatingActionButton, stationId: Long){


    }

    }


private fun FragmentActivity.OnButtonCheckedListener(savedInstanceState: Bundle?) {
    TODO("Not yet implemented")
}
