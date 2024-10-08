package ru.gb.android.marketsample.clean

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.android.marketsample.clean.data.product.ProductApiService
import ru.gb.android.marketsample.clean.data.product.ProductDataMapper
import ru.gb.android.marketsample.clean.data.product.ProductLocalDataSource
import ru.gb.android.marketsample.clean.data.product.ProductRemoteDataSource
import ru.gb.android.marketsample.clean.data.product.ProductRepository
import ru.gb.android.marketsample.clean.data.promo.PromoApiService
import ru.gb.android.marketsample.clean.data.promo.PromoDataMapper
import ru.gb.android.marketsample.clean.data.promo.PromoLocalDataSource
import ru.gb.android.marketsample.clean.data.promo.PromoRemoteDataSource
import ru.gb.android.marketsample.clean.data.promo.PromoRepository
import ru.gb.android.marketsample.clean.domain.product.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.domain.product.ProductDomainMapper
import ru.gb.android.marketsample.clean.domain.promo.ConsumePromosUseCase
import ru.gb.android.marketsample.clean.domain.promo.PromoDomainMapper
import ru.gb.android.marketsample.clean.presentation.product.ProductVOFactory
import ru.gb.android.marketsample.clean.presentation.promo.PromoVOMapper

object ServiceLocator {

    const val ENDPOINT = "https://makzimi.github.io/"

    lateinit var applicationContext: Context

    private var productRepositorySingleton: ProductRepository? = null
    private var promoRepositorySingleton: PromoRepository? = null
    private var retrofitSingleton: Retrofit? = null

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            consumeProductsUseCase = provideConsumeProductsUseCase(),
            productVOFactory = provideProductVOMapper(),
            consumePromosUseCase = provideConsumePromosUseCase(),
            promoVOMapper = providePromoVOMapper(),
        )
    }

    private fun provideConsumePromosUseCase(): ConsumePromosUseCase {
        return ConsumePromosUseCase(
            promoRepository = providePromoRepository(),
            promoDomainMapper = providePromoDomainMapper(),
        )
    }

    private fun providePromoRepository(): PromoRepository {
        val local = promoRepositorySingleton
        return local ?: run {
            val newPromoRepository = PromoRepository(
                promoLocalDataSource = providePromoLocalDataSource(),
                promoRemoteDataSource = providePromoRemoteDataSource(),
                promoDataMapper = providePromoDataMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            promoRepositorySingleton = newPromoRepository
            newPromoRepository
        }
    }

    private fun providePromoLocalDataSource(): PromoLocalDataSource {
        return PromoLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private fun providePromoRemoteDataSource(): PromoRemoteDataSource {
        return PromoRemoteDataSource(
            promoApiService = providePromoApiService(),
        )
    }

    private fun providePromoApiService(): PromoApiService {
        return provideRetrofit().create(PromoApiService::class.java)
    }

    private fun providePromoDataMapper(): PromoDataMapper {
        return PromoDataMapper()
    }

    private fun provideConsumeProductsUseCase(): ConsumeProductsUseCase {
        return ConsumeProductsUseCase(
            productRepository = provideProductRepository(),
            productDomainMapper = provideProductDomainMapper()
        )
    }

    private fun provideProductRepository(): ProductRepository {
        val local = productRepositorySingleton
        return local ?: run {
            val newProductRepository = ProductRepository(
                productLocalDataSource = provideProductLocalDataSource(),
                productRemoteDataSource = provideProductRemoteDataSource(),
                productDataMapper = provideProductDataMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            productRepositorySingleton = newProductRepository
            newProductRepository
        }
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private fun provideProductRemoteDataSource(): ProductRemoteDataSource {
        return ProductRemoteDataSource(
            productApiService = provideProductApiService(),
        )
    }

    private fun provideProductDataMapper(): ProductDataMapper {
        return ProductDataMapper()
    }

    private fun provideProductDomainMapper(): ProductDomainMapper {
        return ProductDomainMapper()
    }

    private fun provideProductVOMapper(): ProductVOFactory {
        return ProductVOFactory()
    }

    private fun providePromoVOMapper(): PromoVOMapper {
        return PromoVOMapper()
    }

    private fun providePromoDomainMapper(): PromoDomainMapper {
        return PromoDomainMapper()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton
        return local ?: run {
            val newRetrofit = Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideProductApiService(): ProductApiService {
        return provideRetrofit().create(ProductApiService::class.java)
    }

    private fun provideProductLocalDataSource(): ProductLocalDataSource {
        return ProductLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "clean_app")
}
