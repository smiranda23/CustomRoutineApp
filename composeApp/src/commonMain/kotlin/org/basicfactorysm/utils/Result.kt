package org.basicfactorysm.utils

sealed class Result(
    val data: Any? = null,
    val message: String? = null
)