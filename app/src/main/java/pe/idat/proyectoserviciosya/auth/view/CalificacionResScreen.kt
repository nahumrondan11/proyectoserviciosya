package pe.idat.proyectoserviciosya.auth.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
fun UserRatingsReviewsScreen(navController: NavController, floatingButtonViewModel: FloatingButtonViewModel) {
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
            ) {
                UserRatingsReviewsContent(navController)
            }
        }
    )
}

@Composable
fun UserRatingsReviewsContent(navController: NavController) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }

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

        // Sección de calificación
        Text(text = "Ingrese su calificación", fontSize = 20.sp, color = Color.Black)
        RatingBar(rating = rating.toFloat(), onRatingChanged = { rating = it })

        // Campo de texto para reseña
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
                .padding(8.dp)
        )

        // Botón de enviar reseña
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Luego impl cód para el envio a la bd
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar Reseña", color = Color.White)
        }

        // Cuadro de calificaciones del vendedor
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Calificaciones del Vendedor", fontSize = 20.sp, color = Color.Black)
        RatingBar(rating = 4.5f)
        UserReviewList()
    }
}

@Composable
fun UserReviewList() {
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
