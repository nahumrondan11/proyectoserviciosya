package pe.idat.proyectoserviciosya.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.idat.proyectoserviciosya.auth.data.network.request.LoginRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.LoginResponse
import pe.idat.proyectoserviciosya.auth.data.network.retrofitclient.RetrofitClient
import pe.idat.proyectoserviciosya.core.dataclass.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun onLoginValueChanged(usuario: String, password: String) {
        _usuario.value = usuario
        _password.value = password
    }

    fun validarcredenciales() {
        val email = _usuario.value ?: return
        val password = _password.value ?: return

        val loginRequest = LoginRequest(email, password)

        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.mensaje == "Login exitoso") {
                    val idUsuario = response.body()?.usuario?.idusuario
                    if (idUsuario != null){
                        SessionManager.userId= idUsuario
                    }
                    _loginResult.value = true
                } else {
                    _loginResult.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = false
            }
        })
    }
}
