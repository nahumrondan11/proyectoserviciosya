package pe.idat.proyectoserviciosya.core.dataclass

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFS_NAME = "user_prefs"
    private const val USER_ID_KEY = "user_id"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var userId: Int?
        get() = sharedPreferences.getInt(USER_ID_KEY, -1).takeIf { it != -1 }
        set(value) {
            sharedPreferences.edit().putInt(USER_ID_KEY, value ?: -1).apply()
        }
}
