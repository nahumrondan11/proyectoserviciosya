package pe.idat.proyectoserviciosya.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class PaisesResponse(
    @SerializedName("\$values")
    val paises: List<Pais>
)