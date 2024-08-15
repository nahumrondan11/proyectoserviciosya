package pe.idat.proyectoserviciosya.auth.data.network.request

data class RegistroRequest(
    val correoelectronico: String,
    val contrasena: String,
    val nombre: String,
    val apellido: String,
    val pais: Int,
    val provincia: Int
)