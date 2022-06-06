package fr.epf.min1.deryne.myvelib.Labbegette


import retrofit2.http.GET

interface StationVelibStatusAPI{
    @GET("station_status.json")
    suspend fun getStatusStation(): GetStatusStationResult

}
    data class GetStatusStationResult(val data:StationsStatusResult)
    data class StationsStatusResult(val stations:List<StationVelibStatus>)
    data class StationVelibStatus(val station_id:Long, val num_bikes_available :Int, val num_docks_available:Int)



interface StationVelibInformationAPI {
    @GET("station_information.json")
    suspend fun getLieuStation():  GetLieuStationResult


}


data class GetLieuStationResult(val data:StationsLieuResult)
data class StationsLieuResult(val stations : List<StationVelibLieu>)
data class StationVelibLieu(val station_id:Long, val name:String, val lat:Double, val lon:Double, val capacity:Int)

