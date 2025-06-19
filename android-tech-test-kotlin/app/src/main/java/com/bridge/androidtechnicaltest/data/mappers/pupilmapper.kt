package com.bridge.androidtechnicaltest.data.mappers

import com.bridge.androidtechnicaltest.data.model.ItemDto
import com.bridge.androidtechnicaltest.domain.model.PupilItem


fun ItemDto.toPupilItem(workType: String): PupilItem {
    return PupilItem(
        country = country,
        image = image,
        latitude = latitude,
        longitude = longitude,
        name = name,
        pupilId = pupilId,
        workType = workType,
        time = System.currentTimeMillis(),
        offlineDataOperation = 0

    )

}