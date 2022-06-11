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
import fr.epf.min1.deryne.myvelib.Labbegette.FavorisDataBase
import kotlinx.coroutines.runBlocking


private const val TAG = "DetailsStationVelibActivity"

class DetailsStationVelibActivity: AppCompatActivity() {

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_station_velib)
        val stationId = intent.getLongExtra("station_id", 0)
        val d = Log.d(TAG, "onCreateB: $stationId")
        val stationSelectionee = listStations.filter { station -> station.station_id == stationId }[0]


        val tvNameStation = findViewById<TextView>(R.id.title)
        val tvIdStation = findViewById<TextView>(R.id.snippet)
        val tvCapacity = findViewById<TextView>(R.id.capacity)
        val tvNbrVelosDispo = findViewById<TextView>(R.id.nbrVelosDispo)
        val tvNbrDockDispo = findViewById<TextView>(R.id.nbrDockDispo)
        val fav_button = findViewById<FloatingActionButton>(R.id.fav_button)


       setupButton(fav_button, stationId)
        fav_button.setOnClickListener{
            val favoris = favoris(stationId)
            val dbFavoris = FavorisDataBase.createDatabase(this)
            val favorisDao = dbFavoris.stationvelibDaoFav()// on a accès aux fonctionnalités de la DAO !!
            if (fav_button.isExpanded){

                runBlocking{
                    favorisDao.delete(favoris)
                }
                fav_button.isExpanded = false
                fav_button.size = FloatingActionButton.SIZE_MINI// pas favoris

                }else{
                runBlocking{
                    favorisDao.insertFav(favoris)

                }
                fav_button.isExpanded = true
                fav_button.size = FloatingActionButton.SIZE_NORMAL//favoris


            }
            runBlocking{
                ListFavoris = favorisDao.getAll()
            }
            dbFavoris.close()
        }


        tvNameStation.text=stationSelectionee.name
        tvIdStation.text= stationSelectionee.station_id.toString()
        tvCapacity.text=stationSelectionee.capacity.toString()
        tvNbrVelosDispo.text=stationSelectionee.nbrVelosDispo.toString()
        tvNbrDockDispo.text=stationSelectionee.nbrDockDispo.toString()

    }

    fun setupButton(fab: FloatingActionButton, stationId: Long){
        fab.isExpanded = ListFavoris.filter { favoris -> favoris.station_id == stationId }.isNotEmpty()
        if (fab.isExpanded){
            fab.size = FloatingActionButton.SIZE_NORMAL//favoris
        }
        else{
            fab.size = FloatingActionButton.SIZE_MINI//pas fav

        }



    }

    }


private fun FragmentActivity.OnButtonCheckedListener(savedInstanceState: Bundle?) {
    TODO("Not yet implemented")
}
