package pe.idat.proyectoserviciosya.auth.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateInfUserScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actualización de Datos") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        },
        content = { padding ->
            UpdateInfUserContent(navController, Modifier.padding(padding))
        }
    )
}

@Composable
fun UpdateInfUserContent(navController: NavController, modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var cuentaBancaria by remember { mutableStateOf("") }
    var yape by remember { mutableStateOf(false) }
    var plin by remember { mutableStateOf(false) }
    var ambos by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF2F2F2)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen del usuario
        selectedImageUri?.let {
            Image(
                bitmap = loadImageBitmap(it).asImageBitmap(),
                contentDescription = "Foto del Usuario",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        } ?: run {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto del Usuario",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Actualizar Foto")
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Apellido") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = celular,
            onValueChange = { celular = it },
            label = { Text("Celular") },
            leadingIcon = { Icon(Icons.Filled.Phone, contentDescription = "Celular") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = cuentaBancaria,
            onValueChange = { cuentaBancaria = it },
            label = { Text("Número de Cuenta Bancaria") },
            leadingIcon = { Icon(Icons.Filled.AccountBalance, contentDescription = "Número de Cuenta Bancaria") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Opciones de Pago", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = yape,
                    onCheckedChange = {
                        yape = it
                        ambos = yape && plin
                    }
                )
                Text("Yape")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = plin,
                    onCheckedChange = {
                        plin = it
                        ambos = yape && plin
                    }
                )
                Text("Plin")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = ambos,
                    onCheckedChange = {
                        ambos = it
                        yape = it
                        plin = it
                    }
                )
                Text("Ambos")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Save changes logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios")
        }
    }
}

@Composable
fun loadImageBitmap(uri: Uri): Bitmap {
    // Aquí puedes cargar el bitmap desde el URI usando el método que prefieras
    // Para simplificar, vamos a retornar un Bitmap vacío
    return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
}