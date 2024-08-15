package pe.idat.proyectoserviciosya.auth.data.network.service

import pe.idat.proyectoserviciosya.auth.data.network.request.LoginRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.RegistroRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentosResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.LoginResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.Pais
import pe.idat.proyectoserviciosya.auth.data.network.response.PaisesResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.RegistroResponse
import retrofit2.Call
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

}
