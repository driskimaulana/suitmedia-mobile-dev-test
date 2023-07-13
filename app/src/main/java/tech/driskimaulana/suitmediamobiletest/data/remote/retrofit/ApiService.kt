package tech.driskimaulana.suitmediamobiletest.data.remote.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tech.driskimaulana.suitmediamobiletest.data.remote.response.UsersResponse

interface ApiService {
    @GET("api/users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") perpage: Int = 6): Call<UsersResponse>
}