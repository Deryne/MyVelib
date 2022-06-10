package fr.epf.min1.deryne.myvelib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Database
import androidx.room.Room

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr.epf.min1.deryne.myvelib.Labbegette.*
import fr.epf.min1.deryne.myvelib.databinding.ActivityMapsBinding
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private const val TAG = "MapsActivity"
var listStations: MutableList<StationVelib> = mutableListOf() //liste vide //
var ListFavoris: List<favoris> = listOf()//liste fav



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var listStationInfo: List<StationVelibLieu> = listOf()

    private var listStationStatus: List<StationVelibStatus> = listOf()

    //elles seront dispos partout dans cette classe
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://velib-metropole-opendata.smoove.pro/opendata/Velib_Metropole/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()


        val service = retrofit.create(StationVelibStatusAPI::class.java)

        runBlocking {//ne bloque pas
            val result = service.getStatusStation()
            Log.d(TAG, "synchroAPI: ${result.data.stations}")
            listStationStatus = result.data.stations
        }


        val serviceInfo = retrofit.create(StationVelibInformationAPI::class.java)

        runBlocking {//ne bloque pas
            val result = serviceInfo.getLieuStation()
            Log.d(TAG, "synchroAPI: ${result.data.stations}")
            listStationInfo = result.data.stations

        }
        listStationInfo.zip(listStationStatus).map {
            StationVelib(
                it.first.station_id,
                it.first.name,
                it.first.lat,
                it.first.lon,
                it.first.capacity,
                it.second.num_bikes_available,
                it.second.num_docks_available,
            )
        }.map {
            listStations.add(it)
            val stationId = it.station_id
            val station = LatLng(it.lat, it.lon)
            mMap.addMarker(MarkerOptions().position(station).title(it.name))
            mMap.setOnInfoWindowClickListener {
                val intent = Intent(this, DetailsStationVelibActivity::class.java)
                intent.putExtra("station_id", stationId)
                startActivity(intent)
            }


        }
        val dbFavoris = FavorisDataBase.createDatabase(this)
        val favorisDao = dbFavoris.stationvelibDaoFav()// on a accès aux fonctionnalités de la DAO !!
        runBlocking{
            ListFavoris = favorisDao.getAll()
        }
        dbFavoris.close()

    }
//    private fun saveFav(marker: Marker){
//        val markerName = marker.title
//        findViewById<Button>(R.id.fav_button).setOnClickListener{
//            val idStation = marker.snippet
//            val stationFav = listStations.find {it.station_id.toString()==idStation}
//            if (stationFav != null) {
//                //favoris.add(stationFav)
//                Toast.makeText(this@MapsActivity, "La station $markerName a été ajouté aux favoris", Toast.LENGTH_SHORT).show()
//
//                val db = Room.databaseBuilder(
//                    applicationContext,
//
//                    Database.AppDatabase::class.java, "database-name"
//                ).allowMainThreadQueries().build()
//
//                val velibDao = db.stationvelibDao()
//                velibDao.insertStation(stationFav)
//                Log.d(TAG, "added : $stationFav")
//
//            }
//        }
//
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorislist -> {
                startActivity(Intent(this, ListFavorisActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

