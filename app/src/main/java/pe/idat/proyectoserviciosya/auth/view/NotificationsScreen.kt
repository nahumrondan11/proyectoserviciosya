package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.R
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notificaciones") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFA726))
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
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                NotificationList()
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(Color.White)
            ) {
                Button(
                    onClick = { /* revisar luego la implementación de la config */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Configuración de Notificaciones", color = Color.White)
                }
            }
        }
    )
}

data class Notification(val icon: Int, val message: String, val timestamp: String)

@Composable
fun NotificationList() {
    val notifications = listOf(
        Notification(R.drawable.ic_review, "Has recibido una nueva reseña de Juan Perez", "10:30 AM, Hoy"),
        Notification(R.drawable.ic_payment, "Se ha recibido el pago de Ana Gomez por $50", "9:15 AM, Hoy"),

    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(notifications) { notification ->
            NotificationItem(notification)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Implementar lógica de acción de notificación */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = notification.icon),
                contentDescription = null,
                tint = Color(0xFFFFA726),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = notification.message,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = notification.timestamp,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
