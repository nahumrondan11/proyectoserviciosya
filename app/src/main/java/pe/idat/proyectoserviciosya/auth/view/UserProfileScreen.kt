package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.data.network.response.UserProfileResponse
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel
import pe.idat.proyectoserviciosya.core.dataclass.SessionManager
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    navController: NavController,
    floatingButtonViewModel: FloatingButtonViewModel,
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    val userId = SessionManager.userId
    val userProfile by serviciosViewModel.userProfile.observeAsState()
    val loading by serviciosViewModel.loading.observeAsState(initial = true)

    LaunchedEffect(userId) {
        userId?.let {
            serviciosViewModel.getPerfilUser(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario", color = Color.White) },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Editar Perfil", tint = Color.White)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Calificaciones y Reseñas") },
                            onClick = { navController.navigate(Ruta.VER_CAL_RES_SCREEN) }
                        )
                        DropdownMenuItem(
                            text = { Text("Actualizar Datos") },
                            onClick = { navController.navigate(Ruta.UPDATEINFUSER_SCREEN)}
                        )
                        DropdownMenuItem(
                            text = { Text("Editar Servicios") },
                            onClick = { navController.navigate(Ruta.EDIRADDSERVICE_SCREEN)}
                        )
                        DropdownMenuItem(
                            text = { Text("Buscar Servicios") },
                            onClick = { navController.navigate(Ruta.MAIN_SEARCH_SCREEN)  }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            onClick = { navController.navigate(Ruta.LOGIN_SCREEN) }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            if (floatingButtonViewModel.isButtonVisible.observeAsState(true).value) {
                FloatingActionButton(
                    onClick = { navController.navigate(Ruta.NOTIFICACIONES_SCREEN) }
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C2E))
                    .padding(paddingValues)
            ) {
                item {
                    if (loading) {
                        Text(
                            text = "Cargando datos...",
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        userProfile?.let {
                            UserProfileContent(it)
                        } ?: run {
                            Text(
                                text = "Error al cargar los datos.",
                                color = Color.Red,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun UserProfileContent(profile: UserProfileResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del usuario
        Text(
            text = profile.nombre,
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profile.apellido,
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Tabla de detalles del usuario
        UserDetailGrid(profile)
    }
}

@Composable
fun UserDetailGrid(profile: UserProfileResponse) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        UserDetailItem(label = "Correo Electrónico", value = profile.correoelectronico)
        UserDetailItem(label = "Número de Teléfono", value = profile.telefono)
        UserDetailItem(label = "Dirección", value = profile.direccion)
        UserDetailItem(label = "Departamento", value = profile.departamentoNombre)
        UserDetailItem(label = "País", value = profile.paisNombre)
        UserDetailItem(label = "Número de Cuenta Bancaria", value = profile.cuentabancaria)
        UserDetailItem(label = "Opciones de Pago", value = profile.opcionesPago?.joinToString(", ") { it.nombre } ?: "Sin datos")
    }
}


@Composable
fun UserDetailItem(label: String, value: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )
        Text(
            text = value ?: "Sin datos",
            color = Color.White,
            fontSize = 16.sp
        )
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}
