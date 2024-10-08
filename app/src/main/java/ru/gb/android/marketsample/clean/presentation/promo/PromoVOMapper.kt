package ru.gb.android.marketsample.clean.presentation.promo

import ru.gb.android.marketsample.clean.domain.promo.Promo

class PromoVOMapper {
    fun map(promo: Promo): PromoVO {
        return PromoVO(
            id = promo.id,
            name = promo.name,
            image = promo.image,
            description = promo.description
        )
    }
}
