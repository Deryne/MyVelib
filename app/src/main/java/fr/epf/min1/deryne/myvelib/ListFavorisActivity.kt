package fr.epf.min1.deryne.myvelib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import androidx.room.Room



private const val TAG = "ListFavorisActivity"

class ListFavorisActivity(): AppCompatActivity(){

    var listFavorisRecyclerview : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_favoris)

        //val favoris = intent.getSerializableExtra( "listFavoris" ) as ArrayList<StationVelib>
//        val db = Room.databaseBuilder(
//            applicationContext,
//            Database.AppDatabase::class.java, "database-name"
//        ).allowMainThreadQueries().build()
//        val velibDao = db.stationvelibDao()
//        val favoris: MutableList<StationVelib> = velibDao.getAll().toMutableList()

        listFavorisRecyclerview = findViewById<RecyclerView>(R.id.list_favoris_recyclerview)

        listFavorisRecyclerview?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

       // listFavorisRecyclerview?.adapter = StationVelibAdapter(favoris)
    }

}