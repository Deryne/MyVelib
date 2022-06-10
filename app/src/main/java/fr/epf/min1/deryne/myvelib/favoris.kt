package fr.epf.min1.deryne.myvelib;

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class favoris (
    @PrimaryKey
    val station_id:Long,

)