package org.basicfactorysm.data

import androidx.compose.runtime.mutableStateOf
import basicfactorysm.composeapp.generated.resources.Res
import basicfactorysm.composeapp.generated.resources.avena_porridge
import basicfactorysm.composeapp.generated.resources.tortitas
import kotlinx.datetime.LocalDate
import org.basicfactorysm.model.Clase
import org.basicfactorysm.model.Ejercicio
import org.basicfactorysm.model.Entrenador
import org.basicfactorysm.model.Ingrediente
import org.basicfactorysm.model.Receta
import org.basicfactorysm.model.Rutina
import org.basicfactorysm.model.Serie

object DataManager {

    val ListaRutinas = mutableListOf(
        Rutina(1, "Empujes 1", 1, 0, 0),
        //Rutina(2, "Espalda", "Rutina avanzada!", 4, 30, ListaRutinaEspalda),
        //Rutina(3, "Pierna", "Rutina media!", 4, 30, ListaRutinaPierna)
    )




    /*val ListaRutinaEspalda: List<Ejercicio> = listOf(
        Ejercicio(5, "Dominadas", 4, 10, 90, "ruta/img"),
        Ejercicio(6, "Jalón al pecho", 4, 10, 120, "ruta/img"),
        Ejercicio(7, "Remo", 4, 10, 120, "ruta/img"),
        Ejercicio(8, "Peso muerto", 4, 8, 120, "ruta/img")
    )

    val ListaRutinaPierna: List<Ejercicio> = listOf(
        Ejercicio(5, "Sentadillas", 4, 10, 90, "ruta/img"),
        Ejercicio(6, "Prensa", 4, 10, 120, "ruta/img"),
        Ejercicio(7, "Zancadas", 4, 10, 120, "ruta/img"),
        Ejercicio(8, "Aductores", 4, 8, 120, "ruta/img")
    )*/




    //------RESTO DE OBJETOS------//

    val ListaClases = mutableListOf(
        Clase(
            1,
            "Zumba",
            "Sala 1",
            LocalDate.parse("2025-01-25").toString(),
            "9:00-9:45",
            15,
            40,
            "Esther"
        ),
        Clase(
            2,
            "BodyPump",
            "Sala 2",
            LocalDate.parse("2025-01-25").toString(),
            "9:00-9:45",
            13,
            30,
            "Marcelo"
        ),
        Clase(
            3, "Funcional", "Sala 3",
            LocalDate.parse("2025-01-26").toString(), "9:00-9:45", 20, 25, "Pol"
        )
    )

    val ListaEntrenadores = mutableListOf(
        Entrenador(1, "Marcelo", "marcelo@gmail.com", "654887541"),
        Entrenador(2, "Alex", "alex@gmail.com", "65244123"),
        Entrenador(3, "Xavi", "xavi@gmail.com", "654892147")
    )

    val ListaIngredientesAvena = mutableListOf(
        Ingrediente(1, "Avena", 60, "g", "image"),
        Ingrediente(2, "Leche de almendra", 300, "ml", "image"),
        Ingrediente(3, "Scoff Proteina", 30, "g", "image"),
    )

    val ListaIngredientesTortitas = mutableListOf(
        Ingrediente(1, "Huevos", 3, "U", "image"),
        Ingrediente(2, "Harina de avena", 300, "g", "image"),
        Ingrediente(3, "Scoff Proteina", 30, "g", "image")
    )

    val ListaRecetas = mutableListOf(
        Receta(
            1,
            "Avena Proteica",
            "Desayunos",
            "Para desayunar",
            350,
            3,
            "Fácil",
            ListaIngredientesAvena
        ),
        Receta(
            2,
            "Tortitas",
            "Desayunos",
            "Tortitas con alto valor proteico, fácil y rápido!",
            300,
            5,
            "Fácil",
            ListaIngredientesTortitas
        ),
        Receta(3, "Pollo al horno", "Comidas"),
        Receta(4, "Patatas Chips", "Snacks"),
        Receta(5, "Ensala César", "Cenas")
    )

}