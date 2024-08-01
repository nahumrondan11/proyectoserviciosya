package pe.idat.proyectoserviciosya.core.dataclass

data class Service(
    var name: String,
    var description: String,
    var rate: String,
    var availability: String,
    var isDeleted: Boolean = false
)