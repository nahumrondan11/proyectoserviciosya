package pe.idat.proyectoserviciosya.auth.data.network.request

data class BuscarServiciosRequest(
    val nombreServicio: String?,
    val categoriaId: Int?,
    val departamentoId: Int?,
    val precioMin: Float?
)