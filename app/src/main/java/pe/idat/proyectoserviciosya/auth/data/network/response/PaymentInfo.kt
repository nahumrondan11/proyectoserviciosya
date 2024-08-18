package pe.idat.proyectoserviciosya.auth.data.network.response

data class PaymentInfo(
    val idServicio: Int,
    val idUsuario: Int,
    val nombreUsuario: String,
    val nombreServicio: String,
    val fechaActual: String,
    val costoServicio: String,
    val cuentaBancaria: String,
    val telefono: String,
    val tiposPago: List<TipoPago>
)
