package pe.idat.proyectoserviciosya.auth.data.network.request

data class ConfirmarPagoRequest(
    val idUsuario: Int,
    val idServicio: Int,
    val idTipoOpcionPago: Int
)
