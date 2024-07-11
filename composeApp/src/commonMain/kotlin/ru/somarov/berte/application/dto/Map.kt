package ru.somarov.berte.application.dto

import kotlinx.serialization.Serializable

// 3D позиция в игровом мире
@Serializable
data class MapPosition(
    // x,y,z позиция в мире
    val x: Long,
    val y: Long,
    val z: Long,
    // доступные пути
    val availableMapRoute: List<MapDirection> = emptyList(),
)

// 3D направления
@Serializable
enum class MapDirection {
    Left,
    Right,
    Top,
    Bottom,
    Up,
    Down
}

@Serializable
data class MapRoute(
    // направление
    val direction: MapDirection,

    // дух пути
    // в минуса опасный сулящий потери в плюса безопасный сулящий находки
    // здесь можно использовать кубик на какой либо стейт героя
    // можно это делать неявно не показывая этот процесс, а можно и явно как хочется
    // проверка может быть провалена на все путешествие
    // например так и не удалось открыть дверь
    val spirit: Long,

    // позиция окончания движения по направлению, указывает так же на
    // длительность перехода - соотвествует разнице между текущей позицией
    // и позицией окончания движения
    // длительность перехода влияет на то сколько событий игрового мира пройдут
    // пока мы идем и сколько раз придется проходить проверку на spirit
    // если destination то пройти нельзя ни при какой проверке
    val destination: MapPosition?,

    // описание того что мы видими по направлению с текущего места
    val description: String
)

// Пример route
// В destination пока не видны route, потому что мы там еще небыли
// route видны до определенной глубины и в зависимости от длинны пути
// даже если мы там и были route могут не показываться так как прошло врмемя
// с момента посещения
// иногда route могут показыватсья потому как кто то о них рассказал
@Suppress("Indentation", "MagicNumber")
private val sampleRoute = MapRoute(
    direction = MapDirection.Down,
    destination = MapPosition(123L, 324L, 12L),
    spirit = -1,
    description = "Мы видим старую каменную лестницу которая ведет в подземелье, " +
            "оно не выглядит таким уж и страшным, " +
            "но некоторые ступени повреждены и можно оступиться"
)
