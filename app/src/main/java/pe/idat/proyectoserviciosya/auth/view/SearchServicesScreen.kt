package pe.idat.proyectoserviciosya.auth.view

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel
import pe.idat.proyectoserviciosya.auth.data.network.response.Categoria
import pe.idat.proyectoserviciosya.auth.data.network.response.DepartamentoSer
import pe.idat.proyectoserviciosya.core.dataclass.Servicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchServicesScreen(
    navController: NavController,
    floatingButtonViewModel: FloatingButtonViewModel,
    serviciosViewModel: ServiciosViewModel = viewModel(),

) {
    val snackbarHostState = remember { SnackbarHostState() }

    val serviciosFiltrados by serviciosViewModel.serviciosFiltrados.observeAsState(emptyList())
    val categorias by serviciosViewModel.categorias.observeAsState(emptyList())
    val departamentos by serviciosViewModel.departamentoser.observeAsState(emptyList())

    var nombreBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<Categoria?>(null) }
    var departamentoSeleccionado by remember { mutableStateOf<DepartamentoSer?>(null) }
    var precioMinimo by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        floatingButtonViewModel.showButton()
        serviciosViewModel.obtenerServiciosNoEliminados()
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
                    SearchBar(
                        nombreBusqueda = nombreBusqueda,
                        onSearchQueryChange = { nombreBusqueda = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Filters(
                        categorias = categorias,
                        categoriaSeleccionada = categoriaSeleccionada,
                        onCategoriaChange = { categoriaSeleccionada = it },
                        departamentos = departamentos,
                        departamentoSeleccionado = departamentoSeleccionado,
                        onDepartamentoChange = { departamentoSeleccionado = it },
                        precioMinimo = precioMinimo,
                        onPrecioChange = { precioMinimo = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        if (departamentoSeleccionado == null) {
                            Log.e("SearchServicesScreen", "No se seleccionó ningún departamento")
                        } else                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            {
                            Log.d("SearchServicesScreen", "ID de Departamento Seleccionado: ${departamentoSeleccionado!!.id}")
                        }
                        serviciosViewModel.buscarServicios(
                            nombreBusqueda,
                            categoriaSeleccionada?.id,
                            departamentoSeleccionado?.id,
                            precioMinimo
                        )
                    }) {
                        Text("Buscar")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ServiceResults(navController, serviciosFiltrados)
                }
            }
        }
    )
}

@Composable
fun ServiceResults(navController: NavController, servicios: List<Servicio>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(servicios) { servicio ->
            ServiceItem(navController, servicio)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun SearchBar(nombreBusqueda: String, onSearchQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = nombreBusqueda,
        onValueChange = onSearchQueryChange,
        label = { Text("Ingrese su búsqueda") },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
        },
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun Filters(
    categorias: List<Categoria>,
    categoriaSeleccionada: Categoria?,
    onCategoriaChange: (Categoria) -> Unit,
    departamentos: List<DepartamentoSer>,
    departamentoSeleccionado: DepartamentoSer?,
    onDepartamentoChange: (DepartamentoSer) -> Unit,
    precioMinimo: Float,
    onPrecioChange: (Float) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp)) {

        // Selección de Categoría
        DropdownMenuSample(
            selectedCategory = categoriaSeleccionada?.nombre ?: "Categoría",
            items = categorias.map { it.nombre },
            onItemSelected = { nombre ->
                val categoria = categorias.firstOrNull { it.nombre == nombre }
                categoria?.let { onCategoriaChange(it) }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Selección de Departamento
        DropdownMenuSample(
            selectedCategory = departamentoSeleccionado?.nombre ?: "Departamento",
            items = departamentos.map { it.nombre },
            onItemSelected = { nombre ->
                val departamento = departamentos.firstOrNull { it.nombre == nombre }
                departamento?.let { onDepartamentoChange(it) }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Rango de precios: ${precioMinimo.toInt()}", color = Color.Gray)
        Slider(
            value = precioMinimo,
            onValueChange = onPrecioChange,
            valueRange = 1f..1000f,
            steps = 5,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DropdownMenuSample(
    selectedCategory: String,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable { expanded = true }) {
        Text(
            text = selectedCategory,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.Gray
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
fun ServiceItem(navController: NavController, servicio: Servicio) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${Ruta.PROVIDER_PROFILE_SCREEN}/${servicio.idServicio}") },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_picture), // Reemplaza con la imagen correcta
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = servicio.nombreUsuario,
                    color = Color.Black,
                    fontSize = 18.sp
                )
                Text(
                    text = servicio.nombreServicio,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = servicio.ubicacion,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "$ ${servicio.precio}",
                    color = Color.Black,
                    fontSize = 14.sp
                )
                Text(
                    text = "Calificación: ${"★".repeat(servicio.calificacion)}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(
                onClick = { navController.navigate("${Ruta.PROVIDER_PROFILE_SCREEN}/${servicio.idServicio}")  }
            ) {
                Text(text = "Ver más", color = Color.Blue)
            }
        }
    }
}
