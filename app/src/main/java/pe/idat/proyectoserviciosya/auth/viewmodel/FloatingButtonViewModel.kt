package pe.idat.proyectoserviciosya.auth.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FloatingButtonViewModel : ViewModel() {
    private val _isButtonVisible = MutableLiveData(true)
    val isButtonVisible: LiveData<Boolean> get() = _isButtonVisible

    fun showButton() {
        _isButtonVisible.value = true
    }

    fun hideButton() {
        _isButtonVisible.value = false
    }
}
