package ru.gb.android.marketsample.clean.data.promo

class PromoDataMapper {
    fun toEntity(promoDto: PromoDto): PromoEntity {
        return PromoEntity(
            id = promoDto.id,
            name = promoDto.name,
            image = promoDto.image,
            discount = promoDto.discount,
            description = promoDto.description,
            type = promoDto.type,
            products = promoDto.products ?: emptyList(),
        )
    }
}
