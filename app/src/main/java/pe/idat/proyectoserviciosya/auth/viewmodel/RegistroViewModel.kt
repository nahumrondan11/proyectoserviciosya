package pe.idat.proyectoserviciosya.auth.viewmodel

import android.util.Log
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
import pe.idat.proyectoserviciosya.auth.data.network.retrofitclient.RetrofitClient.apiService
import pe.idat.proyectoserviciosya.auth.data.network.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory


class RegistroViewModel : ViewModel() {

    private val _paises = MutableLiveData<List<Pais>>()
    val paises: LiveData<List<Pais>> get() = _paises

    private val _departamentos = MutableLiveData<List<Departamento>>()
    val departamentos: LiveData<List<Departamento>> get() = _departamentos

    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.18.7:7231/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    private val _registroResult = MutableLiveData<Boolean>()
    val registroResult: LiveData<Boolean> = _registroResult


    fun obtenerPaises() {
        viewModelScope.launch {
            try {
                val response = api.obtenerPaises()
                if (response.isSuccessful) {
                    _paises.value = response.body() ?: emptyList()
                } else {
                    _paises.value = emptyList()
                }
            } catch (e: Exception) {
                _paises.value = emptyList()
            }
        }
    }

    fun obtenerDepartamentos(idPais: Int) {
        viewModelScope.launch {
            try {
                val response = api.obtenerDepartamentos(idPais)
                if (response.isSuccessful) {
                    _departamentos.value = response.body() ?: emptyList()
                } else {
                    _departamentos.value = emptyList()
                }
            } catch (e: Exception) {
                _departamentos.value = emptyList()
            }
        }
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