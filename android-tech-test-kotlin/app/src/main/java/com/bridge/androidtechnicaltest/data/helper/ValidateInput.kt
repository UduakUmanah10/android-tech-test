package com.bridge.androidtechnicaltest.data.helper


data class PupilInput(
    val country: String?,
    val name: String?,
    val image: String?,
    val latitude: Double?,
    val longitude: Double?,
    val pupilid :Int?
)

data class PupilValidationResult(
    val isCountryCorrect: Boolean = true,
    val isNameCorrect: Boolean = true,
    val isImageCorrect: Boolean = true,
    val isLatitudeCorrect: Boolean = true,
    val isLongitudeCorrect: Boolean = true
)




fun isPupilInputValid(result: PupilValidationResult): Boolean {
    return result.isCountryCorrect &&
            result.isNameCorrect &&
            result.isImageCorrect &&
            result.isLatitudeCorrect &&
            result.isLongitudeCorrect
}
fun validatePupilInput(input: PupilInput): PupilValidationResult {
    val country = input.country?.trim()
    val name = input.name?.trim()
    val image = input.image?.trim()

    val isCountryValid = !country.isNullOrBlank() && country.length in 2..100
    val isNameValid = !name.isNullOrBlank() && name.length in 2..100
    val isImageValid = image.isNullOrBlank() || image.length in 11..1000
    val isLatValid = input.latitude != null && input.latitude in -90.0..90.0
    val isLonValid = input.longitude != null && input.longitude in -180.0..180.0

    return PupilValidationResult(
        isCountryCorrect = isCountryValid,
        isNameCorrect = isNameValid,
        isImageCorrect = isImageValid,
        isLatitudeCorrect = isLatValid,
        isLongitudeCorrect = isLonValid
    )
}
