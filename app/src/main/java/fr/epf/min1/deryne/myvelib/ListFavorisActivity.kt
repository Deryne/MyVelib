package fr.epf.min1.deryne.myvelib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room



private const val TAG = "ListFavorisActivity"

class ListFavorisActivity(): AppCompatActivity(){

    var FavorisAdapter: FavorisAdapter? = null
    val ListFavorisStations : MutableList<StationVelib> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListFavoris.forEach{favoris ->
           val favorisStation = listStations.filter {favoris.station_id == it.station_id }[0]
            ListFavorisStations.add(favorisStation)
        }
        setContentView(R.layout.activity_list_favoris)
        val RecyclerView = findViewById<RecyclerView>(R.id.list_favoris_recyclerview)

        RecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        FavorisAdapter = FavorisAdapter(ListFavorisStations)
        RecyclerView.adapter = FavorisAdapter
    }

}