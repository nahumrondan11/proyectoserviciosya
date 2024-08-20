package pe.idat.proyectoserviciosya.auth.data.network.service

import pe.idat.proyectoserviciosya.auth.data.network.request.ActualizarUsuarioRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.BuscarServiciosRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.LoginRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.ConfirmarPagoRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.RegistroRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentosResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.LoginResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.PaisesResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.RegistroResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.Categoria
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentoSer
import pe.idat.proyectoserviciosya.auth.data.network.response.Pais
import pe.idat.proyectoserviciosya.auth.data.network.response.PaymentInfo
import pe.idat.proyectoserviciosya.auth.data.network.response.ServiceDetails
import pe.idat.proyectoserviciosya.auth.data.network.response.UserProfileResponse
import pe.idat.proyectoserviciosya.core.dataclass.Servicio
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("api/Acceso/Login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/Usuarios/CrearUsuario")
    fun registrarUsuario(@Body registroRequest: RegistroRequest): Call<RegistroResponse>

    @GET("api/Usuarios/GetPaises")
    suspend fun obtenerPaises(): Response<List<Pais>>

    @GET("api/Usuarios/GetDepartamentos/{idPais}")
    suspend fun obtenerDepartamentos(@Path("idPais") idPais: Int): Response<List<Departamento>>

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

    @POST("api/Pagos/ConfirmarPago")
    suspend fun confirmarPago(@Body request: ConfirmarPagoRequest): Response<Unit>

    @GET("api/Usuarios/GetPerfilUsuario/{idUsuario}")
    suspend fun getPerfilUser(@Path("idUsuario") idUsuario: Int): Response<UserProfileResponse>

    @GET("api/Usuarios/GetPerfilUsuario/{idUsuario}")
    suspend fun getPerfilUsuario(@Path("idUsuario") idUsuario: Int): Response<UserProfileResponse>

    @PUT("api/Usuarios/ActualizarUsuario/{idUsuario}")
    suspend fun actualizarUsuario(
        @Path("idUsuario") idUsuario: Int,
        @Body request: ActualizarUsuarioRequest
    ): Response<Unit>


// Nuevas funciones que devuelven listas directamente
    @GET("api/Usuarios/GetPaises")
    fun obtenerPaisesLista(): Call<List<Pais>>

    @GET("api/Usuarios/GetDepartamentos/{idPais}")
    fun obtenerDepartamentosLista(@Path("idPais") idPais: Int): Call<List<Departamento>>


}

