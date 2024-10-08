package ru.gb.android.marketsample.clean.domain.product

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.marketsample.clean.data.product.ProductRepository

class ConsumeProductsUseCase(
    private val productRepository: ProductRepository,
    private val productDomainMapper: ProductDomainMapper,
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.consumeProducts()
            .map{ products -> products.map(productDomainMapper::fromEntity) }
    }
}