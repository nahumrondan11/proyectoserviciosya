package pe.idat.proyectoserviciosya.core.dataclass

data class Servicio(
    val idUsuario: Int,
    val nombreUsuario: String,
    val idServicio: Int,
    val nombreServicio: String,
    val ubicacion: String,
    val precio: String,
    val calificacion: Int
)
