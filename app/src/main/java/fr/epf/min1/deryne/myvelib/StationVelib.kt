package fr.epf.min1.deryne.myvelib;

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

@Entity
data class StationVelib(
    @PrimaryKey val station_id:Long,
    val name:String,
    val lat: Double,
    val lon:Double,
    val capacity:Int,
    val nbrVelosDispo :Int,
    val nbrDockDispo:Int,

)  : ClusterItem {
    override fun getPosition(): LatLng = LatLng(lat, lon)

    override fun getTitle(): String = name
    override fun getSnippet(): String = name

}

//: Serializable




