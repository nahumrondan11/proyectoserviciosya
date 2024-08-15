package pe.idat.proyectoserviciosya.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.auth.data.network.request.RegistroRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentosResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.Pais
import pe.idat.proyectoserviciosya.auth.data.network.response.PaisesResponse
import pe.idat.proyectoserviciosya.auth.data.network.response.RegistroResponse
import pe.idat.proyectoserviciosya.auth.data.network.retrofitclient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistroViewModel : ViewModel() {

    private val _paises = MutableLiveData<List<Pais>>()
    val paises: LiveData<List<Pais>> get() = _paises

    private val _departamentos = MutableLiveData<List<Departamento>>()
    val departamentos: LiveData<List<Departamento>> get() = _departamentos

    private val _registroResult = MutableLiveData<Boolean>()
    val registroResult: LiveData<Boolean> = _registroResult

    fun obtenerPaises() {
        RetrofitClient.apiService.obtenerPaises().enqueue(object : Callback<PaisesResponse> {
            override fun onResponse(call: Call<PaisesResponse>, response: Response<PaisesResponse>) {
                if (response.isSuccessful) {
                    _paises.value = response.body()?.paises ?: emptyList()
                }
            }

            override fun onFailure(call: Call<PaisesResponse>, t: Throwable) {
                // Manejo de error
            }
        })
    }


    fun obtenerDepartamentos(idPais: Int) {
        RetrofitClient.apiService.obtenerDepartamentos(idPais).enqueue(object : Callback<DepartamentosResponse> {
            override fun onResponse(call: Call<DepartamentosResponse>, response: Response<DepartamentosResponse>) {
                if (response.isSuccessful) {
                    _departamentos.value = response.body()?.departamentos ?: emptyList()
                }
            }

            override fun onFailure(call: Call<DepartamentosResponse>, t: Throwable) {
                // Manejo de error
            }
        })
    }



    fun registrarUsuario(registroRequest: RegistroRequest) {
        RetrofitClient.apiService.registrarUsuario(registroRequest).enqueue(object : Callback<RegistroResponse> {
            override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                _registroResult.value = response.isSuccessful
            }

            override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                _registroResult.value = false
            }
        })
    }
}