package pe.idat.proyectoserviciosya.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel
import pe.idat.proyectoserviciosya.core.ruteo.Ruta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProviderRatingsReviewsScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calificaciones y Reseñas") },
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
                    .padding(paddingValues)
                    .background(Color(0xFF2C2C2E))
            ) {
                ProviderRatingsReviewsContent()
            }
        }
    )
}

@Composable
fun ProviderRatingsReviewsContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Calificaciones y Reseñas",
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Estrellas de calificación promedio
        Text(text = "General", fontSize = 20.sp, color = Color.Black)
        RatingBar(rating = 4.5f)

        // Reseñas anteriores
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Reseñas", fontSize = 20.sp, color = Color.Black)
        ProviderReviewList()  // Cambié el nombre aquí
    }
}

@Composable
fun RatingBar(rating: Float, onRatingChanged: (Int) -> Unit = {}) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRatingChanged(index + 1) }
            )
        }
    }
}

@Composable
fun ProviderReviewList() {  // Cambié el nombre aquí
    Column {
        repeat(5) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Usuario $index", fontSize = 18.sp, color = Color.Black)
                    RatingBar(rating = (3 + index % 3).toFloat())
                    Text(
                        text = "Reseña del usuario sobre el servicio recibido. Muy buen servicio, lo recomiendo.",
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
