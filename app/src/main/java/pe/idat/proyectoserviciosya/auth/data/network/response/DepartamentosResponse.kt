package pe.idat.proyectoserviciosya.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class DepartamentosResponse(
    @SerializedName("\$values")
    val departamentos: List<Departamento>
)
