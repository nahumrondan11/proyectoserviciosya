package pe.idat.proyectoserviciosya.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.idat.proyectoserviciosya.auth.data.network.request.ActualizarUsuarioRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.BuscarServiciosRequest
import pe.idat.proyectoserviciosya.auth.data.network.request.ConfirmarPagoRequest
import pe.idat.proyectoserviciosya.auth.data.network.retrofitclient.RetrofitClient.apiService
import pe.idat.proyectoserviciosya.auth.data.network.service.ApiService
import pe.idat.proyectoserviciosya.auth.data.network.response.Categoria
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentoSer
import pe.idat.proyectoserviciosya.auth.data.network.response.PaymentInfo
import pe.idat.proyectoserviciosya.auth.data.network.response.ServiceDetails
import pe.idat.proyectoserviciosya.auth.data.network.response.UserProfile
import pe.idat.proyectoserviciosya.auth.data.network.response.UserProfileResponse
import pe.idat.proyectoserviciosya.core.dataclass.Servicio
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiciosViewModel : ViewModel() {


    private val _serviciosFiltrados = MutableLiveData<List<Servicio>>()
    val serviciosFiltrados: LiveData<List<Servicio>> = _serviciosFiltrados

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _departamentoser = MutableLiveData<List<DepartamentoSer>>()
    val departamentoser: LiveData<List<DepartamentoSer>> = _departamentoser

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _userProfile = MutableLiveData<UserProfileResponse?>()
    val userProfile: LiveData<UserProfileResponse?> get() = _userProfile

    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.18.7:7231/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        obtenerCategorias()
        obtenerDepartamentos()
    }

    fun obtenerServiciosNoEliminados() {
        viewModelScope.launch {
            val response = api.obtenerServiciosNoEliminados()
            _serviciosFiltrados.value = response
        }
    }

    fun buscarServicios(
        nombre: String?,
        categoriaId: Int?,
        departamentoId: Int?,
        precioMin: Float?
    ) {
        viewModelScope.launch {
            try {
                if (departamentoId == null || departamentoId == 0) {
                    Log.e("ServiciosViewModel", "Departamento ID es nulo o 0")
                }

                val request = BuscarServiciosRequest(
                    nombreServicio = nombre,
                    categoriaId = categoriaId,
                    departamentoId = departamentoId ?: 0,
                    precioMin = precioMin
                )
                Log.d("ServiciosViewModel", "Request enviado: $request")
                val resultados = apiService.buscarServicios(request)
                _serviciosFiltrados.postValue(resultados)
            } catch (e: Exception) {
                Log.e("ServiciosViewModel", "Error en buscarServicios: ${e.message}")
            }
        }
    }


    private fun obtenerCategorias() {
        viewModelScope.launch {
            val response = api.obtenerCategorias()
            _categorias.value = response
        }
    }

    private fun obtenerDepartamentos() {
        viewModelScope.launch {
            val response = api.obtenerDepartamentos()
            _departamentoser.value = response
        }
    }

    suspend fun getDetalleServicio(idServicio: Int): ServiceDetails? {
        return try {
            val response = apiService.getDetalleServicio(idServicio)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ServiciosViewModel", "Error en la respuesta: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ServiciosViewModel", "Error al obtener los detalles del servicio", e)
            null
        }
    }


    fun enviarPago(idServicio: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.enviarPago(idServicio)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error en el proceso de pago")
            }
        }
    }


    suspend fun getPaymentInfo(idServicio: Int): PaymentInfo? {
        return try {
            val response = apiService.getPaymentInfo(idServicio)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun confirmarPago(
        idUsuario: Int,
        idServicio: Int,
        idTipoOpcionPago: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = ConfirmarPagoRequest(idUsuario, idServicio, idTipoOpcionPago)
                val response = api.confirmarPago(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error al confirmar el pago: ${response.code()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }

    suspend fun getPerfilUser(idUsuario: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = api.getPerfilUser(idUsuario)
                if (response.isSuccessful) {
                    _userProfile.postValue(response.body())
                } else {
                    _userProfile.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("ServiciosViewModel", "Error al obtener el perfil de usuario", e)
                _userProfile.postValue(null)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    suspend fun getUserProfile(idUsuario: Int): UserProfileResponse? {
        return try {
            val response = api.getPerfilUsuario(idUsuario)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("ServiciosViewModel", "Error al obtener el perfil de usuario", e)
            null
        }
    }




    suspend fun actualizarUsuario(
        idUsuario: Int,
        request: ActualizarUsuarioRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val response = apiService.actualizarUsuario(idUsuario, request)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                onError("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            onError(e.message ?: "Error desconocido")
        }
    }





}
