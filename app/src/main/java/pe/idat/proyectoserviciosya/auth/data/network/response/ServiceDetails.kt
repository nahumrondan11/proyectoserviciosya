package pe.idat.proyectoserviciosya.auth.data.network.response

data class ServiceDetails(
    val idServicio: Int,
    val nombreUsuario: String,
    val categoria: String,
    val descripcion: String,
    val tarifa: String,
    val disponibilidad: String
)
