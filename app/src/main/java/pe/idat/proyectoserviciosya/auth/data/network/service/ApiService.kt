package pe.idat.proyectoserviciosya.auth.data.network.service

import pe.idat.proyectoserviciosya.auth.data.network.request.BuscarServiciosRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.LoginRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.RegistroRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentosResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.LoginResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.PaisesResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.RegistroResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.Categoria
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentoSer
import pe.idat.proyectoserviciosya.auth.data.network.response.PaymentInfo
import pe.idat.proyectoserviciosya.auth.data.network.response.ServiceDetails
import pe.idat.proyectoserviciosya.core.dataclass.Servicio
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/Acceso/Login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/Usuarios/CrearUsuario")
    fun registrarUsuario(@Body registroRequest: RegistroRequest): Call<RegistroResponse>

    @GET("api/Usuarios/GetPaises")
    fun obtenerPaises(): Call<PaisesResponse>

    @GET("api/Usuarios/GetDepartamentos/{idPais}")
    fun obtenerDepartamentos(@Path("idPais") idPais: Int): Call<DepartamentosResponse>

    @GET("api/Servicios/ObtenerServiciosNoEliminados")
    suspend fun obtenerServiciosNoEliminados(): List<Servicio>

    @GET("api/Servicios/Departamentos")
    suspend fun obtenerDepartamentos(): List<DepartamentoSer>

    @GET("api/Servicios/Categorias")
    suspend fun obtenerCategorias(): List<Categoria>

    @POST("api/Servicios/BuscarServicios")
    suspend fun buscarServicios(
        @Body request: BuscarServiciosRequest
    ): List<Servicio>

    @GET("api/Servicios/GetDetalleServicio/{idServicio}")
    suspend fun getDetalleServicio(@Path("idServicio") idServicio: Int): Response<ServiceDetails>

    @POST("api/Pagos/GetServicioInfo/{idServicio}")
    suspend fun enviarPago(@Path("idServicio") idServicio: Int): Response<Unit>

    @GET("api/Pagos/GetServicioInfo/{idServicio}")
    suspend fun getPaymentInfo(@Path("idServicio")idServicio: Int): Response<PaymentInfo>
}

