package ru.gb.android.marketsample.clean.presentation.product

import ru.gb.android.marketsample.clean.domain.product.Product
import ru.gb.android.marketsample.clean.domain.promo.Promo

class ProductVOFactory {
    fun create(product: Product, promos: List<Promo>): ProductVO {
        val promoForProduct: Promo? = promos.firstOrNull { promo ->
            (promo is Promo.PromoForProducts &&
                    promo.products.any { productId -> productId == product.id })
        }
        return ProductVO(
            id = product.id,
            name = product.name,
            image = product.image,
            price = product.price,
            hasDiscount = promoForProduct != null,
            discount = (promoForProduct as? Promo.PromoForProducts)?.discount?.toInt() ?: 0
        )
    }
}