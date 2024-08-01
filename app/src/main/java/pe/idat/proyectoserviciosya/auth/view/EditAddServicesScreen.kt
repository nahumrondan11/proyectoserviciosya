package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.core.dataclass.Service

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAddServicesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Servicios") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        },
        content = { padding ->
            EditAddServicesContent(navController, Modifier.padding(padding))
        }
    )
}

@Composable
fun EditAddServicesContent(navController: NavController, modifier: Modifier = Modifier) {
    val services = remember { mutableStateListOf(
        Service("Electricista", "Servicios eléctricos generales", "50 por hora", "Lunes a Viernes, 9 AM - 6 PM"),
        Service("Plomero", "Reparaciones de plomería", "40 por hora", "Lunes a Viernes, 10 AM - 5 PM")
    ) }
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var rates by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Button(
                onClick = {
                    selectedService = null
                    name = ""
                    description = ""
                    rates = ""
                    availability = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar Servicio")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(services.filter { !it.isDeleted }) { service ->
            ServiceItem(service,
                onEdit = {
                    selectedService = service
                    name = service.name
                    description = service.description
                    rates = service.rate
                    availability = service.availability
                },
                onDelete = {
                    coroutineScope.launch {
                        services.find { it == service }?.apply { isDeleted = true }
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            ServiceForm(
                name = name,
                onNameChange = { name = it },
                description = description,
                onDescriptionChange = { description = it },
                rates = rates,
                onRatesChange = { rates = it },
                availability = availability,
                onAvailabilityChange = { availability = it },
                onSave = {
                    if (selectedService != null) {
                        selectedService!!.apply {
                            this.name = name
                            this.description = description
                            this.rate = rates
                            this.availability = availability
                        }
                    } else {
                        services.add(Service(
                            name = name,
                            description = description,
                            rate = rates,
                            availability = availability
                        ))
                    }
                    selectedService = null
                    name = ""
                    description = ""
                    rates = ""
                    availability = ""
                },
                onDelete = {
                    selectedService?.let {
                        coroutineScope.launch {
                            services.find { it == it }?.apply { isDeleted = true }
                        }
                    }
                    selectedService = null
                    name = ""
                    description = ""
                    rates = ""
                    availability = ""
                },
                isUpdate = selectedService != null
            )
        }
    }
}

@Composable
fun ServiceItem(service: Service, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onEdit() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(service.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(service.description)
        }
        Row {
            IconButton(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun ServiceForm(
    name: String,
    onNameChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    rates: String,
    onRatesChange: (String) -> Unit,
    availability: String,
    onAvailabilityChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit,
    isUpdate: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Nombre del Servicio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text("Descripción del Servicio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = rates,
            onValueChange = onRatesChange,
            label = { Text("Tarifas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = availability,
            onValueChange = onAvailabilityChange,
            label = { Text("Disponibilidad (días y horas)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isUpdate) "Actualizar Servicio" else "Agregar Servicio")
        }
        if (isUpdate) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Eliminar Servicio")
            }
        }
    }
}