package pe.idat.proyectoserviciosya.auth.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.data.network.request.ActualizarUsuarioRequest
import pe.idat.proyectoserviciosya.auth.data.network.response.Departamento
import pe.idat.proyectoserviciosya.auth.data.network.response.Pais
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel
import pe.idat.proyectoserviciosya.auth.viewmodel.UpdatePerfilViewModel
import pe.idat.proyectoserviciosya.core.dataclass.SessionManager
import androidx.compose.ui.res.painterResource as painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInfUserScreen(
    navController: NavController,
    serviciosViewModel: ServiciosViewModel = viewModel(),
    updatePerfilViewModel: UpdatePerfilViewModel = viewModel()
) {
    val context = LocalContext.current

    val userId = SessionManager.userId
    val scope = rememberCoroutineScope()
    val paises by updatePerfilViewModel.paises.observeAsState(emptyList())
    val departamentos by updatePerfilViewModel.departamentos.observeAsState(emptyList())

    var nombre by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var apellido by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var telefono by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var direccion by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var departamento by remember { mutableStateOf<Departamento?>(null) }
    var pais by remember { mutableStateOf<Pais?>(null) }
    var email by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var contrasena by remember { mutableStateOf(TextFieldValue("")) }
    var cuentaBancaria by remember { mutableStateOf(TextFieldValue("Sin datos")) }
    var opcionesPago by remember { mutableStateOf(listOf<Int>()) }

    // Esta variable se usa para controlar si el usuario ha ingresado una nueva contraseña
    var contrasenaIngresada by remember { mutableStateOf(false) }

    // Mostrar alertas
    var showAlert by remember { mutableStateOf(false) }
    var alertMessage by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        updatePerfilViewModel.obtenerPaises()
        userId?.let {
            scope.launch {
                val userInfo = serviciosViewModel.getUserProfile(it)
                userInfo?.let { profile ->
                    nombre = TextFieldValue(profile.nombre ?: "Sin datos")
                    apellido = TextFieldValue(profile.apellido ?: "Sin datos")
                    telefono = TextFieldValue(profile.telefono ?: "Sin datos")
                    direccion = TextFieldValue(profile.direccion ?: "Sin datos")
                    email = TextFieldValue(profile.correoelectronico ?: "Sin datos")
                    cuentaBancaria = TextFieldValue(profile.cuentabancaria ?: "Sin datos")
                    opcionesPago = profile.opcionesPago.map { it.idtipoopcionpago }
                    pais = Pais(profile.idpais, profile.paisNombre ?: "Sin datos")
                    updatePerfilViewModel.obtenerDepartamentos(profile.idpais)
                    departamento = Departamento(profile.iddepartamento, profile.departamentoNombre ?: "Sin datos")
                    // Contraseña se deja vacía deliberadamente
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actualización de Datos", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6A1B9A))
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_picture),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)
                                .padding(16.dp)
                        )
                    }
                }
                item {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                item {
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Celular") },
                        leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        leadingIcon = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = {
                            contrasena = it
                            contrasenaIngresada = it.text.isNotEmpty()
                        },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
                item {
                    // Dropdown para seleccionar país
                    if (paises.isNotEmpty()) {
                        DropdownMenuSample(
                            selectedOption = pais,
                            options = paises,
                            onOptionSelected = {
                                pais = it
                                updatePerfilViewModel.obtenerDepartamentos(it.idpais)
                            },
                            label = "País",
                            getText = { it.nombre }
                        )
                    } else {
                        Text("Cargando países...", modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    // Dropdown para seleccionar departamento
                    if (departamentos.isNotEmpty()) {
                        DropdownMenuSample(
                            selectedOption = departamento,
                            options = departamentos,
                            onOptionSelected = { departamento = it },
                            label = "Departamento",
                            getText = { it.nombre }
                        )
                    } else {
                        Text("Cargando departamentos...", modifier = Modifier.padding(8.dp))
                    }
                }
                item {
                    OutlinedTextField(
                        value = cuentaBancaria,
                        onValueChange = { cuentaBancaria = it },
                        label = { Text("Número de Cuenta Bancaria") },
                        leadingIcon = { Icon(Icons.Filled.AccountBalance, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
                // Opciones de Pago
                item {
                    Text("Opciones de Pago", modifier = Modifier.padding(8.dp), fontSize = 16.sp)
                }
                item {
                    Row(modifier = Modifier.padding(8.dp)) {
                        Checkbox(
                            checked = opcionesPago.contains(100),
                            onCheckedChange = {
                                opcionesPago = if (it) opcionesPago + 100 else opcionesPago - 100
                            }
                        )
                        Text("Transferencia", modifier = Modifier.align(Alignment.CenterVertically))

                        Spacer(modifier = Modifier.width(8.dp))

                        Checkbox(
                            checked = opcionesPago.contains(101),
                            onCheckedChange = {
                                opcionesPago = if (it) opcionesPago + 101 else opcionesPago - 101
                            }
                        )
                        Text("Efectivo", modifier = Modifier.align(Alignment.CenterVertically))

                        Spacer(modifier = Modifier.width(8.dp))

                        Checkbox(
                            checked = opcionesPago.contains(102),
                            onCheckedChange = {
                                opcionesPago = if (it) opcionesPago + 102 else opcionesPago - 102
                            }
                        )
                        Text("Plin_Yape", modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
                item {
                    Button(
                        onClick = {
                            // Validaciones de campos vacíos
                            when {
                                nombre.text.isEmpty() -> {
                                    alertMessage = "El campo Nombre es obligatorio."
                                    showAlert = true
                                }
                                apellido.text.isEmpty() -> {
                                    alertMessage = "El campo Apellido es obligatorio."
                                    showAlert = true
                                }
                                telefono.text.isEmpty() -> {
                                    alertMessage = "El campo Celular es obligatorio."
                                    showAlert = true
                                }
                                direccion.text.isEmpty() -> {
                                    alertMessage = "El campo Dirección es obligatorio."
                                    showAlert = true
                                }
                                email.text.isEmpty() -> {
                                    alertMessage = "El campo Correo Electrónico es obligatorio."
                                    showAlert = true
                                }
                                contrasenaIngresada && contrasena.text.isEmpty() -> {
                                    alertMessage = "El campo Contraseña no puede estar vacío si se está editando."
                                    showAlert = true
                                }
                                else -> {
                                    scope.launch {
                                        userId?.let {
                                            val updateRequest = ActualizarUsuarioRequest(
                                                nombre = nombre.text,
                                                apellido = apellido.text,
                                                telefono = telefono.text,
                                                direccion = direccion.text,
                                                iddepartamento = departamento?.iddepartamento ?: 0,
                                                idpais = pais?.idpais ?: 0,
                                                correoelectronico = email.text,
                                                contrasena = if (contrasenaIngresada) contrasena.text else null,
                                                cuentabancaria = cuentaBancaria.text,
                                                fotoperfil = "ruta/tes",  // Luego actualizar esto con la ruta correcta si la tienes
                                                opcionesPago = opcionesPago
                                            )

                                            serviciosViewModel.actualizarUsuario(
                                                idUsuario = it,
                                                request = updateRequest,
                                                onSuccess = {
                                                    // Actualizar los valores en la pantalla con los nuevos datos
                                                    nombre = TextFieldValue(updateRequest.nombre)
                                                    apellido = TextFieldValue(updateRequest.apellido)
                                                    telefono = TextFieldValue(updateRequest.telefono)
                                                    direccion = TextFieldValue(updateRequest.direccion)
                                                    email = TextFieldValue(updateRequest.correoelectronico)
                                                    cuentaBancaria = TextFieldValue(updateRequest.cuentabancaria)
                                                    opcionesPago = updateRequest.opcionesPago
                                                    // Nota: contrasena puede dejarse vacía ya que no es necesario mostrarla

                                                    // Mostrar el diálogo de éxito
                                                    alertMessage = "Datos guardados exitosamente."
                                                    showAlert = true
                                                },
                                                onError = { errorMessage ->
                                                    alertMessage = "Error: $errorMessage"
                                                    showAlert = true
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("Guardar Cambios")
                    }
                }
            }
        }
    )

    // Mostrar alerta en caso de error o éxito
    if (showAlert) {
        AlertDialog(
            onDismissRequest = {
                showAlert = false
            },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text("OK")
                }
            },
            title = { Text(if (alertMessage == "Datos guardados exitosamente.") "Éxito" else "Error") },
            text = { Text(alertMessage) }
        )
    }
}
