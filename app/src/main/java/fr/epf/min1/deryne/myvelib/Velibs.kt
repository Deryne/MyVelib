package fr.epf.min1.deryne.myvelib

import retrofit2.http.GET
import retrofit2.http.Query

interface Velibs {
    //quand quelqu'un appelle, tu fais un GET
    @GET("station_information.json")

    suspend fun getUsers(): GetUsersResult //on peut la mettre en pause cette fonction
}

data class GetUsersResult(val data : UserResult)
data class UserResult(val stations : List<Station>)




