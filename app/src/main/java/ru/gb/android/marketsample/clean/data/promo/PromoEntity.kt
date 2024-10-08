package ru.gb.android.marketsample.clean.data.promo

import kotlinx.serialization.Serializable

@Serializable
class PromoEntity (
    val id: String,
    val name: String,
    val image: String,
    val discount: Double,
    val description: String,
    val type: String,
    val products: List<String>,
)