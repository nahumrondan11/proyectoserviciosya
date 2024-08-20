package pe.idat.proyectoserviciosya.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


class UpdatePerfilViewModel : ViewModel() {

        private val _paises = MutableLiveData<List<Pais>>()
        val paises: LiveData<List<Pais>> get() = _paises

        private val _departamentos = MutableLiveData<List<Departamento>>()
        val departamentos: LiveData<List<Departamento>> get() = _departamentos

        fun obtenerPaises() {
            RetrofitClient.apiService.obtenerPaisesLista().enqueue(object : Callback<List<Pais>> {
                override fun onResponse(call: Call<List<Pais>>, response: Response<List<Pais>>) {
                    if (response.isSuccessful) {
                        _paises.value = response.body() ?: emptyList()
                    } else {
                        Log.e("UpdatePerfilViewModel", "Error al obtener países: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Pais>>, t: Throwable) {
                    Log.e("UpdatePerfilViewModel", "Error al obtener países", t)
                }
            })
        }

        fun obtenerDepartamentos(idPais: Int) {
            RetrofitClient.apiService.obtenerDepartamentosLista(idPais).enqueue(object : Callback<List<Departamento>> {
                override fun onResponse(call: Call<List<Departamento>>, response: Response<List<Departamento>>) {
                    if (response.isSuccessful) {
                        _departamentos.value = response.body() ?: emptyList()
                    } else {
                        Log.e("UpdatePerfilViewModel", "Error al obtener departamentos: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Departamento>>, t: Throwable) {
                    Log.e("UpdatePerfilViewModel", "Error al obtener departamentos", t)
                }
            })
        }

}