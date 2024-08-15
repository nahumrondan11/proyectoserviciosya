package pe.idat.proyectoserviciosya.auth.data.network.request

data class LoginRequest (
    val correoelectronico:String,
    val contrasena:String
)