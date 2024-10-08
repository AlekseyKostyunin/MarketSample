package ru.gb.android.marketsample.clean.presentation.product

data class ProductVO(
    val id: String,
    val name: String,
    val image: String,
    val price: Double,
    val hasDiscount: Boolean,
    val discount: Int,
)