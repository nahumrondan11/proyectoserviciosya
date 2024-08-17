package pe.idat.proyectoserviciosya.auth.data.network.response

import com.google.gson.annotations.SerializedName

data class CategoriasResponse(
    @SerializedName("\$values")
    val categorias: List<Categoria>
)
