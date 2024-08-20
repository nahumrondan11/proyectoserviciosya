package pe.idat.proyectoserviciosya.auth.view

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pe.idat.proyectoserviciosya.auth.data.network.response.PaymentInfo
import pe.idat.proyectoserviciosya.auth.viewmodel.ServiciosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    idServicio: Int,
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    var paymentInfo by remember { mutableStateOf<PaymentInfo?>(null) }
    val scope = rememberCoroutineScope()
    val idUsuario = 100  // Suponiendo que idUsuario se obtiene de algún lugar

    LaunchedEffect(idServicio) {
        scope.launch {
            paymentInfo = serviciosViewModel.getPaymentInfo(idServicio)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pago Seguro") },
                actions = {
                    IconButton(onClick = { /* Opciones adicionales */ }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEDEDED))
                    .padding(paddingValues)
            ) {
                paymentInfo?.let { info ->
                    ServiceSummary(info)
                    PaymentForm(navController, info, idServicio, idUsuario, serviciosViewModel)
                } ?: Text("Cargando...", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    )
}

@Composable
fun ServiceSummary(paymentInfo: PaymentInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Resumen del Servicio", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Proveedor: ${paymentInfo.nombreUsuario}", fontSize = 16.sp)
        Text(text = "Servicio: ${paymentInfo.nombreServicio}", fontSize = 16.sp)
        Text(text = "Fecha: ${paymentInfo.fechaActual}", fontSize = 16.sp)
        Text(text = "Costo: S/ ${paymentInfo.costoServicio}", fontSize = 16.sp)
    }
}

@Composable
fun PaymentForm(
    navController: NavController,
    paymentInfo: PaymentInfo,
    idServicio: Int,
    idUsuario: Int,
    serviciosViewModel: ServiciosViewModel
) {
    var selectedPaymentMethod by remember { mutableStateOf(paymentInfo.tiposPago.firstOrNull()?.nombre ?: "Opción no disponible") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFC1E1C1), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "Forma de Pago", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            paymentInfo.tiposPago.forEach { tipoPago ->
                PaymentOption(
                    icon = when (tipoPago.nombre) {
                        "Transferencia" -> Icons.Default.Transform
                        "Efectivo" -> Icons.Default.Money
                        else -> Icons.Default.Check
                    },
                    label = tipoPago.nombre,
                    selected = selectedPaymentMethod == tipoPago.nombre,
                    onClick = {
                        selectedPaymentMethod = tipoPago.nombre
                    }
                )
            }
        }

        // Mostrar detalles adicionales según el método de pago seleccionado
        when (selectedPaymentMethod) {
            "Transferencia" -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cuenta Bancaria: ${paymentInfo.cuentaBancaria}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            "Plin_Yape" -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Teléfono: ${paymentInfo.telefono}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            "Efectivo" -> {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Coordinar con el proveedor. Teléfono: ${paymentInfo.telefono}",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val selectedTipoOpcionPago = paymentInfo.tiposPago.find { it.nombre == selectedPaymentMethod }?.idTipoOpcionPago
            if (selectedTipoOpcionPago != null) {
                serviciosViewModel.confirmarPago(
                    idUsuario = idUsuario,
                    idServicio = idServicio,
                    idTipoOpcionPago = selectedTipoOpcionPago,
                    onSuccess = {
                        dialogMessage = "Servicio contratado con éxito, se envió notificación al vendedor"
                        showDialog = true
                    },
                    onError = { errorMessage ->
                        dialogMessage = "Error al contratar el servicio: $errorMessage"
                        showDialog = true
                    }
                )
            } else {
                dialogMessage = "No se pudo confirmar el pago. Selecciona un método de pago válido."
                showDialog = true
            }
        }) {
            Text(text = "Pagar")
        }
    }

    // Mostrar el AlertDialog cuando se confirme el pago o haya un error
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Confirmación") },
            text = { Text(dialogMessage) }
        )
    }
}

@Composable
fun PaymentOption(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) Color.Blue else Color.Gray
        )
        Text(
            text = label,
            color = if (selected) Color.Blue else Color.Gray,
            fontSize = 14.sp
        )
    }
}