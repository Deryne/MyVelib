package fr.epf.min1.deryne.myvelib

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
import com.google.maps.android.clustering.ClusterManager
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
    private lateinit var clusterManager: ClusterManager<StationVelib>


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
        mapFragment.getMapAsync { googleMap ->
            setUpClusterer(googleMap, this)
        }


    }

    private fun setUpClusterer(map: GoogleMap, context: Context) {
        // Position the map.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.856614, 2.3522219), 10f))

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(context, map)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(clusterManager)

        // Add cluster items (markers) to the cluster manager.
        clusterManager.addItems(listStations)
        clusterManager.cluster()

        clusterManager.setOnClusterItemClickListener {
            val intent = Intent(this, DetailsStationVelibActivity::class.java)
            intent.putExtra("station_id", it.station_id)
            startActivity(intent)
            true
        }
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


        if (checkForInternet(this)){
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
            }
            val dbStationVelib = StationDatabase.createDatabase(this)
            val stationDao =
                dbStationVelib.stationvelibDao()// on a accès aux fonctionnalités de la DAO !!
            runBlocking {
                stationDao.deleteAll()
                stationDao.insertStation(listStations)
            }
            dbStationVelib.close()
        }else {
            val dbStationVelib = StationDatabase.createDatabase(this)
            val stationDao =
                dbStationVelib.stationvelibDao()// on a accès aux fonctionnalités de la DAO !!
            runBlocking {
               listStations = stationDao.getAll() as MutableList<StationVelib>
            }
            dbStationVelib.close()
        }

        val dbFavoris = FavorisDataBase.createDatabase(this)
        val favorisDao =
            dbFavoris.stationvelibDaoFav()// on a accès aux fonctionnalités de la DAO !!
        runBlocking {
            ListFavoris = favorisDao.getAll()
        }
        dbFavoris.close()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorislist -> {
                startActivity(Intent(this, ListFavorisActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("MissingPermission")
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}

