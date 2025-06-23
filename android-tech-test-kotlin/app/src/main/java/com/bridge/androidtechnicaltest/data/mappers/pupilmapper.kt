package com.bridge.androidtechnicaltest.data.mappers

import androidx.room.PrimaryKey
import com.bridge.androidtechnicaltest.data.remote.model.PupilItemDto
import com.bridge.androidtechnicaltest.data.local.PupilItemEntity
import com.bridge.androidtechnicaltest.data.remote.model.PupilDtoResponse
import com.bridge.androidtechnicaltest.data.remote.model.PupilsDto
import com.bridge.androidtechnicaltest.domain.model.PupilList
import com.bridge.androidtechnicaltest.domain.model.Pupils



fun PupilItemDto.toPupilItem(workType: String): PupilItemEntity {
    return PupilItemEntity(
        country = country!!,
        image = image!!,
        latitude = latitude!!,
        longitude = longitude!!,
        name = name!!,
        pupilId = pupilId!!,
        workType = workType,
        time = System.currentTimeMillis(),
        offlineDataOperation = 0

    )

}

fun PupilsDto.toPupilList(): PupilList {

    return PupilList(
        itemCount = itemCount,
        items = items?.map { it.toPupils() } ?: emptyList<Pupils>(),
        pageNumber = pageNumber,
        totalPages = totalPages
    )

}

fun PupilItemDto.toPupils(): Pupils {

    return Pupils(
        country = country,
        latitude = latitude,
        longitude = longitude,
        name = name,
        pupilId = pupilId,
        image = image
    )
}


fun PupilItemEntity.toPupil(): Pupils {
    return Pupils(
        country = country,
        latitude = latitude,
        longitude = longitude,
        name = name,
        pupilId = pupilId,
        image = image
    )
}

fun PupilItemEntity.toPupils1(): Pupils{

return Pupils(
    country =country,
    image = image,
    latitude =latitude,
    longitude =longitude,
    name =name,
    pupilId = pupilId
)

}

fun Pupils.toPupilsEntity(): PupilItemEntity {

    return PupilItemEntity(
        country = country,
        image = image,
        latitude = latitude,
        longitude = longitude,
        name = name,
        pupilId = pupilId!!,
        workType = "",
        time = System.currentTimeMillis(),
        offlineDataOperation = 0,
    )
}

fun PupilDtoResponse.toPupilsEntity(): PupilItemEntity {
    return PupilItemEntity(
        pupilId = this.pupilId,
        name = this.name,
        country = this.country,
        image = this.image,
        latitude = this.latitude,
        longitude = this.longitude,
        workType = "",
        time = System.currentTimeMillis(),
        offlineDataOperation = 0
    )
}


fun PupilDtoResponse.toPupil(): Pupils {
    return Pupils(
        pupilId = this.pupilId,
        country = this.country,
        name = this.name,
        image = this.image,
        latitude = this.latitude,
        longitude = this.longitude
    )
}