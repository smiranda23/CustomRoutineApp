package org.basicfactorysm.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Training(
    var id: Int,
    var name: String,
    var duration: Int,
    var date: String,
    var setsFinished: Int
) {
}
