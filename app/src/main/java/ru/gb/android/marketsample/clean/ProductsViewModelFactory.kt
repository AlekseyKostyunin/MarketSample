package ru.gb.android.marketsample.clean

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.marketsample.clean.domain.product.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.domain.promo.ConsumePromosUseCase
import ru.gb.android.marketsample.clean.presentation.product.ProductVOFactory
import ru.gb.android.marketsample.clean.presentation.product.ProductsViewModel
import ru.gb.android.marketsample.clean.presentation.promo.PromoVOMapper
import ru.gb.android.marketsample.clean.presentation.promo.PromoViewModel

class ProductsViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
    private val promoVOMapper: PromoVOMapper,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    productVOFactory = productVOFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }

            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoViewModel(
                    consumePromosUseCase = consumePromosUseCase,
                    promoVOMapper = promoVOMapper,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
