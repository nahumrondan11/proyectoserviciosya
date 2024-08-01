package pe.idat.proyectoserviciosya.auth.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun ProviderProfileScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
    var expanded by remember { mutableStateOf(false) }

    // Mostrar el botón flotante cuando se carga la pantalla
    LaunchedEffect(Unit) {
        floatingButtonViewModel.showButton()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del Proveedor") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Calificaciones y Reseñas") },
                            onClick = {
                                expanded = false
                                navController.navigate(Ruta.VER_CAL_RES_SCREEN)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Calificar") },
                            onClick = {
                                expanded = false
                                navController.navigate(Ruta.CALIF_SCREEN)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar Sesión") },
                            onClick = {
                                expanded = false
                                navController.navigate(Ruta.LOGIN_SCREEN)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF2C2C2E))
                    .padding(paddingValues)
            ) {
                ProviderProfileContent()
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(Color(0xFF2C2C2E))
            ) {
                Button(
                    onClick = { navController.navigate(Ruta.PAGO_SCREEN) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Contratar Servicio", color = Color.White)
                }
            }
        }
    )
}

@Composable
fun ProviderProfileContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.profile_picture), // Luego Reemp con el ID correcto de la imagen
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del proveedor
        Text(
            text = "SERVICIOSYA",
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Provider",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Tabla de detalles del proveedor
        ProviderDetailGrid()
    }
}

@Composable
fun ProviderDetailGrid() {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ProviderDetailItem(label = "Categoría de Servicios", value = "Electricista")
        ProviderDetailItem(label = "Descripción", value = "Servicios eléctricos generales y reparaciones.")
        ProviderDetailItem(label = "Tarifas", value = "$50 por hora")
        ProviderDetailItem(label = "Disponibilidad (días y horas)", value = "Lunes a Viernes, 9 AM - 6 PM")
    }
}

@Composable
fun ProviderDetailItem(label: String, value: String) {
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
        HorizontalDivider(color = Color.Gray, thickness = 1.dp)
    }
}
