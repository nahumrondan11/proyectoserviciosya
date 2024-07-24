package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pago Seguro") },
                actions = {
                    IconButton(onClick = { /* TODO: Implementar opciones */ }) {
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
                ServiceSummary()
                PaymentForm(navController)
            }
        }
    )
}

@Composable
fun ServiceSummary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Resumen del Servicio", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Proveedor: Carolina", fontSize = 16.sp)
        Text(text = "Servicio: Corte de Cabello", fontSize = 16.sp)
        Text(text = "Fecha: 23/07/2024", fontSize = 16.sp)
        Text(text = "Costo: S/ 100.00", fontSize = 16.sp)
    }
}

@Composable
fun PaymentForm(navController: NavController) {
    var selectedPaymentMethod by remember { mutableStateOf("Tarjeta") }

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
            PaymentOption(
                icon = Icons.Default.CreditCard,
                label = "Tarjeta",
                selected = selectedPaymentMethod == "Tarjeta"
            ) { selectedPaymentMethod = "Tarjeta" }
            PaymentOption(
                icon = Icons.Default.Money,
                label = "Efectivo",
                selected = selectedPaymentMethod == "Efectivo"
            ) { selectedPaymentMethod = "Efectivo" }
            PaymentOption(
                icon = Icons.Default.Check,
                label = "Yape/Plin",
                selected = selectedPaymentMethod == "Yape/Plin"
            ) { selectedPaymentMethod = "Yape/Plin" }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedPaymentMethod) {
            "Tarjeta" -> CardPaymentForm(navController)
            "Efectivo" -> CashPaymentForm(navController)
            "Yape/Plin" -> YapePlinPaymentForm(navController)
        }
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

@Composable
fun CardPaymentForm(navController: NavController) {
    var cardName by remember { mutableStateOf(TextFieldValue("")) }
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }
    var expirationDate by remember { mutableStateOf(TextFieldValue("")) }
    var cvv by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        TextField(value = cardName, onValueChange = { cardName = it }, label = { Text("Nombre en la tarjeta") })
        TextField(value = cardNumber, onValueChange = { cardNumber = it }, label = { Text("Número de tarjeta") })
        TextField(value = expirationDate, onValueChange = { expirationDate = it }, label = { Text("Fecha de expiración") })
        TextField(value = cvv, onValueChange = { cvv = it }, label = { Text("Código de seguridad (CVV)") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Implementar lógica de pago */ }) {
            Text(text = "Confirmar Pago")
        }
    }
}

@Composable
fun CashPaymentForm(navController: NavController) {
    Column {
        Text(text = "Método de pago seleccionado: Efectivo")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Implementar lógica de generación de comprobante */ }) {
            Text(text = "Pagar")
        }
    }
}

@Composable
fun YapePlinPaymentForm(navController: NavController) {
    Column {
        Text(text = "Método de pago seleccionado: Yape/Plin")
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.qr_yape), contentDescription = "QR Yape", modifier = Modifier.size(100.dp))
                Text(text = "Yape")
                Text(text = "926 438 306")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.qr_plin), contentDescription = "QR Plin", modifier = Modifier.size(100.dp))
                Text(text = "Plin")
                Text(text = "926 438 306")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO: Implementar lógica de generación de comprobante */ }) {
            Text(text = "Pagar")
        }
    }
}
