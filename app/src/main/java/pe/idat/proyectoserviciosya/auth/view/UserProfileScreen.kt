package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario") },
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
                            text = { Text("Editar Nombres y Apellidos") },
                            onClick = { /* Implement navigation to edit names screen */ }
                        )
                        DropdownMenuItem(
                            text = { Text("Editar Servicios") },
                            onClick = { }
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C2E))
                    .padding(paddingValues)
            ) {
                UserProfileContent()
            }
        }
    )
}

@Composable
fun UserProfileContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.profile_picture), // Luego reemplazar con el ID correcto de la imagen
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del usuario
        Text(
            text = "Nahum Yonatan",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Rondan Urbano",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Tabla de detalles del usuario
        UserDetailGrid()
    }
}

@Composable
fun UserDetailGrid() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        UserDetailItem(label = "Correo Electrónico", value = "usuario@example.com")
        UserDetailItem(label = "Número de Teléfono", value = "+123 456 7890")
        UserDetailItem(label = "Dirección", value = "123 Calle Principal, Ciudad")
        // Agrega más detalles si es necesario
    }
}

@Composable
fun UserDetailItem(label: String, value: String) {
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
            text = value,
            color = Color.White,
            fontSize = 16.sp
        )
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}
