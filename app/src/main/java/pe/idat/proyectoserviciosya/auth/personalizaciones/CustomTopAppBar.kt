package pe.idat.proyectoserviciosya.auth.personalizaciones

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import pe.idat.proyectoserviciosya.auth.viewmodel.FloatingButtonViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CustomTopAppBar(
    title: String,
    floatingButtonViewModel: FloatingButtonViewModel,
    navController: NavController? = null
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = { floatingButtonViewModel.hideButton() }) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFA726))
    )
}
