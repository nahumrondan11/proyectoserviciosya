package pe.idat.proyectoserviciosya.auth.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel
import pe.idat.proyectoserviciosya.auth.data.network.response.ServiceDetails
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderProfileScreen(
    navController: NavController,
    idServicio: Int,
    floatingButtonViewModel: FloatingButtonViewModel,
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Estado para guardar los detalles del servicio
    var serviceDetails by remember { mutableStateOf<ServiceDetails?>(null) }

    LaunchedEffect(idServicio) {
        floatingButtonViewModel.showButton()

        Log.d("ProviderProfileScreen", "Fetching service details for id $idServicio")

        // Llamada al API para obtener los detalles del servicio
        serviceDetails = serviciosViewModel.getDetalleServicio(idServicio)
        Log.d("ProviderProfileScreen", "Detalles obtenidos: $serviceDetails")
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Servicio") },
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
                serviceDetails?.let { details ->
                    ProviderProfileContent(details)
                } ?: Text(text = "Cargando...", color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(Color(0xFF2C2C2E))
            ) {
                Button(
                    onClick = { navController.navigate("${Ruta.PAGO_SCREEN}/${idServicio}") },
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
fun ProviderProfileContent(serviceDetails: ServiceDetails) {
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

        // Nombre del proveedor
        Text(
            text = serviceDetails.nombreUsuario,
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Tabla de detalles del proveedor
        ProviderDetailGrid(serviceDetails)
    }
}

@Composable
fun ProviderDetailGrid(serviceDetails: ServiceDetails) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ProviderDetailItem(label = "Categoría de Servicios", value = serviceDetails.categoria)
        ProviderDetailItem(label = "Descripción", value = serviceDetails.descripcion)
        ProviderDetailItem(label = "Tarifas", value = "$${serviceDetails.tarifa} por hora")
        ProviderDetailItem(label = "Disponibilidad", value = serviceDetails.disponibilidad)
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
        Divider(color = Color.Gray, thickness = 1.dp)
    }
}