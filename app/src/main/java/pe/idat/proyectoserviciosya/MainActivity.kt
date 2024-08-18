package pe.idat.proyectoserviciosya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pe.idat.proyectoserviciosya.auth.view.*
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.LoginViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta
import pe.idat.proyectoserviciosya.ui.theme.AppproyectoserviciosyaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val floatingButtonViewModel: FloatingButtonViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MainScreen(navController, floatingButtonViewModel)
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController, floatingButtonViewModel: FloatingButtonViewModel) {
    val isButtonVisible = floatingButtonViewModel.isButtonVisible.observeAsState(true)
    Scaffold(
        floatingActionButton = {
            if (isButtonVisible.value == true && debeMostrarBotonNotificaciones(navController.currentDestination?.route)) {
                Box(modifier = Modifier.padding(16.dp)) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Ruta.NOTIFICACIONES_SCREEN) },
                        containerColor = Color(0xFFD1C4E9)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                    }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 12.dp, y = (-12).dp)
                            .background(Color.White, CircleShape)
                            .clickable {
                                floatingButtonViewModel.hideButton()
                            }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                AppNavigation(navController, floatingButtonViewModel)
            }
        }
    )
}

fun debeMostrarBotonNotificaciones(ruta: String?): Boolean {
    return when (ruta) {
        Ruta.USER_PROFILE_SCREEN,
        Ruta.PROVIDER_PROFILE_SCREEN,
        Ruta.MAIN_SEARCH_SCREEN,
        Ruta.VER_CAL_RES_SCREEN,
        Ruta.CALIF_SCREEN,
        Ruta.NOTIFICACIONES_SCREEN -> true
        else -> false
    }
}

@Composable
fun AppNavigation(navController: NavHostController, floatingButtonViewModel: FloatingButtonViewModel) {
    NavHost(navController = navController, startDestination = Ruta.LOGIN_SCREEN) {
        composable(Ruta.LOGIN_SCREEN) {
            LoginScreen(navController, LoginViewModel())
        }
        composable(Ruta.REGISTRO_SCREEN) {
            RegistroScreen(navController)
        }
        composable(Ruta.USER_PROFILE_SCREEN) {
            UserProfileScreen(navController, floatingButtonViewModel)
        }
        composable("${Ruta.PROVIDER_PROFILE_SCREEN}/{idServicio}") { navBackStackEntry ->
            val idServicio = navBackStackEntry.arguments?.getString("idServicio")?.toIntOrNull()

            // Verifica que idServicio no sea nulo antes de pasar a la pantalla
            idServicio?.let {
                ProviderProfileScreen(
                    navController = navController,
                    floatingButtonViewModel = floatingButtonViewModel,
                    idServicio = it
                )
            }
        }
        composable(Ruta.MAIN_SEARCH_SCREEN) {
            SearchServicesScreen(navController, floatingButtonViewModel)
        }
        composable("${Ruta.PAGO_SCREEN}/{idServicio}") { backStackEntry ->
            val idServicio = backStackEntry.arguments?.getString("idServicio")?.toInt() ?: 0
            PaymentScreen(navController, idServicio)
        }

        composable(Ruta.VER_CAL_RES_SCREEN) {
            ProviderRatingsReviewsScreen(navController, floatingButtonViewModel)
        }
        composable(Ruta.CALIF_SCREEN) {
            UserRatingsReviewsScreen(navController, floatingButtonViewModel)
        }
        composable(Ruta.NOTIFICACIONES_SCREEN) {
            NotificationsScreen(navController, floatingButtonViewModel)
        }
        composable(Ruta.UPDATEINFUSER_SCREEN){
            UpdateInfUserScreen(navController)
        }
        composable(Ruta.EDIRADDSERVICE_SCREEN){
            EditAddServicesScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppproyectoserviciosyaTheme {
        val navController = rememberNavController()
        val floatingButtonViewModel = FloatingButtonViewModel()
        MainScreen(navController, floatingButtonViewModel)
    }
}
