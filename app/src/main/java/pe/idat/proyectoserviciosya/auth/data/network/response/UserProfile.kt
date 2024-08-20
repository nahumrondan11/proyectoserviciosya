package pe.idat.proyectoserviciosya.auth.data.network.response

data class UserProfile(
    val idusuario: Int,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val direccion: String,
    val iddepartamento: Int,
    val departamentoNombre: String,
    val idpais: Int,
    val paisNombre: String,
    val correoelectronico: String,
    val contrasena: String,
    val cuentabancaria: String,
    val opcionesPago: List<OpcionPago>
)
