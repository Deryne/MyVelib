package fr.epf.min1.deryne.myvelib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent

class FavorisAdapter(private val favorisStations: List<StationVelib>) :
    RecyclerView.Adapter<FavorisAdapter.FavorisViewHolder>() {
    class FavorisViewHolder(val View: View) : RecyclerView.ViewHolder(View)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorisViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val stationView = inflater.inflate(
            R.layout.activity_favoris_adapter,
            parent, false
        )
        return FavorisViewHolder(stationView)
    }

    override fun onBindViewHolder(holder: FavorisViewHolder, position: Int) {
        val stationFavoris = favorisStations[position]
        holder.View.setOnClickListener{
            val context = it.context
            val intent = Intent(context, DetailsStationVelibActivity:: class.java)
            intent.putExtra("station_id", stationFavoris.station_id)
            context.startActivity(intent)
        }

        val stationName = holder.View.findViewById<TextView>(R.id.adapter_stationvelib_name_textview)
        val stationId = holder.View.findViewById<TextView>(R.id.adapter_stationvelib_id_text_view)
        stationName.text = stationFavoris.name
        stationId.text = stationFavoris.station_id.toString()


    }

    override fun getItemCount() = favorisStations.size
    }




