package pe.idat.proyectoserviciosya.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class DepartamentoSerResponse(
    @SerializedName("\$values")
    val departamentoSer: List<DepartamentoSer>
)
