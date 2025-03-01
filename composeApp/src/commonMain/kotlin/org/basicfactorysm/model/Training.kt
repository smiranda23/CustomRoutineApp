package org.basicfactorysm.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Training(
    var id: Int,
    var name: String,
    var dateTime: LocalDateTime,
    var setsFinished: Int
) {
}
