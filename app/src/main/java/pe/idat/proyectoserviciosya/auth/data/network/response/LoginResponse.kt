package pe.idat.proyectoserviciosya.auth.data.network.response

data class LoginResponse(
    val mensaje: String,
    val usuario: Usuario
)

data class Usuario(
    val idusuario: Int,
    val correoelectronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String?,
    val direccion: String?,
    val fotoperfil: String?,
    val creadoen: String,
    val actualizadoen: String,
    val eliminado: Boolean
)