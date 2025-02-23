package org.basicfactorysm.presentacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.viewmodel.ViewModel
import org.basicfactorysm.domain.IGlobalRepository
import org.basicfactorysm.model.Receta
import org.basicfactorysm.navigation.Rutas
import org.basicfactorysm.utils.Categoria

class RecetasViewModel(private val repository: IGlobalRepository) : ViewModel() {

    private var _listaCategorias by mutableStateOf(listOf<Categoria>())
    val listaCategorias: List<Categoria> get() = _listaCategorias

    private var categorias = mutableListOf<Categoria>()

    private var _listaRecetas by mutableStateOf(listOf<Receta>())
    val listaRecetas: List<Receta> get() = _listaRecetas
    var recetaSeleccionada = mutableStateOf(Receta(0, "", ""))

    init {
        categorias.add(Categoria(1, "Desayunos", true))
        categorias.add(Categoria(2, "Comida"))
        categorias.add(Categoria(3, "Postres"))
        categorias.add(Categoria(4, "Cena"))
        categorias.add(Categoria(5, "Snacks"))
        _listaCategorias = categorias
        _listaRecetas = repository.getRecetasByCategoria("Desayunos")
    }


    fun onClickCategoria(c: Categoria) {

        val idCategoria = c.id
        val nomCategoria = c.nombre

        categorias = categorias.map { cat ->
            cat.copy(clicado = cat.id == idCategoria)
        }.toMutableList()
        _listaCategorias = categorias

        _listaRecetas = repository.getRecetasByCategoria(nomCategoria)
        recetaSeleccionada.value = Receta(0, "", "")

    }

    fun onClickReceta(r: Receta, nav: Navigator) {

        recetaSeleccionada.value = r

        nav.navigate(Rutas.DetalleReceta.ruta)


    }


}


