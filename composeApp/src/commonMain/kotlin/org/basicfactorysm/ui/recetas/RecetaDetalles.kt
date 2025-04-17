import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.avena
import basicfactorysm.composeapp.generated.resources.avena_porridge
import basicfactorysm.composeapp.generated.resources.huevo
import basicfactorysm.composeapp.generated.resources.leche_almendra
import basicfactorysm.composeapp.generated.resources.scoff_proteina
import basicfactorysm.composeapp.generated.resources.tortitas
import moe.tlaster.precompose.navigation.Navigator
import org.basicfactorysm.model.Ingrediente
import org.basicfactorysm.model.Receta
import org.basicfactorysm.presentacion.RecetasViewModel
import org.basicfactorysm.ui.genericos.TopBarBasicFactory
import org.basicfactorysm.utils.backgroundApp
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecetaDetalles(nav: Navigator, recetasViewModel: RecetasViewModel) {

    val receta = recetasViewModel.recetaSeleccionada.value
    val listaIngredientes = receta.ingredientes
    val paddingHorizontal = 20.dp
    Column(modifier = Modifier.fillMaxSize().background(backgroundApp)) {

        TopBarBasicFactory("Detalles", nav)
        Box(modifier = Modifier.weight(0.40f).padding(horizontal = paddingHorizontal)) {
            ImagenReceta(receta)
        }

        Box(
            modifier = Modifier.weight(0.15f).padding(horizontal = paddingHorizontal)
        ) {
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                TituloMasDatos(receta)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        Box(modifier = Modifier.weight(0.15f).padding(horizontal = paddingHorizontal)) {
            Column {
                Descripcion(receta)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        Box(modifier = Modifier.weight(0.30f).padding(horizontal = paddingHorizontal)) {
            Column {
                Text(
                    "Ingredientes",
                    fontSize = 25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                ListaIngredientes(listaIngredientes)
            }
        }
    }

}

@Composable
fun ImagenReceta(receta: Receta) {

    val imgReceta = painterResource(
        when (receta.nombre) {
            "Avena Proteica" -> Res.drawable.avena_porridge
            "Tortitas" -> Res.drawable.tortitas
            else -> Res.drawable.tortitas
        }
    )

    Image(
        imgReceta,
        contentDescription = "imgReceta",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
        /*modifier = Modifier.background(
            shape = RoundedCornerShape(20.dp),
            color = Color.White
        ).clip(RoundedCornerShape(20.dp))*/
    )
}

@Composable
fun TituloMasDatos(r: Receta) {

    val colorText = Color.White

    Text(r.nombre, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = colorText)
    Spacer(modifier = Modifier.height(10.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            Icons.Default.Schedule,
            contentDescription = "iconMins",
            tint = Color.White,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            r.tiempo.toString() + " mins",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorText
        )
        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            Icons.Default.MonitorHeart,
            contentDescription = "iconDifficulty",
            tint = Color.White,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            r.dificultad,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorText
        )

        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            Icons.Default.LocalFireDepartment,
            contentDescription = "iconCalories",
            tint = Color.White,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            r.calorias.toString() + " cal",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorText
        )
    }
}

@Composable
fun Descripcion(r: Receta) {
    val colorText = Color.White

    Text("Descripción", fontSize = 25.sp, color = colorText, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(10.dp))
    Text(r.descripcion, fontSize = 20.sp, color = colorText)
}

@Composable
fun ListaIngredientes(listaIngredientes: List<Ingrediente>) {
    LazyColumn {

        items(listaIngredientes) {
            ItemIngrediente(it)
        }
    }
}

@Composable
fun ItemIngrediente(i: Ingrediente) {
    val colorText = Color.White

    val imgIngrediente = painterResource(
        when (i.nombre) {
            "Huevos" -> Res.drawable.huevo
            "Harina de avena" -> Res.drawable.avena
            "Scoff Proteina" -> Res.drawable.scoff_proteina
            "Leche de almendra" -> Res.drawable.leche_almendra
            "Avena" -> Res.drawable.avena

            else -> Res.drawable.huevo
        }
    )

    val cantidadMedida = i.cantidad.toString() + " " + i.medida

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imgIngrediente,
            contentDescription = "imageIngrediente",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(i.nombre, fontSize = 16.sp, color = colorText)

        Spacer(modifier = Modifier.weight(1f))
        Text(cantidadMedida, fontSize = 16.sp, color = colorText)
    }

}
