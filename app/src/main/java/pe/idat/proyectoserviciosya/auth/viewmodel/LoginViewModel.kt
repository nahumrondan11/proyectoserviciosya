package pe.idat.proyectoserviciosya.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _usuario = MutableLiveData<String>()
    val usuario : LiveData<String> = _usuario
    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    fun onLoginValueChanged(usuario:String, password: String){
        _usuario.value = usuario
        _password.value = password
    }
    fun validarcredenciales(email: String, password: String): Boolean {
        // lógica de validación
        // Validación temporal estáticas para demostración
        return email == "usuario" && password == "123"
    }
    fun login(): Boolean{
        if(usuario.value == "nahum2" && password.value == "123") {
            return true
        }
        return false
    }
}