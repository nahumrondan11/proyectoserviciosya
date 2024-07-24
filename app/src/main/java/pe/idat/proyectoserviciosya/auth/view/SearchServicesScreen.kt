package pe.idat.proyectoserviciosya.auth.view

import pe.idat.proyectoserviciosya.core.ruteo.Ruta


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchServicesScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        floatingButtonViewModel.showButton()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("ServiciosYa", color = Color.White) },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ir a mi Perfil") },
                            onClick = {
                                expanded = false
                                navController.navigate(Ruta.USER_PROFILE_SCREEN)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
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
                    .background(Color(0xFF1E1E1E))
                    .padding(paddingValues)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    SearchBar()
                    Spacer(modifier = Modifier.height(16.dp))
                    Filters()
                    Spacer(modifier = Modifier.height(16.dp))
                    ServiceResults(navController)
                }
            }
        }
    )
}

@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text("Search") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search), // Reemplaza con el ícono correcto
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun Filters() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        // Categoría
        var selectedCategory by remember { mutableStateOf("Categoría") }
        DropdownMenuSample(selectedCategory) { selectedCategory = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Ubicación
        var location by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Ubicación") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Rango de precios
        var priceRange by remember { mutableStateOf(0f) }
        Text(text = "Rango de precios: ${priceRange.toInt()}", color = Color.Gray)
        Slider(
            value = priceRange,
            onValueChange = { priceRange = it },
            valueRange = 0f..1000f,
            steps = 5,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DropdownMenuSample(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Categoría 1", "Categoría 2", "Categoría 3", "Categoría 4")

    Box(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Text(
            text = selectedCategory,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
            color = Color.Gray
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ServiceResults(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(10) { index ->
            ServiceItem(navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ServiceItem(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Ruta.PROVIDER_PROFILE_SCREEN) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture), // luego reemp con el ID correcto de la imagen
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Nombre del Proveedor",
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = "Categoría del servicio",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Ubicación",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "$ Precio del servicio",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Calificación: ★★★★☆",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { navController.navigate(Ruta.PROVIDER_PROFILE_SCREEN) }
            ) {
                Text(text = "Ver más", color = Color.Blue)
            }
        }
    }
}
