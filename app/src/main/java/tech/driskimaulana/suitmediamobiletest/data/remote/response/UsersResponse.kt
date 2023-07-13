package tech.driskimaulana.suitmediamobiletest.data.remote.response

import com.google.gson.annotations.SerializedName
import tech.driskimaulana.suitmediamobiletest.data.models.RegresinModel

data class UsersResponse (
    @SerializedName("page")
    val page: Int,

    @SerializedName("per_page")
    val perPage: Int,

    @SerializedName("total")
    val total: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("data")
    val data: ArrayList<RegresinModel>
)