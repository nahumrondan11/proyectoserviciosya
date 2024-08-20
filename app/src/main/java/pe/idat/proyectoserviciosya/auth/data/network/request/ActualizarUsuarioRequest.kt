package pe.idat.proyectoserviciosya.auth.data.network.request

data class ActualizarUsuarioRequest(
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val direccion: String,
    val iddepartamento: Int,
    val idpais: Int,
    val correoelectronico: String,
    val contrasena: String? = null,  // Nullable para omitirla si no se envía
    val cuentabancaria: String,
    val fotoperfil: String? = null,
    val opcionesPago: List<Int>
)
